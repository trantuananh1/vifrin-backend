package com.vifrin.media.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vifrin.common.config.constant.StringPool;
import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.entity.Media;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.MediaRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.media.dto.FileSupport;
import com.vifrin.media.mapper.MediaMapper;
import com.vifrin.media.utils.FileUploadHelper;
import org.apache.commons.io.FilenameUtils;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@PropertySources({ @PropertySource("aws_s3.properties") })
public class CdnService {

    private static final Logger logger = LoggerFactory.getLogger(CdnService.class);

    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaMapper mediaMapper;

    @Value("${AWS_S3_ACCESS_KEY}")
    private String AWS_S3_ACCESS_KEY;

    @Value("${AWS_S3_SECRET_KEY}")
    private String AWS_S3_SECRET_KEY;

    @Value("${AWS_S3_BUCKET_NAME}")
    private String AWS_S3_BUCKET_NAME;

    @Value("${AWS_S3_MEDIA_FOLDER}")
    private String AWS_S3_MEDIA_FOLDER;

    @Value("${AWS_S3_BUCKET_ADDRESS_FORMAT}")
    private String AWS_S3_BUCKET_ADDRESS_FORMAT;

    @Value("${AWS_S3_ENABLE}")
    private Boolean AWS_S3_ENABLE;

    @Value("${AWS_S3_REGION}")
    private String AWS_S3_REGION;


    public static final String AWS_S3_ASSET_FOLDER = "assets";
    public static final String AWS_S3_ASSET_FORMAT = "https://s3-ap-southeast-2.amazonaws.com/...";

    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_S3_ACCESS_KEY, AWS_S3_SECRET_KEY);

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setConnectionTimeout(120000);
        clientConfig.setMaxConnections(500);

        // create a client connection based on credentials
        s3Client = AmazonS3ClientBuilder.standard().withRegion(AWS_S3_REGION).withClientConfiguration(clientConfig)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

    }


    public String uploadToSpecificLocationOnCdn(String filePath, String targetLocation, boolean deleteLocalFile) {
        if (AWS_S3_ENABLE == null || AWS_S3_ENABLE == false) {
            logger.info("AWS disabled");
            return null;
        }

        if (!FileUploadHelper.isFileExists(filePath)) {
            logger.error("File to upload to CDN is not found at {}", filePath);
            return null;
        }

        logger.info("Start uploading to CDN ...");
        String fileName = FilenameUtils.getName(filePath);

        PutObjectResult result = s3Client.putObject(new PutObjectRequest(AWS_S3_BUCKET_NAME, targetLocation + fileName, new File(filePath))
                .withCannedAcl(CannedAccessControlList.PublicRead));

        logger.info("Uploaded file: " + fileName + " to S3 with eTag: " + result.getETag());

        // delete local file if upload success
        if (deleteLocalFile)
            FileUploadHelper.removeFileOnServer(fileName);

        return result.getETag();
    }

    public String uploadToCdn(String filePath, boolean deleteLocalFile) {
        if (AWS_S3_ENABLE == null || AWS_S3_ENABLE == false) {
            logger.info("AWS disabled");
            return null;
        }

        if (!FileUploadHelper.isFileExists(filePath)) {
            logger.error("File to upload to CDN is not found at {}", filePath);
            return null;
        }

        logger.info("Start uploading to CDN ...");
        String fileName = FilenameUtils.getName(filePath);

        s3Client.putObject(
                new PutObjectRequest(AWS_S3_BUCKET_NAME, AWS_S3_MEDIA_FOLDER + "/" + fileName, new File(filePath))
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        logger.info("Uploaded file: " + fileName);

        // delete local file if upload success
        if (deleteLocalFile)
            FileUploadHelper.removeFileOnServer(fileName);

        return String.format(AWS_S3_BUCKET_ADDRESS_FORMAT, fileName);
    }

    public String uploadToCdn(String filePath, String subFolder, boolean deleteLocalFile) {

        if (AWS_S3_ENABLE == null || AWS_S3_ENABLE == false) {
            logger.info("AWS disabled");
            return null;
        }

        if (!FileUploadHelper.isFileExists(filePath)) {
            logger.error("File to upload to CDN is not found at {}", filePath);
            return null;
        }

        logger.info("Start uploading to CDN ...");
        String fileName = subFolder + "/" + FilenameUtils.getName(filePath);

        s3Client.putObject(
                new PutObjectRequest(AWS_S3_BUCKET_NAME, AWS_S3_MEDIA_FOLDER + "/" + fileName, new File(filePath))
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        logger.info("Uploaded file: " + fileName);

        // delete local file if upload success
        if (deleteLocalFile)
            FileUploadHelper.removeFileOnServer(fileName);

        return String.format(AWS_S3_BUCKET_ADDRESS_FORMAT, fileName);
    }

    public String uploadToAsset(File file) {

        if (AWS_S3_ENABLE == null || AWS_S3_ENABLE == false) {
            logger.error("AWS disabled");
            return null;
        }

        if (file == null || !file.exists()) {
            logger.error("File to upload is not found");
            return null;
        }

        String fileName = file.getName();

        s3Client.putObject(new PutObjectRequest(AWS_S3_BUCKET_NAME, AWS_S3_ASSET_FOLDER + "/" + fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        // clean up local file
        FileUploadHelper.removeFileOnServer(fileName);

        return String.format(AWS_S3_ASSET_FORMAT, fileName);
    }


    public String getMediaFolder() {
        return AWS_S3_MEDIA_FOLDER;
    }

    public String getMediaFolderAddress() {
        return AWS_S3_BUCKET_ADDRESS_FORMAT;
    }

    public MediaDto uploadToCdn(MultipartFile file, String username) {
        try {
            File fileUp = convertMultipartToFile(file);
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(StringPool.SPACE, StringPool.UNDERLINE);
            String fileUrl = uploadFileToCloudinary(fileName, fileUp, file.getContentType());
            float height = 0;
            float width = 0;
            if (FileSupport.IMAGE.getTypes().contains(file.getContentType())) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                height = image.getHeight();
                width = image.getWidth();
            }
            User user = userRepository.findByUsername(username).get();
            Media media = mediaRepository.save(new Media(fileUrl, fileName, file.getContentType(), width, height, file.getSize(), user));
            FileUploadHelper.removeLocalFile(fileName);
            return mediaMapper.mediaToMediaDto(media);
        } catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String uploadFileToS3Bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(AWS_S3_BUCKET_NAME, AWS_S3_MEDIA_FOLDER + "/" + fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return String.format(AWS_S3_BUCKET_ADDRESS_FORMAT, AWS_S3_MEDIA_FOLDER, fileName);
    }

    private String uploadFileToCloudinary(String fileName, File fileUp, String mime){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzhowprbg",
                "api_key", "678878459731679",
                "api_secret", "1zl9VPue_eYt4DthOS-Etew9m6o"));
        try {
            String type = mime.contains("video") ? "video" : "image";
            Map response =  cloudinary.uploader().upload(fileUp, ObjectUtils.asMap("resource_type", type));
            JSONObject json = new JSONObject(response);
            return json.getString("url");
        }catch (Exception e){
            logger.error(e.getMessage());
            return StringPool.BLANK;
        }
    }
}

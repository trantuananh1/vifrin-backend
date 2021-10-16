package madie.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUploadHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadHelper.class);

    // Default time out in seconds
    private static final int CONNECTION_DEFAULT_TIMEOUT = 10 * 1000;

    private FileUploadHelper() {
    }

    public static String ROOT_UPLOAD_DIRECTORY = null;

    public static String ROOT_TMP_DIRECTORY = null;

    private static String ROOT_EXPORT_DIRECTORY = null;

    /**
     * This init function is called in MediaServiceImpl Contructor - where we can use Spring
     * managed property $vifrin.root
     *
     * @param rootDir
     */
    public static void init(String rootDir) {
        try {
            ROOT_TMP_DIRECTORY = new File(rootDir.trim(), "vifrin_upload_tmp").getAbsolutePath() + File.separator;
            File tempDir = new File(ROOT_TMP_DIRECTORY);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            ROOT_EXPORT_DIRECTORY = new File(rootDir.trim(), "vifrin_export").getAbsolutePath() + File.separator;
            File exportDir = new File(ROOT_EXPORT_DIRECTORY);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

        } catch (Exception e) {
            logger.error("Cannot create RootUpload directory", e);
        }
    }

    public static boolean removeFileOnServer(String fileName) {
        try {
            String filePath = String.format("%s%s", ROOT_UPLOAD_DIRECTORY, fileName);
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean removeLocalFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * Get final link (for case redirect to another link)
     */
    public static Map<String, String> getFinalUrl(String url) {

        Map<String, String> info = new HashMap<>();
        URL resourceUrl, base, next;
        HttpURLConnection con = null;
        String location;
        // prevent infinite loop
        int maxTry = 10;
        int tries = 0;

        try {

            while (true & tries < maxTry) {

                tries++;

                resourceUrl = new URL(url);
                con = (HttpURLConnection) resourceUrl.openConnection();
                con.setConnectTimeout(CONNECTION_DEFAULT_TIMEOUT);
                con.setReadTimeout(CONNECTION_DEFAULT_TIMEOUT);

                con.setInstanceFollowRedirects(false);
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                con.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");

                switch (con.getResponseCode()) {
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                    case HttpURLConnection.HTTP_SEE_OTHER:
                        location = con.getHeaderField("Location");
                        base = new URL(url);
                        next = new URL(base, location);
                        // Get redirect image url
                        url = next.toExternalForm();
                        continue;
                }
                break; // Stop when response code is not 301, 302
            }
        } catch (Exception e) {
            logger.error(String.format("Unable to read media info from url: ", url));
        }

        info.put("url", url);

        if (con != null)
            info.put("contentType", con.getContentType());

        return info;
    }


//    // Get thumbnail, title and correct url of embed media
//    public static VideoInfo grabVideoInfo(String url) {
//        VideoInfo info = new VideoInfo();
//
//        if (StringUtils.isEmpty(url))
//            return info;
//
//        try {
//
//            String vUrl = null;
//            if (url.contains("youtube.com")) {
//
//                Map<Object, Object> params = StringHelper.getRequestParams(url);
//                String vId = (String) params.get("v");
//
//                // https://www.youtube.com/embed/f0PhnsVNraA
//                if (vId == null && url.contains("embed")) {
//                    String[] parts = StringUtils.delimitedListToStringArray(url, "/");
//
//                    if ("embed".equals(parts[parts.length - 2])) {
//                        vId = parts[parts.length - 1];
//                    }
//                }
//
//                vUrl = String.format("https://www.youtube.com/watch?v=%s", vId);
//
//            } else if (url.contains("youtu.be")) {
//
//                String vId = url.contains("?") ? StringHelper.extract(url, "youtu.be\\/(.*)\\?.*")
//                        : StringHelper.extract(url, "youtu.be\\/(.*)");
//                vUrl = String.format("https://www.youtube.com/watch?v=%s", vId);
//
//            } else if (url.contains("vimeo.com")) {
//                vUrl = url;
//                if (url.contains("review")) {
//                    String vId = StringHelper.extract(url, "review\\/(.*)\\/.*");
//                    vUrl = String.format("https://vimeo.com/%s", vId);
//
//                } else if (url.contains("player.vimeo.com/video")) {
//                    String vId = StringHelper.extract(url, "video/(\\d+).*");
//                    vUrl = String.format("https://vimeo.com/%s", vId);
//                }
//            }
//
//            // Noembed Embed everything. https://noembed.com/
//            URLConnection conn = new URL(String.format("https://noembed.com/embed?url=%s", vUrl)).openConnection();
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            Map<Object, Object> data = mapper.readValue(conn.getInputStream(),
//                    new TypeReference<HashMap<Object, Object>>() {
//                    });
//
//            // if URL is not final (redirect to another link)
//            if (data != null && data.get("error") != null) {
//
//                String errorMessage = (String) data.get("error");
//                if (errorMessage.contains("404")) {
//
//                    Map<String, String> finalUrlInfo = getFinalUrl(url);
//
//                    if (finalUrlInfo != null) {
//                        String finalUrl = finalUrlInfo.get(P.URL);
//                        if (!StringUtils.isEmpty(finalUrl) && !finalUrl.equals(vUrl)) {
//
//                            conn = new URL(String.format("https://noembed.com/embed?url=%s", finalUrl))
//                                    .openConnection();
//
//                            data = mapper.readValue(conn.getInputStream(),
//                                    new TypeReference<HashMap<Object, Object>>() {
//                                    });
//                        }
//                    }
//                }
//            }
//
//            if (data != null) {
//                if (data.get("title") instanceof String) {
//                    info.setTitle((String) data.get("title"));
//                }
//
//                if (data.get("url") instanceof String) {
//                    info.setUrl((String) data.get("url"));
//                }
//
//                String thumbnailUrl = (String) data.get("thumbnail_url");
//
//                if (!StringUtils.isEmpty(thumbnailUrl)) {
//                    // hqdefault.jpg has less quality but always exist
//                    String videoThumbnail = thumbnailUrl;
//
//                    if (videoThumbnail.contains("hqdefault")) {
//                        // check if higher res is available (standard size (640x480))
//                        String standardThumbnail = thumbnailUrl.replace("hqdefault", "sddefault");
//                        Reader entityReader = RestConsumerHelper.getDataFromApiUrl(standardThumbnail);
//                        if (entityReader != null)
//                            videoThumbnail = standardThumbnail;
//                    }
//
//                    info.setThumbnailUrl(videoThumbnail);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Grab Video Info got error for url {}", url);
//        }
//
//        return info;
//
//    }

    public static String getFullFilePath(String fileName) {
        return String.format("%s%s", FileUploadHelper.ROOT_UPLOAD_DIRECTORY, fileName);
    }

    public static boolean isFileExists(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        File f = new File(filePath);

        return f.exists();
    }

    public static String guessContentType(String fileName) {
        String contentType = null;
        String filePath = FileUploadHelper.getFullFilePath(fileName);

        if (FileUploadHelper.isFileExists(filePath)) {
            try {
                Tika tika = new Tika();

                // detect by filename first
                contentType = tika.detect(fileName);

                if (StringUtils.isEmpty(contentType) || contentType.equalsIgnoreCase(MimeTypes.OCTET_STREAM))
                    contentType = tika.detect(new FileInputStream(new File(filePath)));

            } catch (Exception e) {
                logger.error("Unable to detect mime type");
            }
        }
        return contentType;
    }

    public static InputStream getInputStreamFromUrl(String url) throws Exception {
        InputStream stream = null;

        Connection connection = Jsoup.connect(url);
        connection.header("Content-Type", "application/x-www-form-urlencoded");
        connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.timeout(0); // infinite time out
        connection.maxBodySize(0); // read whole content

        byte[] bytes = connection.ignoreContentType(true).execute().bodyAsBytes();
        stream = new ByteArrayInputStream(bytes);
        return stream;
    }
    public static String saveFileOnServer(InputStream file, String subFolder, String extension)
            throws VifrinBaseException {

        return saveFileOnServer(file, subFolder, extension, null);
    }

    public static String saveFileOnServer(InputStream file, String subFolder, String extension, String fileName)
            throws VifrinBaseException {

        File uploadDir = new File(ROOT_UPLOAD_DIRECTORY + (subFolder == null ? "" : subFolder));
        try {
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
        } catch (Exception e) {
            logger.error("Cannot create upload subfolter", e);
            throw e;
        }

        // Generate random file name if not provide
        if (StringUtils.isEmpty(fileName)) {
            fileName = UUID.randomUUID().toString().replace("-", "");

            // generate random name with no extension
            if (!StringUtils.isEmpty(extension)) {
                fileName = String.format("%s%s%s", fileName, FilenameUtils.EXTENSION_SEPARATOR, extension);
            }
        }

        File fileOnServer = new File(uploadDir, fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(fileOnServer);
            IOUtils.copy(file, fos);
            // To be certain that the file is actually written to disk
            fos.flush();
            fos.getFD().sync();
        } catch (Exception e) {
            throw new VifrinBaseException(new ErrorInfo(ErrorInfo.UNKNOWN_ERROR_CODE, e.getMessage()));
        } finally {
            try {
                if (file != null)
                    file.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

        // if subFolder parameter passed, returns full file path
        String uploadedFile = StringUtils.isEmpty(subFolder) ? fileName : fileOnServer.getAbsolutePath();
        if (StringUtils.isEmpty(uploadedFile)) {
            throw new VifrinBaseException(ErrorInfo.NOT_SUPPORT_ERROR);
        }
        return uploadedFile;
    }
}

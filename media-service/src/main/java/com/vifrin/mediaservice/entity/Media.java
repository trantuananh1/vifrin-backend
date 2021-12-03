//package com.vifrin.mediaservice.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.vifrin.mediaservice.dto.FileSupport;
//import com.vifrin.mediaservice.dto.TypeOfMedia;
//import com.vifrin.mediaservice.utils.FileUploadHelper;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.*;
//import java.beans.Transient;
//import java.io.Serializable;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@SuppressWarnings("serial")
//@Entity
//@Table(name = "media")
//@Embeddable
//public class Media implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//    private Integer id;
//
//    @Column(name = "original_filename", nullable = true)
//    private String originalFilename;
//
//    @Column(name = "filename", unique = true, nullable = true)
//    private String filename;
//
//    @Column(name = "thumbnail")
//    private String thumbnail;
//
//    @Column(name = "content_type")
//    private String contentType;
//
//    @Column(name = "url")
//    private String url;
//
//    @Column(name = "is_processing")
//    private Boolean isProcessing;
//
//    @Column(name = "duration")
//    private String duration;
//
//    @Column(name = "local_thumbnail")
//    private String localThumbnail;
//
//    @Column(name = "local_thumbnail_cdn")
//    private String localThumbnailCdn;
//
//    @Column(name = "account_id", nullable = true)
//    private Integer uploader;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "upload_date", length = 19)
//    private Date uploadDate;
//
//    /**
//     * Url to origin media in cdn location, for sample
//     * https://vifrin.s3.amazonaws.com/media_test/ 4d8c026708744ff38651ede7d318d563
//     */
//    @Column(name = "cdn_origin")
//    private String cdnOrigin;
//
//    @Column(name = "cdn_large")
//    private String cdnLarge;
//
//    @Column(name = "cdn_medium")
//    private String cdnMedium;
//
//    @Column(name = "cdn_small")
//    private String cdnSmall;
//
//    /**
//     * Time after upload to CDN success
//     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "cdn_time", length = 19)
//    private Date cdnTime;
//
//    @Transient
//    private String shortOriginalFilename;
//
//    @Transient
//    private Integer typeOfMedia;
//
//    @Column(name = "guid")
//    private String guid;
//
//    // --------------------------------------------------------------------------------------
//    // constructor
//
//    public Media(Integer id) {
//        this.id = id;
//    }
//
//    public Media() {
//    }
//
//    // --------------------------------------------------------------------------------------
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getOriginalFilename() {
//        return originalFilename;
//    }
//
//    public void setOriginalFilename(String originalFilename) {
//        this.originalFilename = originalFilename;
//    }
//
//    public String getFilename() {
//        return filename;
//    }
//
//    public void setFilename(String filename) {
//        this.filename = filename;
//    }
//
//    public String getContentType() {
//        return contentType;
//    }
//
//    public void setContentType(String contentType) {
//        this.contentType = contentType;
//    }
//
//    public Integer getUploader() {
//        return uploader;
//    }
//
//    public void setUploader(Integer uploader) {
//        this.uploader = uploader;
//    }
//
//    public Date getUploadDate() {
//        return uploadDate;
//    }
//
//    public void setUploadDate(Date uploadDate) {
//        this.uploadDate = uploadDate;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public Boolean getIsProcessing() {
//        return isProcessing;
//    }
//
//    public void setIsProcessing(Boolean isProcessing) {
//        this.isProcessing = isProcessing;
//    }
//
//    public String getDuration() {
//        return duration;
//    }
//
//    public void setDuration(String duration) {
//        this.duration = duration;
//    }
//
//    public String getCdnOrigin() {
//        return cdnOrigin;
//    }
//
//    public void setCdnOrigin(String cdnOrigin) {
//        this.cdnOrigin = cdnOrigin;
//    }
//
//    public String getCdnLarge() {
//        return cdnLarge;
//    }
//
//    public void setCdnLarge(String cdnLarge) {
//        this.cdnLarge = cdnLarge;
//    }
//
//    public String getCdnMedium() {
//        return cdnMedium;
//    }
//
//    public void setCdnMedium(String cdnMedium) {
//        this.cdnMedium = cdnMedium;
//    }
//
//    public String getCdnSmall() {
//        return cdnSmall;
//    }
//
//    public void setCdnSmall(String cdnSmall) {
//        this.cdnSmall = cdnSmall;
//    }
//
//    public Date getCdnTime() {
//        return cdnTime;
//    }
//
//    public void setCdnTime(Date cdnTime) {
//        this.cdnTime = cdnTime;
//    }
//
//    @Override
//    public int hashCode() {
//        return getId() != null ? getId() : 0;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        if (o == this)
//            return true;
//
//        Media m = (Media) o;
//        return this.getId() != null && this.getId().equals(m.getId());
//    }
//
//    public void setShortOriginalFilename(String shortOriginalFilename) {
//        this.shortOriginalFilename = shortOriginalFilename;
//    }
//
//    @Transient
//    @JsonIgnore
//    public boolean isImage() {
//        String fileType = FileSupport.getFileType(this.getContentType());
//        if (!StringUtils.isEmpty(fileType) && fileType.equals(FileSupport.IMAGE.name())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Transient
//    @JsonIgnore
//    public boolean isVideo() {
//        String fileType = FileSupport.getFileType(this.getContentType());
//        if (!StringUtils.isEmpty(fileType) && fileType.equals(FileSupport.VIDEO.name())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Transient
//    @JsonIgnore
//    public boolean isAudio() {
//        String fileType = FileSupport.getFileType(this.getContentType());
//        if (!StringUtils.isEmpty(fileType) && fileType.equals(FileSupport.AUDIO.name())) {
//            return true;
//        }
//        return false;
//    }
//
//    public String getLocalThumbnail() {
//        return localThumbnail;
//    }
//
//    public void setLocalThumbnail(String localThumbnail) {
//        this.localThumbnail = localThumbnail;
//    }
//
//    public Integer getTypeOfMedia() {
//        return typeOfMedia;
//    }
//
//    public void setTypeOfMedia(Integer typeOfMedia) {
//        this.typeOfMedia = typeOfMedia;
//    }
//
//    /**
//     * @return the localThumbnailCdn
//     */
//    public String getLocalThumbnailCdn() {
//        return localThumbnailCdn;
//    }
//
//    /**
//     * @param localThumbnailCdn the localThumbnailCdn to set
//     */
//    public void setLocalThumbnailCdn(String localThumbnailCdn) {
//        this.localThumbnailCdn = localThumbnailCdn;
//    }
//
//    public Integer getFileType() {
//        // internal files
//        String contentType = this.getContentType();
//
//        if (StringUtils.isEmpty(contentType)) {
//            contentType = FileUploadHelper.guessContentType(this.getFilename());
//        }
//
//        String fileType = FileSupport.getFileType(contentType);
//        if (fileType != null) {
//            if (fileType.equals(FileSupport.IMAGE.name())) {
//                return TypeOfMedia.IMAGE.getCode();
//
//            } else if (fileType.equals(FileSupport.VIDEO.name())) {
//                return TypeOfMedia.VIDEO.getCode();
//
//            } else if (fileType.equals(FileSupport.AUDIO.name())) {
//                return TypeOfMedia.AUDIO.getCode();
//
//            } else {
//                return TypeOfMedia.DOCUMENT.getCode();
//
//            }
//        } else {
//            return TypeOfMedia.DOCUMENT.getCode();
//        }
//    }
//
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//}

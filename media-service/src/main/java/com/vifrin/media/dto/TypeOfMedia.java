package com.vifrin.media.dto;

import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public enum TypeOfMedia {
    IMAGE(1, "Image"),

    AUDIO(2, "Audio"),

    VIDEO(3, "Video"),

    ALBUM(4, "Album"),

    DOCUMENT(5, "Document");


    private int code;
    private String name;

    private TypeOfMedia(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public static boolean isMember(int value) {
        for (TypeOfMedia item : TypeOfMedia.values()) {
            if (item.getCode() == value) {
                return true;
            }
        }
        return false;
    }

    public static Integer convertToCode(String value) {
        if (!StringUtils.isEmpty(value)) {

            for (TypeOfMedia item : TypeOfMedia.values()) {
                if (item.getName().toLowerCase().contains(value.toLowerCase())) {
                    return item.getCode();
                }
            }
        }
        return TypeOfMedia.IMAGE.getCode();
    }

    public static String getMediaTypeName(int code) {
        String type = null;
        if (code == IMAGE.code || code == ALBUM.code) {
            type = "Image";
        } else if (code == VIDEO.code) {
            type = "Video";
        } else if (code == AUDIO.code) {
            type = "Audio";
        } else if(code == DOCUMENT.code) {
            type = DOCUMENT.getName();
        }
        return type;
    }

    public static String getMediaWebUrl(int code) {
        String url = "#";
        if (code == IMAGE.code || code == ALBUM.code) {
            url = "photo";
        } else if (code == VIDEO.code) {
            url = "video";
        } else if (code == AUDIO.code) {
            url = "audio";
        }
        return url;
    }

    public static Set<Integer> getBasicMediaTypes() {
        Set<Integer> mediaTypes = new HashSet<>();
        mediaTypes.add(TypeOfMedia.IMAGE.getCode());
        mediaTypes.add(TypeOfMedia.VIDEO.getCode());
        mediaTypes.add(TypeOfMedia.AUDIO.getCode());
        return mediaTypes;
    }
}

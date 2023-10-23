package cn.toutatis.xvoid.toolkit.file;

import org.springframework.http.MediaType;

public enum FileMagic {

    /**
     * JPEG
     */
    FFD8FFE0("JPEG",MediaType.IMAGE_JPEG),

    ;

    private String suffix;

    private MediaType mediaType;

    FileMagic(String suffix, MediaType mediaType) {
        this.suffix = suffix;
        this.mediaType = mediaType;
    }
}

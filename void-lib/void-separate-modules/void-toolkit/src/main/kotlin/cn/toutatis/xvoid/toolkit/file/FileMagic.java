package cn.toutatis.xvoid.toolkit.file;

import org.springframework.http.MediaType;

/**
 * 文件头枚举
 * @author Toutatis_Gc
 */
public enum FileMagic {

    /**
     * JPEG
     */
    FFD8FFE0("JPEG",MediaType.IMAGE_JPEG),

    ;

    private final String suffix;

    private final MediaType mediaType;

    FileMagic(String suffix, MediaType mediaType) {
        this.suffix = suffix;
        this.mediaType = mediaType;
    }

    public String getSuffix() {
        return suffix;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}

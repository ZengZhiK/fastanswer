package com.zzk.fastanswer.model.dto;

import lombok.Data;

/**
 * @author zzk
 * @create 2020-11-25 9:00
 */
@Data
public class ImageUploadDTO {
    /**
     * 0 表示上传失败，1 表示上传成
     */
    private int success;

    /**
     * 提示的信息，上传成功或上传失败及错误信息等
     */
    private String message;

    /**
     * 图片地址, 上传成功时才返回
     */
    private String url;
}

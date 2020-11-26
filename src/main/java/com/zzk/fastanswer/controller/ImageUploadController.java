package com.zzk.fastanswer.controller;

import com.zzk.fastanswer.model.dto.ImageUploadDTO;
import com.zzk.fastanswer.provider.QCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传 请求处理
 *
 * @author zzk
 * @create 2020-11-25 8:58
 */
@Controller
@RequestMapping("/image")
public class ImageUploadController {
    @Autowired
    private QCloudProvider qCloudProvider;

    /**
     * markdown编辑器图片上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public ImageUploadDTO upload(@RequestParam(value = "editormd-image-file") MultipartFile image) {
        String imageUrl = qCloudProvider.upload(image);
        ImageUploadDTO imageUploadDTO = new ImageUploadDTO();
        imageUploadDTO.setSuccess(1);
        imageUploadDTO.setMessage("上传成功");
        imageUploadDTO.setUrl(imageUrl);
        return imageUploadDTO;
    }
}

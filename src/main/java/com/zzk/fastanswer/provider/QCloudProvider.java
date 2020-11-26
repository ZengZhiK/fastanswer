package com.zzk.fastanswer.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.zzk.fastanswer.common.constant.ExceptionConstants;
import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


/**
 * QCloud API
 *
 * @author zzk
 * @create 2020-11-25 9:14
 */
@Component
public class QCloudProvider {
    @Value("${qcloud.cos.secret-id}")
    private String secretId;

    @Value("${qcloud.cos.secret-key}")
    private String secretKey;

    @Value("${qcloud.cos.region}")
    private String regionName;

    @Value("${qcloud.cos.bucket}")
    private String bucketName;

    @Value("${qcloud.cos.url}")
    private String url;

    /**
     * 文件上传至COS
     */
    public String upload(MultipartFile file) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 指定要上传的文件
            String oldFileName = file.getOriginalFilename();
            String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), eName);
            file.transferTo(localFile);
            String newFileName = UUID.randomUUID() + eName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFileName, localFile);
            cosClient.putObject(putObjectRequest);
            return url + "/" + newFileName;
        } catch (Exception e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.FILE_UPLOAD_FAILED);
        } finally {
            cosClient.shutdown();
        }
    }
}

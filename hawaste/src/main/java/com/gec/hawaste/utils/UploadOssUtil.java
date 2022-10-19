package com.gec.hawaste.utils;


import com.gec.hawaste.config.OssProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class UploadOssUtil {

    public static  void uploadFile(MultipartFile upfile, String fileName, OssProperties ossProperties){

        try {
            System.out.println(fileName);
            System.out.println(upfile.getInputStream());
            System.out.println(upfile.getContentType());
            System.out.println(upfile.getOriginalFilename());
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = MinioClient.builder().endpoint(ossProperties.getEndpoint(), ossProperties.getPort(),ossProperties.getSecure()).credentials(ossProperties.getAccessKey(),ossProperties.getSecretKey()).build();

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(ossProperties.getBucketName()).build());
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为uploads的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(ossProperties.getBucketName()).build());
            }
            // 使用putObject上传一个文件到存储桶中。
//            minioClient.putObject(
//                    PutObjectArgs.builder().
//                            bucket(ossProperties.getBucketName()).
//                            object(fileName).
//                            stream(upfile.getInputStream(),upfile.getSize(),-1).
//                            build());
            //String objName = "ueditor/image/20211119/" + upfile.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(ossProperties.getBucketName()).object(fileName).stream(upfile.getInputStream(),upfile.getSize(),-1).build());
            //minioClient.putObject(ossProperties.getBucketName(),fileName,upfile.getInputStream(),upfile.getContentType());
            String url = minioClient.getObjectUrl(ossProperties.getBucketName(),fileName);
            System.out.println(url);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}

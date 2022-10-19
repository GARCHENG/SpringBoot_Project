package com.gec.controller;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@ResponseBody
public class UploadController {
    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
   private String endpoint;
    @Value("${minio.port}")
   private Integer port;
    @Value("${minio.secure}")
   private  Boolean secure;
    @Value("${minio.accessKey}")
   private String accessKey;
    @Value("${minio.secretKey}")
   private String secretKey;

/*    bucketName: uploads # 存储桶名字
    endpoint: 127.0.0.1 #桶所在节点ip
    port: 9000      # web管理服务访问端口
    secure: false  #false为http   true为 https
    accessKey: minioadmin #登录账号
    secretKey: minioadmin #密码*/

    //  上传的方法   springmvc 中上传
    @RequestMapping("upload")
    public String upload(MultipartFile file)
    {
     // file.transferTo();
        // 将原来的上传到本地 改造成 上传的minio 图片服务器上

        //1. 创建 minioclient
        // public MinioClient.Builder endpoint(String endpoint, int port, boolean secure)
      //  MinioClient minioClient = MinioClient.builder().endpoint(endpoint, port, secure).build();
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint, port, secure).credentials(accessKey,secretKey).build();

        //2. 判断 当前 minio 服务器上是否存在  存储桶  bucket

        // 如果 当前minio 中 存在 存储桶 就直接使用
        // 如果当前minio 中 不存在 存储桶 就需要先去创建
        // BucketExistsArgs
        boolean ixExist = true;

        String objectUrl= "";

        try {
            ixExist =  minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (ixExist)
            {
                System.out.println("当前 存储桶 bucket 已经存在了...");
            }
            else
            {
                // 当前minio 服务器上 不存在  存储桶 需要 创建出来 存储桶
                // MakeBucketArgs  MakeBucketArgs.builder().bucket(bucketName).build()
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());


            }
            //  将 上传的内容 存储  到 bucket 桶中

            // 按照日期  创建好 将来 存储的 文件夹

            // 20220419
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 获取前端上传的文件名称
            String filename = file.getOriginalFilename();

            // 定义存储对象名称
           String objectname =    sdf.format(new Date())+"/"+filename;


           //mall     20220419  a.jpg  b.jpg     20220420  c.jpg


            //  public PutObjectArgs.Builder stream(InputStream stream, long objectSize, long partSize)

            // 上传
           // minioClient.putObject(PutObjectArgs.builder().object(objectname).stream(file.getInputStream(),file.getSize(),-1).build());
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectname).stream(file.getInputStream(),file.getSize(),-1).build());


            // 获取上传后的图片地址
             objectUrl = minioClient.getObjectUrl(bucketName, objectname);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectUrl;
    }


}

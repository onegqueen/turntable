package com.example.turntable.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NcpService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    /*이미지를 얻음*/
    public byte[] getImgObject(String key) throws IOException {
        S3Object object = amazonS3.getObject(bucketName, key);
        S3ObjectInputStream objectInputStream = object.getObjectContent();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length= objectInputStream.read(buffer))!=-1){
            byteArrayOutputStream.write(buffer,0,length);
        }

        return byteArrayOutputStream.toByteArray();
    }

    /*이미지 업로드 후 url 반환*/
    public String uploadFile(MultipartFile file) throws IOException {
        if (file!=null){
            String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucketName,uniqueFileName).toString();
        }
        else{
            System.out.println("*************이미지 null");
            return null;
        }
    }

}

package com.example.turntable.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NcpService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

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

}

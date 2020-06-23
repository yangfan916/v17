package com.yangfan.v17center;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V17CenterApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(V17CenterApplicationTests.class);

    @Autowired
    private FastFileStorageClient client;

    @Test
    public void contextLoads() {
    }

    @Test
    public void uploadTest() throws FileNotFoundException {
        File file = new File("D:\\Google Downloads\\20200621082654467.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = client.uploadImageAndCrtThumbImage(fileInputStream, file.length(), "jpg", null);
        log.info("fullPath: " + storePath.getFullPath());
        log.info("group: " + storePath.getGroup());
        log.info("path: " + storePath.getPath());
    }

    @Test
    public void deleteTest(){
        client.deleteFile("group1/M00/00/00/wKiVgF7wbOCAY7wxAAvX5XSrBdo731.jpg");
        log.info("删除成功");
    }

}

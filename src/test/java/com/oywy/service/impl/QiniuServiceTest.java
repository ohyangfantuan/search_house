package com.oywy.service.impl;

import com.oywy.SearchhouseApplicationTests;
import com.oywy.service.QiniuService;
import com.qiniu.http.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by oywy on 2018/3/15.
 */
public class QiniuServiceTest extends SearchhouseApplicationTests {
    @Autowired
    QiniuService qiniuService;
    @Test
    public void uploadFile() throws Exception {
        String fileName = "C:/Users/oywy/IdeaProjects/searchhouse/src/main/resources/static/upload/photo/75ad3534349b033b118f8e2312ce36d3d439bd4f.png";
        File file = new File(fileName);
        //断言file是否存在
        assertTrue(file.exists());
        Response response = qiniuService.uploadFile(file);
        assertTrue(response.isOK());
        System.out.println(response.getInfo());

    }

    @Test
    public void uploadFile1() throws Exception {
    }

    @Test
    public void delete() throws Exception {
        String key = "FnM0Y1JCp-RKIDKxgeyaq4bhHEiC";
        Response response = qiniuService.delete(key);
        assertTrue(response.isOK());
    }

}
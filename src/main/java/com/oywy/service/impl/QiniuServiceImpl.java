package com.oywy.service.impl;

import com.oywy.service.QiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * Created by oywy on 2018/3/15.
 */
@Service
public class QiniuServiceImpl implements QiniuService {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private Auth auth;
    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    {
        putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":\"$(imageInfo.width)\",\"height\":\"$(imageInfo.height)\"}");
    }

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = uploadManager.put(file, null, getUploadToken());
        int retry = 3;
        //尝试三次
        while (response.needRetry() && retry-- < 3)
            response = uploadManager.put(file, null, getUploadToken());
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        return null;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        return null;
    }

    /**
     * 获取上传token
     *
     * @return
     */
    private String getUploadToken() {
        System.out.println(putPolicy.get("returnBody"));
        return auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}

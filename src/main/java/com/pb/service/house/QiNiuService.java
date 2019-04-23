package com.pb.service.house;

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
 * @Author pengbin
 * @date 2019/4/19 13:44
 */
@Service
public class QiNiuService implements IQiNiuService{

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;



    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, null, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3){
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, null, getUploadToken(),null,null);
        int retry = 0;
        while (response.needRetry() && retry < 3){
            response = this.uploadManager.put(inputStream, null, getUploadToken(),null,null);
            retry++;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        return null;
    }

    /**
     * 返回结果格式定义，遵循七牛云文档要求
     * @throws QiniuException
     */
    @Override
    public void afterPropertiesSet() throws QiniuException {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\"," +
                "\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 返回上传凭证
     * @return
     */
    private String getUploadToken(){
        return this.auth.uploadToken(bucket,null,3600,putPolicy);
    }
}

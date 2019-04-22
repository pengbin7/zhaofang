package com.pb.service.house;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

/**
 * @Author pengbin
 * @date 2019/4/19 11:43
 */
public interface IQiNiuService {

    Response uploadFile(File file) throws QiniuException;

    Response uploadFile(InputStream inputStream)throws QiniuException;

    Response delete(String key)throws QiniuException;

    void afterPropertiesSet()throws QiniuException;

}

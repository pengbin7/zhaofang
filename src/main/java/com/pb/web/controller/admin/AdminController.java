package com.pb.web.controller.admin;

import com.google.gson.Gson;
import com.pb.base.ApiResponse;
import com.pb.service.house.IQiNiuService;
import com.pb.web.domain.QiNiuPutSet;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author pengbin
 * @date 2019/4/17 15:00
 */
@Controller
public class AdminController {

    @Autowired
    private IQiNiuService qiNiuService;

    @Autowired
    private Gson gson;

    /**
     * 后台管理中心
     * @return
     */
    @GetMapping("/admin/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    /**
     * 欢迎页
     * @return
     */
    @GetMapping("/admin/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }

    /**
     * 管理员登录页
     * @return
     */
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    /**
     * 新增房源功能页
     * @return
     */
    @GetMapping("admin/add/house")
    public String addHousePage() {
        return "admin/house-add";
    }

    /**
     * 房源列表页
     * @return
     */
    @GetMapping("admin/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }

    @PostMapping(value = "admin/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        /*String fileName = file.getOriginalFilename();
        File target = new File("D:\\workspace\\myGit\\搜索测试\\zhaofang\\tmp\\"+fileName);
        try {
            file.transferTo(target);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return ApiResponse.ofSuccess(null);
        */
        try {
            InputStream inputStream = file.getInputStream();
            Response response = qiNiuService.uploadFile(inputStream);
            if(response.isOK()){
                QiNiuPutSet putSet = gson.fromJson(response.bodyString(), QiNiuPutSet.class);
                return ApiResponse.ofSuccess(putSet);
            }else{
                return ApiResponse.ofMessage(response.statusCode,response.getInfo());
            }
        }catch (QiniuException e){
            Response response = e.response;
            try {
                return ApiResponse.ofMessage(response.statusCode,response.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
                return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

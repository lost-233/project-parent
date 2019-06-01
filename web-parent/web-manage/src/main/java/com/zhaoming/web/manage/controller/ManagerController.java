package com.zhaoming.web.manage.controller;


import com.zhaoming.base.util.PasswordUtil;
import com.zhaoming.bean.web.Result;
import com.zhaoming.fastdfs.DfsUpAndDowService;
import com.zhaoming.web.manage.entity.Manager;
import com.zhaoming.web.manage.security.ManagerUserDetailsService;
import com.zhaoming.web.manage.utils.ManagerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerUserDetailsService service;
    @Autowired
    private DfsUpAndDowService dfsUpAndDowService;

    public static final String BASE_URL="http://zm.lost-2112.top/";

    @PostMapping("/edit")
    public Result edit(@RequestBody Manager manager) {
        service.edit(manager);
        return Result.OK;
    }

    @GetMapping("/check-password")
    public Result checkOldPassword(String password) {
        return PasswordUtil.encrypt(password).equals(ManagerContextHolder.requireGetManager().getPassword()) ? Result.OK : Result.error("原始密码错误!");
    }

    @PutMapping("/password")
    public Result updatePassword(String password) {
        service.updatePassword(ManagerContextHolder.requireGetManager().getUsername(), PasswordUtil.encrypt(password));
        return Result.OK;
    }

    @PostMapping("/upload")
    public Result updatePassword(MultipartFile file) {
        try {
            String url = dfsUpAndDowService.uploadFile(file.getInputStream(), file.getOriginalFilename());
            return Result.ok(BASE_URL + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ERROR;
    }
}

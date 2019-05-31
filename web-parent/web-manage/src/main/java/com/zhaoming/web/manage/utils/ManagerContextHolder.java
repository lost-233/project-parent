package com.zhaoming.web.manage.utils;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.web.manage.bean.ManagerProxy;
import com.zhaoming.web.manage.view.Authority;
import com.zhaoming.web.manage.view.SideViewItem;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 工具类
 *
 * @author hhx
 */
public class ManagerContextHolder {

    public static String requireGetManagerRealName(){
        return requireGetManager().getRealName();
    }

    public static ManagerProxy requireGetManager(){
        return Optional.of(getManager()).orElseThrow(() -> new RuntimeException("登录信息丢失!"));
    }

    public static ManagerProxy getManager(){
        return (ManagerProxy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 判断登录的管理员是否拥有指定权限,并忽略ALL权限
     * @param roles
     * @return
     */
    public static boolean hasRolesIgnoreAll(SideViewItem... roles){
        List<String> userRoles = Arrays
                .asList(getManager().getAuthority().split(CommonConstant.SEPARATOR));
        for (SideViewItem role : roles) {
            if(!userRoles.contains(role.getRole())){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断登录的管理员是否拥有指定权限
     * @param roles
     * @return
     */
    public static boolean hasRoles(SideViewItem... roles){
        String authority = getManager().getAuthority();
        List<String> userRoles = Arrays
                .asList(authority.split(CommonConstant.SEPARATOR));
        if(!authority.equals(Authority.ALL)) {
            for (SideViewItem role : roles) {
                if (!userRoles.contains(role.getRole())) {
                    return false;
                }
            }
        }
        return true;
    }
}

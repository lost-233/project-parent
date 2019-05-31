package com.zhaoming.web.manage.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaoming.base.bean.PageCondition;
import com.zhaoming.base.util.PasswordUtil;
import com.zhaoming.web.manage.bean.ManagerProxy;
import com.zhaoming.web.manage.entity.Manager;
import com.zhaoming.web.manage.mapper.ManagerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zm
 */
@Slf4j
@Service
public class ManagerUserDetailsService implements UserDetailsService {

    @Autowired
    ManagerMapper managerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerMapper.selectByUsername(username);
        if (null!= manager){
            if(!manager.getEnabled()){
               throw new DisabledException("用户被禁用");
            }
            if(StringUtils.isBlank(manager.getAuthority())){
                throw new DisabledException("用户没有任何权限");
            }
            return new ManagerProxy(manager);
        }else{
            throw new UsernameNotFoundException("该用户不存在");
        }
    }

    public Manager getById(Integer id) {
        return managerMapper.selectById(id);
    }

    public PageInfo getPage(PageCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<Manager> managers = managerMapper.selectList(new LambdaQueryWrapper<>());
        return new PageInfo<>(managers);
    }

    public void edit(Manager manager) {
        if(manager.getId() == null){
            manager.setPassword(PasswordUtil.encrypt(manager.getPassword()));
            managerMapper.insert(manager);
        }else{
            managerMapper.updateById(manager);
        }
    }

    public void updatePassword(String username, String password) {
        int i = managerMapper.updatePassword(username, password);
        log.info("用户 {} 的密码更新为 {} : {}", username, password, i==1);
    }
}

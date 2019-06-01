package com.zhaoming.web.manage.bean;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.web.manage.entity.Manager;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 代理
 *
 * @author hhx
 */
@Data
@NoArgsConstructor
public class ManagerProxy extends Manager implements UserDetails {

    public ManagerProxy(Manager manager) {
        this.id = manager.getId();
        this.headImage = manager.getHeadImage();
        this.username = manager.getUsername();
        this.password = manager.getPassword();
        this.enabled = manager.getEnabled();
        this.authority = manager.getAuthority();
        this.tel = manager.getTel();
        this.email = manager.getEmail();
        this.realName = manager.getRealName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authority.split(CommonConstant.SEPARATOR))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

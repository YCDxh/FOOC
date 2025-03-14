package com.YCDxh.security;

import com.YCDxh.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author YCDxhg
 */
public class MyUserDetails implements UserDetails {
    private final User user; // 业务User对象

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回角色权限（如ROLE_USER）
        return Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 必须返回加密后的密码
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 根据业务逻辑判断
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 根据业务逻辑判断
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 根据业务逻辑判断
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // 用户是否激活
    }

    // 自定义扩展方法（非接口要求）
    public Long getUserId() {
        return user.getUserId();
    }
}

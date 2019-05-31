package com.zhaoming.web.manage.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * @author zm
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    Logger logger = LoggerFactory.getLogger(getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException{
        String msg = e.getMessage();
        if (e instanceof UsernameNotFoundException){
            msg = "用户找不到！";
        }else if (e instanceof BadCredentialsException){
            msg = "用户名或密码错误！";
        }else if (e instanceof DisabledException){
            msg = "用户被禁用！";
        }
        logger.error("login fail [message:{}]",msg);
        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/login?msg=" + URLEncoder.encode(msg,"utf-8"));
    }
}

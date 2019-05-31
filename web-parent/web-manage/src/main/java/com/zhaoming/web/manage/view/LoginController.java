package com.zhaoming.web.manage.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:shih
 * @description:
 * @date: 2018/3/8
 */
@Slf4j
@Controller
public class LoginController extends BaseView{

    private RequestCache requestCache = new HttpSessionRequestCache();

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("msg",request.getParameter("msg"));
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if (savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：" + targetUrl);
        }
        return toView("login", model);
    }
}

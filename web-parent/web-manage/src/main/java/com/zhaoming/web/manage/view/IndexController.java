package com.zhaoming.web.manage.view;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.web.manage.bean.ManagerProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

/**
 * 描述:
 * @author huhuixin
 * @create 2017/12/27 下午3:13
 */
@Slf4j
@Controller
public class IndexController extends BaseView{

    @GetMapping(value={"/","/index"})
    public ModelAndView index(Model model, Authentication authentication){
        try {
            ManagerProxy manager = (ManagerProxy) authentication.getPrincipal();
            model.addAttribute("user", manager);
            model.addAttribute("views", Authority.getAuthorities(manager.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(CommonConstant.SEPARATOR))));
            return toView("index", model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toView("login", model);
    }
}

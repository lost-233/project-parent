package com.zhaoming.web.manage.view;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.base.util.DateUtil;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述:
 *
 * @author huhuixin
 * @create 2017/12/29 上午10:14
 */
public class BaseView {

    ModelAndView toView(String viewPath, Model model){
        model.addAttribute("time", DateUtil.getNow());
        return new ModelAndView(viewPath, CommonConstant.MODEL, model);
    }

    ModelAndView error(String msg, Model model){
        model.addAttribute("msg", msg);
        return new ModelAndView("500", CommonConstant.MODEL, model);
    }
}

package com.zhaoming.web.manage.view;

import com.github.pagehelper.PageInfo;
import com.zhaoming.base.bean.PageCondition;
import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.web.manage.entity.Manager;
import com.zhaoming.web.manage.security.ManagerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author huhuixin
 */
@Controller
public class ManagerView extends BaseView{

    public static final String LIST = "/view/manager";

    @Autowired
    private ManagerUserDetailsService managerService;

    @GetMapping(LIST)
    public ModelAndView list(Model model, PageCondition condition){
        PageInfo page = managerService.getPage(condition);
        model.addAttribute(CommonConstant.PAGE, page);
        model.addAttribute(CommonConstant.CONDITION, condition);
        return toView("manager/list", model);
    }

    @GetMapping("/manager/add")
    public ModelAndView add(Model model){
        model.addAttribute("manager", new Manager());
        model.addAttribute("views", Authority.getAllView());
        return toView("manager/edit", model);
    }

    @GetMapping("/manager/update/{id}")
    public ModelAndView add(@PathVariable Integer id, Model model){
        Manager manager = managerService.getById(id);
        model.addAttribute("manager", manager);
        model.addAttribute("views", Authority.getAllView());
        return toView("manager/edit", model);
    }

    @GetMapping("/manager/change-password")
    public ModelAndView changePassword(Model model){
        return toView("manager/change-password", model);
    }
}

package com.zhaoming.web.manage.aspect;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.base.util.JsonUtil;
import com.zhaoming.base.util.ObjectUtil;
import com.zhaoming.bean.web.Result;
import com.zhaoming.web.manage.utils.ManagerContextHolder;
import com.zhaoming.web.manage.vo.CheckParamAble;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 异常处理
 *
 * @author hhx
 */
@Slf4j
@Aspect
@Component
public class ExceptionAspect {

    /**
     * 处理ajax请求的返回值
     * @param pjp pjp
     * @return null
     * @throws Throwable
     */
    @Around(value="execution(* com.zhaoming.web.manage.controller.*.* (..))")
    public Object handleController(ProceedingJoinPoint pjp) throws Throwable {
        try {
            // 校验参数
            for (Object o : pjp.getArgs()) {
                if(o instanceof CheckParamAble){
                    String checkResult = ((CheckParamAble) o).check();
                    if(!CheckParamAble.SUCCESS.equals(checkResult)){
                        return Result.error(checkResult);
                    }
                }
            }
            // 可视化参数
            String args = Stream.of(pjp.getArgs())
                    .map(ExceptionAspect::formatArg)
                    .collect(Collectors.joining(",", "[", "]"));
            log.info("managerName : {}, method : {}, args : {}.",
                    ManagerContextHolder.requireGetManagerRealName(), pjp.getSignature(), args);
            return pjp.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error : {} ", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    private static String formatArg(Object arg){
        try {
            if(ObjectUtil.isBaseType(arg.getClass())){
                return arg.toString();
            }else{
                if(arg instanceof MultipartFile || arg instanceof MultipartFile[]){
                    return "files";
                }
            }
            return JsonUtil.toJson(arg);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 处理页面异常
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value="execution(* com.zhaoming.web.manage.view.*.* (..))")
    public Object handleView(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error : {} ", e.getMessage());
            Model model = (Model) Stream.of(pjp.getArgs())
                    .filter(arg -> arg instanceof Model).findFirst().get();
            model.addAttribute("msg", e.getMessage());
            return new ModelAndView("500", CommonConstant.MODEL, model);
        }
    }
}

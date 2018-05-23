package com.permission.common;

import com.permission.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 * @author zt1994 2018/5/23 20:17
 */
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger("SpringExceptionResolver");

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        ModelAndView modelAndView;
        String defaultMsg = "System error";
        //.json .page
        //json数据以.json结尾
        if(url.endsWith(".json")){
            if(e instanceof PermissionException){
                JsonData result = JsonData.fail(e.getMessage());
                modelAndView = new ModelAndView("jsonView", result.toMap());
            }else {
                logger.error("unknown json exception,url:" + url, e);
                JsonData result = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("jsonView", result.toMap());
            }
        }else if(url.endsWith(".page")){ //请求page页面，用.page结尾
            logger.error("unknown page exception,url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("exception",result.toMap());
        }else {
            logger.error("unknown exception,url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("jsonView", result.toMap());
        }
        return modelAndView;
    }
}

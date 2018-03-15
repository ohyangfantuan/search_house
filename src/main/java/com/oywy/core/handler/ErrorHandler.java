package com.oywy.core.handler;

import com.oywy.core.common.ApiResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web错误全局处理
 * Created by oywy on 2018/3/14.
 */
@Controller
public class ErrorHandler implements ErrorController {
    private final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * 页面请求错误
     *
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPagehandle(HttpServletResponse response, Model model) {
        model.addAttribute("status", response.getStatus());
        return ERROR_PATH;
    }

    /**
     * 接口请求错误
     *
     * @param request
     * @return
     */
    @RequestMapping(ERROR_PATH)
    @ResponseBody
    public ApiResponse errorApihandle(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        status = status == null ? 500 : status;
        return ApiResponse.message(status, null);
    }

}

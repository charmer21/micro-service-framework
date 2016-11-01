package io.wang.framework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ErrorController {

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @GetMapping("/error/401")
    public Map<String, String> unauthorized(HttpServletRequest request) {
        String message = "";
        Throwable ex = (Throwable) request.getSession().getAttribute("error");
        if(ex != null) {
            message = ex.getMessage();
        }

        Map<String, String> result = new HashMap<>();
        result.put("code", "401");
        result.put("message", "未通过权限验证");
        result.put("error", message);
        return result;
    }

    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    @GetMapping("/error/503")
    public Map<String, String> serviceUnavailable(HttpServletRequest request) {
        String message = "";
        Throwable ex = (Throwable) request.getSession().getAttribute("error");
        if(ex != null) {
            message = ex.getMessage();
        }

        Map<String, String> result = new HashMap<>();
        result.put("code", "503");
        result.put("message", "请求过程中出现异常,请稍后再试");
        result.put("error", message);
        return result;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @GetMapping("/error/500")
    public Map<String, String> internalServerError(HttpServletRequest request) {
        String message = "";
        Throwable ex = (Throwable) request.getSession().getAttribute("error");
        if(ex != null) {
            message = ex.getMessage();
        }

        Map<String, String> result = new HashMap<>();
        result.put("code", "500");
        result.put("message", "服务异常");
        result.put("error", message);
        return result;
    }
}

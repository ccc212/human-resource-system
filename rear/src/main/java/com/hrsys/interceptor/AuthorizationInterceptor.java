package com.hrsys.interceptor;

import com.hrsys.context.BaseContext;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    /**
     * 鉴权逻辑
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("当前线程的id:" + Thread.currentThread().getId());

        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 1、从上下文中获取Claims对象
        Claims claims = BaseContext.getClaims();
        if (claims == null) {
            throw new BizException(StatusCodeEnum.NO_LOGIN);
        }

        // 2、获取请求路径
        String requestURI = request.getRequestURI();
        log.info("请求路径: {}", requestURI);

        // 3、根据请求路径进行鉴权
        if (requestURI.startsWith("/hr-record")) {
            // 检查是否具有管理员权限
            String role = claims.get("role", String.class);
            if (!"admin".equals(role)) {
                response.setStatus(403); // 禁止访问
                return false;
            }
        } else if (requestURI.startsWith("/user")) {
            // 检查是否具有用户权限
            String role = claims.get("role", String.class);
            if (!"user".equals(role)) {
                response.setStatus(403); // 禁止访问
                return false;
            }
            //TODO: 其他权限
        }

        // 4、通过，放行
        return true;
    }
}

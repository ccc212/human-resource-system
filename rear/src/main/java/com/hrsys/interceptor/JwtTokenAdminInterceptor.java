package com.hrsys.interceptor;

import com.hrsys.constant.JwtClaimsConstant;
import com.hrsys.config.properties.JwtProperties;
import com.hrsys.context.BaseContext;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 校验jwt
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

        // 1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        if (token == null) {
            throw new BizException(StatusCodeEnum.NO_LOGIN);
        }

        // 2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.ID).toString());
            log.info("当前员工id：", id);
//            将员工角色放入上下文
            BaseContext.setClaims(claims);
            // 3、通过，放行
            return true;
        } catch (Exception ex) {
            // 4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }


}

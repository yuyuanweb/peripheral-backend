package com.yupi.springbootinit.aop;

import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前登录用户并存到 threadLocal
 *
 * @author pine
 */
@Aspect
@Component
@Order(2)
public class GetLoginUserInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param point 切入点
     * @return {@link Object}
     * @throws Throwable 异常
     */
    @Around("execution(public * com.yupi.springbootinit.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        /*
         * 获取当前登录用户并存到 threadLocal
         *  用户未登录，也不抛出异常，保证不需登录的接口能够正常执行
         *  需要登录的接口在使用 ThreadLocalUtil 获取用户信息时抛出异常
         *
         */
        User loginUser = null;
        try {
            loginUser = userService.getLoginUser(request);
        } catch (Exception ignored) {
        }
        ThreadLocalUtil.setLoginUser(loginUser);
        try {
            return point.proceed();
        } finally {
            // 方法执行结束清空 threadLocal
            ThreadLocalUtil.removeAll();
        }

    }
}


package com.yupi.springbootinit.utils;


import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.User;

/**
 * @author pine
 */
public final class ThreadLocalUtil {
    private static final ThreadLocal<User> LOGIN_USER = ThreadLocal.withInitial(User::new);

    public static User getLoginUser() {
        User user = LOGIN_USER.get();
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    public static void setLoginUser(User user) {
        LOGIN_USER.set(user);
    }

    public static void removeLoginUser() {
        LOGIN_USER.remove();
    }

    /**
     * 清空当前线程的所有 ThreadLocal 变量
     */
    public static void removeAll() {
        removeLoginUser();
    }
}
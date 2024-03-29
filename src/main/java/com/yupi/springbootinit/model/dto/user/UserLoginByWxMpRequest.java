package com.yupi.springbootinit.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserLoginByWxMpRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 登录场景码
     */
    private String scene;
}

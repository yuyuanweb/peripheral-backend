package com.yupi.springbootinit.constant;

import com.yupi.springbootinit.model.enums.UserRoleEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 用户常量
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 系统用户 id
     *  todo 根据实际修改
     */
    long SYSTEM_USER_ID = 1L;

    //  region 权限

    /**
     * 默认权限
     */
    String DEFAULT_ROLE = "user";

    /**
     * VIP
     */
    String VIP_ROLE = "vip";

    /**
     * SVIP
     */
    String SVIP_ROLE = "svip";

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "admin";

    /**
     * 内部员工
     */
    String INTERNAL_ROLE = "internal";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    // endregion

    /**
     * VIP 角色枚举列表
     */
    List<UserRoleEnum> VIP_ROLE_ENUM_LIST = Arrays.asList(
            UserRoleEnum.VIP,
            UserRoleEnum.SVIP
    );
}

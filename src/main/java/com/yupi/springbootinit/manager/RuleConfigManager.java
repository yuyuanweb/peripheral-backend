package com.yupi.springbootinit.manager;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.Permission;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * 权限 配置管理
 *
 * @author daiyifei
 */
@Service
public class RuleConfigManager {

    private final static Permission permission;

    static {
        String json = ResourceUtil.readUtf8Str("biz/rule.json");
        permission = JSONUtil.toBean(json, Permission.class);
    }

    //获取permission
    public Permission getPermission() {
        return permission;
    }
}

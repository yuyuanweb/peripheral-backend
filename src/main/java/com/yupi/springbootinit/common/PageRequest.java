package com.yupi.springbootinit.common;

import lombok.Data;

import java.util.List;

/**
 * 分页请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 升序排序字段
     */
    private List<String> ascSortField;

    /**
     * 降序排序字段
     */
    private List<String> descSortField;



}

package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.peripheralInfo.PeripheralInfoAddRequest;
import com.yupi.springbootinit.model.dto.peripheralInfo.PeripheralInfoQueryRequest;
import com.yupi.springbootinit.model.dto.peripheralInfo.PeripheralInfoUpdateRequest;
import com.yupi.springbootinit.model.entity.PeripheralInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.enums.PeripheralInfoStatusEnum;
import com.yupi.springbootinit.model.vo.peripheralInfo.PeripheralInfoVO;
import com.yupi.springbootinit.service.PeripheralInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 周边接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@RestController
@RequestMapping("/peripheralInfo")
@Slf4j
public class PeripheralInfoController {


    @Resource
    private PeripheralInfoService peripheralInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param peripheralInfoAddRequest 产品添加请求
     * @param request                  请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addPeripheralInfo(@RequestBody PeripheralInfoAddRequest peripheralInfoAddRequest, HttpServletRequest request) {
        if (peripheralInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PeripheralInfo peripheralInfo = new PeripheralInfo();
        ThrowUtils.throwIf(ObjectUtils.isEmpty(peripheralInfoAddRequest.getType()), ErrorCode.PARAMS_ERROR, "分类不能为空");
        peripheralInfo.setType(peripheralInfoAddRequest.getType().get(0));
        BeanUtils.copyProperties(peripheralInfoAddRequest, peripheralInfo);
        peripheralInfoService.validPeripheralInfo(peripheralInfo, true);
        User loginUser = userService.getLoginUser(request);
        peripheralInfo.setUserId(loginUser.getId());
        boolean result = peripheralInfoService.save(peripheralInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newPeripheralInfoId = peripheralInfo.getId();
        return ResultUtils.success(newPeripheralInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除请求
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deletePeripheralInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        PeripheralInfo oldPeripheralInfo = peripheralInfoService.getById(id);
        if (oldPeripheralInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean b = peripheralInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param peripheralInfoUpdateRequest 产品更新请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePeripheralInfo(@RequestBody PeripheralInfoUpdateRequest peripheralInfoUpdateRequest) {
        if (peripheralInfoUpdateRequest == null || peripheralInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PeripheralInfo peripheralInfo = new PeripheralInfo();
        ThrowUtils.throwIf(ObjectUtils.isEmpty(peripheralInfoUpdateRequest.getType()), ErrorCode.PARAMS_ERROR, "分类不能为空");
        peripheralInfo.setType(peripheralInfoUpdateRequest.getType().get(0));
        BeanUtils.copyProperties(peripheralInfoUpdateRequest, peripheralInfo);
        // 参数校验
        peripheralInfoService.validPeripheralInfo(peripheralInfo, false);
        long id = peripheralInfoUpdateRequest.getId();
        // 判断是否存在
        PeripheralInfo oldPeripheralInfo = peripheralInfoService.getById(id);
        if (oldPeripheralInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = peripheralInfoService.updateById(peripheralInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id 编号
     * @return {@link BaseResponse}<{@link PeripheralInfo}>
     */
    @GetMapping("/get")
    public BaseResponse<PeripheralInfo> getPeripheralInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PeripheralInfo peripheralInfo = peripheralInfoService.getById(id);
        if (peripheralInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(peripheralInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param peripheralInfoQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link List}<{@link PeripheralInfo}>>
     */
    @PostMapping("/list")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<PeripheralInfo>> listPeripheralInfo(@RequestBody PeripheralInfoQueryRequest peripheralInfoQueryRequest) {
        List<PeripheralInfo> peripheralInfoList = peripheralInfoService.list(getQueryWrapper(peripheralInfoQueryRequest));
        return ResultUtils.success(peripheralInfoList);
    }

    /**
     * 分页获取列表（仅管理员可使用）
     *
     * @param peripheralInfoQueryRequest 产品查询请求
     * @param request                    请求
     * @return {@link BaseResponse}<{@link Page}<{@link PeripheralInfo}>>
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<PeripheralInfo>> listPeripheralInfoByPage(@RequestBody PeripheralInfoQueryRequest peripheralInfoQueryRequest,
                                                                       HttpServletRequest request) {
        long current = peripheralInfoQueryRequest.getCurrent();
        long size = peripheralInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<PeripheralInfo> peripheralInfoPage = peripheralInfoService.page(new Page<>(current, size),
                getQueryWrapper(peripheralInfoQueryRequest));
        return ResultUtils.success(peripheralInfoPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param peripheralInfoQueryRequest 产品查询请求
     * @return {@link BaseResponse}<{@link Page}<{@link PeripheralInfoVO}>>
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<PeripheralInfoVO>> listPeripheralInfoVOByPage(@RequestBody PeripheralInfoQueryRequest peripheralInfoQueryRequest) {
        long current = peripheralInfoQueryRequest.getCurrent();
        long size = peripheralInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<PeripheralInfo> queryWrapper = this.getQueryWrapper(peripheralInfoQueryRequest);
        // 只返回可以浏览的周边
        queryWrapper.eq("status", PeripheralInfoStatusEnum.ENABLED.getValue());
        queryWrapper.orderByDesc("createTime");
        Page<PeripheralInfo> peripheralInfoPage = peripheralInfoService.page(new Page<>(current, size),
                queryWrapper);
        return ResultUtils.success(peripheralInfoService.getPeripheralInfoVOPage(peripheralInfoPage));
    }

    @GetMapping("/list/type")
    public BaseResponse<List<String>> listPeripheralInfoType() {
        QueryWrapper<PeripheralInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct type");
        List<String> typeList = peripheralInfoService.listObjs(queryWrapper, obj -> (String) obj);
        return ResultUtils.success(typeList);
    }


    // endregion

    /**
     * 获取查询包装类
     *
     * @param peripheralInfoQueryRequest 产品查询请求
     * @return {@link QueryWrapper}<{@link PeripheralInfo}>
     */
    private QueryWrapper<PeripheralInfo> getQueryWrapper(PeripheralInfoQueryRequest peripheralInfoQueryRequest) {
        if (peripheralInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = peripheralInfoQueryRequest.getId();
        String name = peripheralInfoQueryRequest.getName();
        Integer price = peripheralInfoQueryRequest.getPrice();
        Integer stock = peripheralInfoQueryRequest.getStock();
        List<String> typeList = peripheralInfoQueryRequest.getType();

        Integer status = peripheralInfoQueryRequest.getStatus();
        Long userId = peripheralInfoQueryRequest.getUserId();
        List<String> ascSortField = peripheralInfoQueryRequest.getAscSortField();
        List<String> descSortField = peripheralInfoQueryRequest.getDescSortField();

        QueryWrapper<PeripheralInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(price != null, "price", price);
        queryWrapper.eq(stock != null, "stock", stock);

        // 取出列表中的第一个元素（只有一个分类）
        if (ObjectUtils.isNotEmpty(typeList)) {
            String type = typeList.get(0);
            queryWrapper.eq(ObjectUtils.isNotEmpty(type), "type", type);
        }
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(userId != null, "userId", userId);

        // MyBatis-plus 自带 columnToSqlSegment 方法进行注入过滤处理，不需要SqlUtils.validSortField(sortField)
        boolean ascValid = ascSortField != null && !ascSortField.isEmpty();
        boolean descValid = descSortField != null && !descSortField.isEmpty();
        queryWrapper.orderByAsc(ascValid, ascSortField);
        queryWrapper.orderByDesc(descValid, descSortField);

        return queryWrapper;

    }

}

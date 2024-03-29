package com.yupi.springbootinit.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.manager.RuleConfigManager;
import com.yupi.springbootinit.mapper.PeripheralInfoMapper;
import com.yupi.springbootinit.model.Permission;
import com.yupi.springbootinit.model.entity.PeripheralInfo;
import com.yupi.springbootinit.model.vo.peripheralInfo.PeripheralInfoVO;
import com.yupi.springbootinit.service.PeripheralInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daiyifei
 * @description 针对表【peripheral_info(周边)】的数据库操作Service实现
 * @createDate 2024-01-29 17:19:54
 */
@Service
@Slf4j
public class PeripheralInfoServiceImpl extends ServiceImpl<PeripheralInfoMapper, PeripheralInfo>
        implements PeripheralInfoService {


    @Resource
    private RuleConfigManager ruleConfigManager;

    @Override
    public void validPeripheralInfo(PeripheralInfo peripheralInfo, boolean add) {
        if (peripheralInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = peripheralInfo.getName();
        Integer stock = peripheralInfo.getStock();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name), ErrorCode.PARAMS_ERROR,"周边名称不能为空");
        }
        // 有参数则校验
        ThrowUtils.throwIf(stock == null || stock < 0, ErrorCode.PARAMS_ERROR,"库存不能小于0");
        if (StringUtils.isNotBlank(name) && name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

    @Override
    public Page<PeripheralInfoVO> getPeripheralInfoVOPage(Page<PeripheralInfo> peripheralInfoPage) {

        List<PeripheralInfo> peripheralInfoList = peripheralInfoPage.getRecords();
        Page<PeripheralInfoVO> drawAppVOPage = new Page<>(peripheralInfoPage.getCurrent(), peripheralInfoPage.getSize(), peripheralInfoPage.getTotal());
        if (CollectionUtils.isEmpty(peripheralInfoList)) {
            return drawAppVOPage;
        }
        // 填充信息
        List<PeripheralInfoVO> drawAppVOList = peripheralInfoList.stream().map(this::getPeripheralInfoVO).collect(Collectors.toList());
        drawAppVOPage.setRecords(drawAppVOList);
        return drawAppVOPage;
    }

    @Override
    public PeripheralInfoVO getPeripheralInfoVO(PeripheralInfo peripheralInfo) {
        if (peripheralInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 转换VO
        PeripheralInfoVO peripheralInfoVO = new PeripheralInfoVO();
        String permission = peripheralInfo.getPermission();
        //降级策略
        if (StringUtils.isBlank(permission)) {
            Permission globalPermission = ruleConfigManager.getPermission();
            List<String> sensitiveFields = globalPermission.getSensitiveFields();
            BeanUtils.copyProperties(peripheralInfo, peripheralInfoVO,sensitiveFields.toArray(new String[0]));
            return peripheralInfoVO;
        }
        // 判断展示字段权限
        Permission rule = JSONUtil.toBean(permission, Permission.class);
        boolean publicView = rule.isPublicView();

        List<String> sensitiveFields = rule.getSensitiveFields();
        if(CollectionUtils.isEmpty(sensitiveFields)) {
            BeanUtils.copyProperties(peripheralInfo, peripheralInfoVO);
            return peripheralInfoVO;
        }

        // 如果启用权限限制，那么就返回脱敏字段后的数据
        if(publicView){
            BeanUtils.copyProperties(peripheralInfo, peripheralInfoVO,sensitiveFields.toArray(new String[0]));
        }else{
            BeanUtils.copyProperties(peripheralInfo, peripheralInfoVO);
        }
        return peripheralInfoVO;
    }


}





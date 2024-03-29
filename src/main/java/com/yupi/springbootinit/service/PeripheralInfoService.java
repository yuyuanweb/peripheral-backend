package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.PeripheralInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.vo.peripheralInfo.PeripheralInfoVO;

/**
* @author daiyifei
* @description 针对表【peripheral_info(周边)】的数据库操作Service
* @createDate 2024-01-29 17:19:54
*/
public interface PeripheralInfoService extends IService<PeripheralInfo> {


    void validPeripheralInfo(PeripheralInfo peripheralInfo, boolean add);


    Page<PeripheralInfoVO> getPeripheralInfoVOPage(Page<PeripheralInfo> peripheralInfoPage);

    PeripheralInfoVO getPeripheralInfoVO(PeripheralInfo peripheralInfo);
}

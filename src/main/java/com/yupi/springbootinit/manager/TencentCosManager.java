package com.yupi.springbootinit.manager;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.config.TencentCosConfig;
import com.yupi.springbootinit.constant.FileConstant;
import com.yupi.springbootinit.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * 操作 Tencent Cos 对象存储
 *
 * @author yupi
 */
@Component
public class TencentCosManager {

    @Resource
    private TencentCosConfig tencentCosConfig;

    @Resource
    private COSClient cosClient;

    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(tencentCosConfig.getBucket(), key,
                new File(localFilePath));
        return cosClient.putObject(putObjectRequest);
    }

    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(tencentCosConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }


}

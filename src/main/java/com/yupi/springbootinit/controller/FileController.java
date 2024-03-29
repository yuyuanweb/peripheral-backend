package com.yupi.springbootinit.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.FileConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.manager.TencentCosManager;
import com.yupi.springbootinit.model.dto.file.Base64UploadFileRequest;
import com.yupi.springbootinit.model.dto.file.UploadFileRequest;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.enums.FileUploadBizEnum;
import com.yupi.springbootinit.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 文件接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private TencentCosManager tencentCosManager;

    /**
     * 文件上传
     *
     * @param multipartFile     multipart 文件
     * @param uploadFileRequest 上传文件请求
     * @return {@link BaseResponse}<{@link String}>
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                           UploadFileRequest uploadFileRequest) {
        return ResultUtils.success(uploadFileUser(multipartFile, uploadFileRequest, null));
    }

    public String uploadFileUser(MultipartFile multipartFile,
                                 UploadFileRequest uploadFileRequest,
                                 User loginUser) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        if (loginUser == null) {
            loginUser = ThreadLocalUtil.getLoginUser();
        }
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/zhoubian/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            tencentCosManager.putObject(filepath, file);
            // 返回可访问地址
            return FileConstant.COS_HOST + filepath;
        } catch (Exception e) {
            log.error("文件上传失败 ", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * base64 格式的文件上传
     *
     * @param base64UploadFileRequest base64 格式的数据 和 biz
     * @return {@link BaseResponse}<{@link String}>
     */
    @PostMapping("/upload/base64")
    public BaseResponse<String> uploadFileByBase64(@RequestBody Base64UploadFileRequest base64UploadFileRequest) {
        return ResultUtils.success(uploadFileByBase64User(base64UploadFileRequest, null));
    }

    public String uploadFileByBase64User(@RequestBody Base64UploadFileRequest base64UploadFileRequest, User loginUser) {
        String fileBase64 = base64UploadFileRequest.getFileBase64();
        String biz = base64UploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (loginUser == null) {
            loginUser = ThreadLocalUtil.getLoginUser();
        }
        String uuid = RandomStringUtils.randomAlphanumeric(15);
        String filename = uuid + ".webp";
        String filepath = String.format("/laoyujianli/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
        File file = null;
        FileOutputStream fos = null;
        try {
            file = File.createTempFile(filepath, null);
            byte[] byteData = Base64.decode(fileBase64);
            final long ONE_M = 1024 * 1024L;
            if (byteData.length > 2 * ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 2M");
            }
            fos = new FileOutputStream(file);
            fos.write(byteData, 0, byteData.length);
            fos.flush();

            tencentCosManager.putObject(filepath, file);

            // 返回可访问地址
            return FileConstant.COS_HOST + filepath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
        } finally {
            if (file != null) {
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile     multipart 文件
     * @param fileUploadBizEnum 文件上传业务类型枚举
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        switch (fileUploadBizEnum) {
            case USER_AVATAR:
            default:
                if (fileSize > ONE_M) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
                }
                if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
                }
                break;
        }
    }
}

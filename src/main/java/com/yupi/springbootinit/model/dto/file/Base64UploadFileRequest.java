package com.yupi.springbootinit.model.dto.file;

import lombok.Data;

@Data
public class Base64UploadFileRequest {
    private String fileBase64;

    private String biz;
}

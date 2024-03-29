package com.yupi.springbootinit.model.dto.applyrecords;


import lombok.Data;

import java.util.List;

@Data
public class ApplyStatusRequest {
    private List<Long> ids;
    private static final long serialVersionUID = 1L;
}

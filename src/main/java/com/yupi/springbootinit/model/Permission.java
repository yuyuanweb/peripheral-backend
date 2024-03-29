package com.yupi.springbootinit.model;


import lombok.Data;

import java.util.List;

/**
 * @author daiyifei
 */
@Data
public class Permission {
    private boolean publicView;

    private List<String> sensitiveFields;
}

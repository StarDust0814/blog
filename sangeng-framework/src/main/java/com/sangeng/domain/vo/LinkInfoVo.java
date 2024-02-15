package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkInfoVo {
    private String address;
    private String description;
    private String id;
    private String logo;
    private String name;
    private String status;
}

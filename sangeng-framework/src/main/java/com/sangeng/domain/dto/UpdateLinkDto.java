package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkDto {
    private String name;
    private String description;
    private String address;
    private String logo;
    private String status;
    private String id;
}

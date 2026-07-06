package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchAssociatedUsersForm {
    @NotNull(message = "roleId不能为空")
    @Min(value = 1, message = "roleId不能小于1")
    private Long roleId;

    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Min(value = 1, message = "size不能小于1")
    private Integer size;
}

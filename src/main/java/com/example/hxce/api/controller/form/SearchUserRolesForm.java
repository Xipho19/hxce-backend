package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchUserRolesForm {
    @NotNull(message = "userId不能为空")
    @Min(value = 1,message = "userId不能小于1")
    private Long userId;
}

package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeleteUserByIdsForm {
    @NotEmpty(message = "ids不能为空")
    private Long[] ids;
}


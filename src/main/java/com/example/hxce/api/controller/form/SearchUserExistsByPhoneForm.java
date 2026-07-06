package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchUserExistsByPhoneForm {

        @NotBlank(message = "手机号不能为空")
        @Pattern(regexp = "^1[345678]\\d{9}$", message = "手机号格式错误")
        private String phone;

        @Min(value = 1, message = "id不能小于1")
        private Long id;

}

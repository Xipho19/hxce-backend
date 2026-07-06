package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateStudentForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Integer id;

    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "name不正确")
    private String name;

    @Pattern(regexp = "^男|女$", message = "gender不正确")
    private String gender;

    @Pattern(regexp = "^1[34578][0-9]{9}$", message = "phone不正确")
    private String phone;

    @Email(message = "email不正确")
    private String email;

    @Pattern(regexp = "^[01]$", message = "status不正确")
    private String status;
}

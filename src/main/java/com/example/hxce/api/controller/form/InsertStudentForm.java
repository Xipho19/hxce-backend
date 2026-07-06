package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InsertStudentForm {
  @NotEmpty(message = "name不能为空")
  @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "name不正确")
  private String name;

  @NotEmpty(message = "gender不能为空")
  @Pattern(regexp = "^男|女$", message = "gender不正确")
  private String gender;

  @NotEmpty(message = "phone不能为空")
  @Pattern(regexp = "^1[34578][0-9]{9}$", message = "phone不正确")
  private String phone;

  @NotEmpty(message = "email不能为空")
  @Email(message = "email不正确")
  private String email;
}

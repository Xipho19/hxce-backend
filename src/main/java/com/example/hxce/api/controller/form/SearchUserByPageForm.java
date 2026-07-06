package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SearchUserByPageForm {
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,20}$", message = "name格式不正确")
    private String name;

    @Min(value = 1, message = "roleId必须大于0")
    private Long roleId;

    @Range(min = 1, max = 3, message = "状态只能是1~3之间的整数")
    private Byte status;

    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page必须大于0")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Range(min = 1, max = 50, message = "size必须在1~50之间")
    private Integer size;
}

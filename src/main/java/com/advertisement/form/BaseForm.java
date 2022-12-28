package com.advertisement.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BaseForm {

    @Range(min = 1,max = 100, message = "每页大小必须在1~100之间")
    /** 每页记录数 */
    public Integer limit = 10;

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能小于零")
    /** 当前页码 */
    public Integer page;

    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    /** 开始时间 */
    public Date startTime;
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    /** 结束时间 */
    public Date endTime;
}

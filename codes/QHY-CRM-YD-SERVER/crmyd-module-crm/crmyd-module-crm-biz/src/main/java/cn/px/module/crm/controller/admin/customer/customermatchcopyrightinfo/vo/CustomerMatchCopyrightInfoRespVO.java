package cn.px.module.crm.controller.admin.customer.customermatchcopyrightinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - CRM-客户管理-匹配数据-软件著作信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerMatchCopyrightInfoRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25423")
    @ExcelProperty("编号")
    private Long id;
    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25233")
    @ExcelProperty("客户id")
    private Long customerId;
    @Schema(description = "登记日期")
    @ExcelProperty("登记日期")
    private Long regtime;

    @Schema(description = "首次发表日期")
    @ExcelProperty("首次发表日期")
    private Long publishtime;

    @Schema(description = "全称", example = "张三")
    @ExcelProperty("全称")
    private String fullname;

    @Schema(description = "全称")
    @ExcelProperty("全称")
    private Integer total;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    @Schema(description = "获取的总条数")
    private Integer totals;
}

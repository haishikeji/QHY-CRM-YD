package cn.px.module.crm.controller.admin.customer.customerlabelrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - CRM 客户标签数据修改记录新增/修改 Request VO")
@Data
public class CustomerLabelRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private Long id;

    @Schema(description = "客户标签id", example = "10585")
    @NotNull(message = "客户标签id不能为空")
    private Long labelId;

    @Schema(description = "标签值的等级")
    private String level;

    @Schema(description = "标签值")
    @NotNull(message = "标签值不能为空")
    private String value;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;
    @Schema(description = "显示的值")
    private String valStr;

    private String dataType;
}

package cn.px.module.crm.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 标签新增/修改 Request VO")
@Data
public class SysTagsSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30199")
    private Long id;

    @Schema(description = "标签名称", example = "芋艿")
    private String name;

    @Schema(description = "数据类型", example = "2")
    private String dataType;

    @Schema(description = "子数据类型", example = "2")
    private String subDataType;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "设为可实现标签")
    private Integer achievable;

    @Schema(description = "标签组", example = "13201")
    private Long groupId;

    @Schema(description = "创建人")
    private Long createBy;

    @Schema(description = "修改人")
    private Long updateBy;

    @Schema(description = "是否删除 0正常，1 删除")
    private Integer isDelete;

    @Schema(description = "数据所属年份")
    private Integer isDataBelongs;

    @Schema(description = "父级ID", example = "26486")
    private Long parentId;

}
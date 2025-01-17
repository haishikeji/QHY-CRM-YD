package cn.px.module.crm.controller.admin.publicdatagroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 公开数据接口新增/修改 Request VO")
@Data
public class PublicDataGroupSaveReqVO {

    @Schema(description = "主键Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6330")
    private Integer id;

    @Schema(description = "接入平台 ")
    private String interfacePlatform;

    @Schema(description = "接口名称", example = "王五")
    private String interfaceName;

    @Schema(description = "接口地址 ", example = "https://www.iocoder.cn")
    private String interfaceUrl;

    @Schema(description = "接口描述")
    private String remark;

    @Schema(description = "详细数据接口表名", example = "李四")
    private String detailTableName;

    @Schema(description = "图标", example = "https://www.iocoder.cn")
    private String iconUrl;

    @Schema(description = "排序 ")
    private String sort;

}
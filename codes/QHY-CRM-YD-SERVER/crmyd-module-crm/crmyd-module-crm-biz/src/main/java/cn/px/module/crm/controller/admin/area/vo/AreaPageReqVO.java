package cn.px.module.crm.controller.admin.area.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.px.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 地区代码映射分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AreaPageReqVO extends PageParam {

    @Schema(description = "地区名称", example = "赵六")
    private String name;

    @Schema(description = "代码")
    private String code;

    @Schema(description = "父级代码", example = "15844")
    private String parentId;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "状态")
    private Integer isOpen;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "拼音")
    private String pinyin;

    @Schema(description = "0为未开放，1为已开放", example = "2")
    private Boolean status;

    @Schema(description = "是否热度，0否，1是")
    private Integer hot;

}

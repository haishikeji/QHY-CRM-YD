package cn.px.module.crm.controller.admin.customer.customerpublicpatentinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.px.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.px.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM-客户管理-公开数据-专利信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerPublicPatentInfoPageReqVO extends PageParam {

    @Schema(description = "客户id", example = "9955")
    private Long customerId;

    @Schema(description = "专利评级")
    private String rank;

    @Schema(description = "LOC分类号")
    private String loc;

    @Schema(description = "法律状态", example = "2")
    private String legalStatus;

    @Schema(description = "专利类型", example = "1")
    private String type;

    @Schema(description = "专利名称")
    private String title;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "申请人")
    private String applicant;

    @Schema(description = "申请日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] applicationDate;

    @Schema(description = "主分类号")
    private String mainIpc;

    @Schema(description = "申请号")
    private String applicationNumber;

    @Schema(description = "文献号")
    private String documentNumber;

    @Schema(description = "文献日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] documentDate;

    @Schema(description = "发明人")
    private String inventor;

    @Schema(description = "申请人类型，包括：学校、科研院所、企业、其他 四种", example = "1")
    private String applicantType;

    @Schema(description = "ipc分类号")
    private String ipc;

    @Schema(description = "cpc分类号")
    private String cpc;

    @Schema(description = "imagePath")
    private String imagePath;

    @Schema(description = "天眼查id", example = "2423")
    private String tId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}

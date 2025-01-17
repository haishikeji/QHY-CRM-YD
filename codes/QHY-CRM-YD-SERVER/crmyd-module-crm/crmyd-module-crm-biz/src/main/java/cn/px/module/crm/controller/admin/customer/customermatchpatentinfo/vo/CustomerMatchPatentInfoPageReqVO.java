package cn.px.module.crm.controller.admin.customer.customermatchpatentinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.px.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.px.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM-客户管理-匹配数据-专利信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerMatchPatentInfoPageReqVO extends PageParam {

    @Schema(description = "客户id", example = "31934")
    private Long customerId;

    @Schema(description = "专利总数")
    private Integer total;

    @Schema(description = "发明授权专利总数")
    private Integer grantTotal;

    @Schema(description = "发明授权专利title 拼接")
    private String grantTitle;

    @Schema(description = "发明授权专利summary 拼接")
    private String grantSummary;

    @Schema(description = "发明授权专利application_date 最小值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] grantApplicationDate;

    @Schema(description = "发明授权专利document_date 最大值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] grantDocumentDate;

    @Schema(description = "发明公开专利总数")
    private Integer publicationTotal;

    @Schema(description = "发明公开专利title 拼接")
    private String publicationTitle;

    @Schema(description = "发明公开专利summary 拼接")
    private String publicationSummary;

    @Schema(description = "发明公开专利application_date 最小值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] publicationApplicationDate;

    @Schema(description = "发明公开专利document_date 最大值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] publicationDocumentDate;

    @Schema(description = "实用新型专利总数")
    private Integer utilityTotal;

    @Schema(description = "实用新型专利title 拼接")
    private String utilityTitle;

    @Schema(description = "实用新型专利summary 拼接")
    private String utilitySummary;

    @Schema(description = "实用新型专利application_date 最小值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] utilityApplicationDate;

    @Schema(description = "实用新型专利document_date 最大值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] utilityDocumentDate;

    @Schema(description = "外观设计专利总数")
    private Integer industrialTotal;

    @Schema(description = "外观设计专利title 拼接")
    private String industrialTitle;

    @Schema(description = "外观设计专利summary 拼接")
    private String industrialSummary;

    @Schema(description = "外观设计专利application_date 最小值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] industrialApplicationDate;

    @Schema(description = "外观设计专利document_date 最大值")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] industrialDocumentDate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;


    @Schema(description = "获取的总条数")
    private Integer totals;
}

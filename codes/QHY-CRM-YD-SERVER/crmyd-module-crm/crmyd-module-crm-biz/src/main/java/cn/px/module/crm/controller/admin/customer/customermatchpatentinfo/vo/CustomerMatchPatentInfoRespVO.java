package cn.px.module.crm.controller.admin.customer.customermatchpatentinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static cn.px.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM-客户管理-匹配数据-专利信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerMatchPatentInfoRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10315")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31934")
    @ExcelProperty("客户id")
    private Long customerId;

    @Schema(description = "专利总数")
    @ExcelProperty("专利总数")
    private Integer total;


    @Schema(description = "发明授权专利总数")
    @ExcelProperty("发明授权专利总数")
    private Integer grantTotal;

    @Schema(description = "发明授权专利title 拼接")
    @ExcelProperty("发明授权标题")
    private String grantTitle;

    @Schema(description = "发明授权专利summary 拼接")
    @ExcelProperty("发明授权摘要")
    private String grantSummary;

    @Schema(description = "发明授权专利application_date 最小值")
    @ExcelProperty("发明授权专利application_date 最小值")
    private String[] grantApplicationDate;

    @Schema(description = "发明授权专利document_date 最大值")
    @ExcelProperty("发明授权专利document_date 最大值")
    private String[] grantDocumentDate;

    @Schema(description = "发明公开专利总数")
    @ExcelProperty("发明公开专利总数")
    private Integer publicationTotal;

    @Schema(description = "发明公开专利title 拼接")
    @ExcelProperty("发明公开专利title")
    private String publicationTitle;

    @Schema(description = "发明公开专利summary 拼接")
    @ExcelProperty("发明公开专利summary")
    private String publicationSummary;

    @Schema(description = "发明公开专利application_date 最小值")
    @ExcelProperty("发明公开专利application_date")
    private String[] publicationApplicationDate;

    @Schema(description = "发明公开专利document_date 最大值")
    @ExcelProperty("发明公开专利document_date")
    private String[] publicationDocumentDate;


    @Schema(description = "实用新型专利总数")
    @ExcelProperty("实用新型专利总数")
    private Integer utilityTotal;

    @Schema(description = "实用新型专利title 拼接")
    @ExcelProperty("实用新型专利title 拼接")
    private String utilityTitle;

    @Schema(description = "实用新型专利summary 拼接")
    @ExcelProperty("实用新型专利summary 拼接")
    private String utilitySummary;

    @Schema(description = "实用新型专利application_date 最小值")
    @ExcelProperty("实用新型专利application_date 最小值")
    private String utilityApplicationDate;

    @Schema(description = "实用新型专利document_date 最大值")
    @ExcelProperty("实用新型专利document_date 最大值")
    private String utilityDocumentDate;

    @Schema(description = "外观设计专利总数")
    @ExcelProperty("外观设计专利总数")
    private Integer industrialTotal;

    @Schema(description = "外观设计专利title 拼接")
    @ExcelProperty("外观设计专利title 拼接")
    private String industrialTitle;

    @Schema(description = "外观设计专利summary 拼接")
    @ExcelProperty("外观设计专利summary 拼接")
    private String industrialSummary;

    @Schema(description = "外观设计专利application_date 最小值")
    @ExcelProperty("外观设计专利application_date 最小值")
    private String industrialApplicationDate;

    @Schema(description = "外观设计专利document_date 最大值")
    @ExcelProperty("外观设计专利document_date 最大值")
    private String industrialDocumentDate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    @Schema(description = "获取的总条数")
    private Integer totals;
}

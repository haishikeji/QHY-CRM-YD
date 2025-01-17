package cn.px.module.crm.dal.dataobject.searchenterpriselog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.px.framework.mybatis.core.dataobject.BaseDO;

/**
 * 天眼查企业名称搜索记录 DO
 *
 * @author 品讯科技
 */
@TableName("crm_search_enterprise_log")
@KeySequence("crm_search_enterprise_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEnterpriseLogDO extends BaseDO {

    /**
     * 参数主键
     */
    @TableId
    private Long id;
    /**
     * 企业状态
     */
    @TableField("regStatus")
    private String regStatus;
    /**
     * 成立日期
     */
    @TableField("estiblishTime")
    private String estiblishTime;
    /**
     * 企业名称
     */
    private String name;
    /**
     * 统一社会信用代码
     */
    @TableField("creditCode")
    private String creditCode;
    /**
     * 注册资本
     */
    @TableField("regCapital")
    private String regCapital;
    /**
     * 机构类型
     */
    @TableField("companyType")
    private String companyType;
    /**
     * 注册号
     */
    @TableField("regNumber")
    private String regNumber;
    /**
     * 组织机构代码
     */
    @TableField("orgNumber")
    private String orgNumber;
    /**
     * 类型
     */
    private String type;
    /**
     * 省份
     */
    private String base;
    /**
     * 法人
     */
    @TableField("legalPersonName")
    private String legalPersonName;

}

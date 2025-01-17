package cn.px.module.crm.dal.dataobject.customer.customerpublicpatentinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.px.framework.mybatis.core.dataobject.BaseDO;

/**
 * CRM-客户管理-公开数据-专利信息 DO
 *
 * @author 品讯科技
 */
@TableName("crm_customer_public_patent_info")
@KeySequence("crm_customer_public_patent_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPublicPatentInfoDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 专利评级
     */
    private String rank;
    /**
     * LOC分类号
     */
    private String loc;
    /**
     * 法律状态
     */
    private String legalStatus;
    /**
     * 专利类型
     */
    private String type;
    /**
     * 专利名称
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 申请日期
     */
    private String applicationDate;
    /**
     * 主分类号
     */
    private String mainIpc;
    /**
     * 申请号
     */
    private String applicationNumber;
    /**
     * 文献号
     */
    private String documentNumber;
    /**
     * 文献日期
     */
    private String documentDate;
    /**
     * 发明人
     */
    private String inventor;
    /**
     * 申请人类型，包括：学校、科研院所、企业、其他 四种
     */
    private String applicantType;
    /**
     * ipc分类号
     */
    private String ipc;
    /**
     * cpc分类号
     */
    private String cpc;
    /**
     * imagePath
     */
    private String imagePath;
    /**
     * 天眼查id
     */
    private String tId;

}

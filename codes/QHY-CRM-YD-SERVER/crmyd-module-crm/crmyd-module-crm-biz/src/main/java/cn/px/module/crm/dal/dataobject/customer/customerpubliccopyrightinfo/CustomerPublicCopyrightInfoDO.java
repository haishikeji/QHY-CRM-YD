package cn.px.module.crm.dal.dataobject.customer.customerpubliccopyrightinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.px.framework.mybatis.core.dataobject.BaseDO;

/**
 * CRM-客户管理-公开数据-软件著作信息 DO
 *
 * @author 品讯科技
 */
@TableName("crm_customer_public_copyright_info")
@KeySequence("crm_customer_public_copyright_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPublicCopyrightInfoDO extends BaseDO {

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
     * 登记日期
     */
    private Long regtime;
    /**
     * 首次发表日期
     */
    private Long publishtime;

    /**
     * 著作权人
     */
    private String authorNationality;
    /**
     * 简称
     */
    private String simplename;
    /**
     * 天眼查连接
     */
    private String connList;
    /**
     * 登记号
     */
    private String regnum;
    /**
     * 分类号
     */
    private String catnum;
    /**
     * 天眼查id
     */
    private String tid;
    /**
     * 唯一标识符
     */
    private String uni;
    /**
     * 批准日期
     */
    private Long eventTime;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 版本号
     */
    private String version;

}

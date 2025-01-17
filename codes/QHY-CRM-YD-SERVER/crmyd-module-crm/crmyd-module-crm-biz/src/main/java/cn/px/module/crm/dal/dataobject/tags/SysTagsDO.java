package cn.px.module.crm.dal.dataobject.tags;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.fhs.core.trans.vo.TransPojo;
import lombok.*;

import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.px.framework.mybatis.core.dataobject.BaseDO;

/**
 * 标签 DO
 *
 * @author 品讯科技
 */
@TableName("back_sys_tags")
@Data
@ToString(callSuper = true)
@Builder
@DS("slave")
@NoArgsConstructor
@AllArgsConstructor
public class SysTagsDO implements Serializable, TransPojo {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 子数据类型
     */
    private String subDataType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 设为可实现标签
     */
    private Integer achievable;
    /**
     * 标签组
     */
    private Long groupId;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 修改人
     */
    private Long updateBy;
    /**
     * 是否删除 0正常，1 删除
     */
    private Integer isDelete;
    /**
     * 数据所属年份
     */
    private Integer isDataBelongs;
    /**
     * 父级ID
     */
    private Long parentId;

}
package cn.px.module.system.dal.dataobject.industry;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.px.framework.mybatis.core.dataobject.BaseDO;

/**
 * 国家行业类别表（来源国家统计局数据） DO
 *
 * @author 品讯科技
 */
@TableName("system_industry")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndustryDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类别代码
     */
    private String code;
    /**
     * 类别名字
     */
    private String name;
    /**
     * 父类别代码
     */
    private String parentCode;
    /**
     * 0 门类 1 大类 2 中类 3小类
     */
    private Integer type;

}

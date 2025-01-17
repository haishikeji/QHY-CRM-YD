package cn.px.module.crm.dal.mysql.publicdatafield;

import java.util.*;

import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.module.crm.controller.admin.tags.vo.SysTagConfigVO;
import cn.px.module.crm.dal.dataobject.publicdatafield.PublicDataFieldDO;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;
import cn.px.module.crm.controller.admin.publicdatafield.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 公开数据字段 Mapper
 *
 * @author 品讯科技
 */
@Mapper
@InterceptorIgnore(tenantLine = "1")
public interface PublicDataFieldMapper extends BaseMapperX<PublicDataFieldDO> {

    default PageResult<PublicDataFieldDO> selectPage(PublicDataFieldPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicDataFieldDO>()
                .eqIfPresent(PublicDataFieldDO::getField, reqVO.getField())
                .eqIfPresent(PublicDataFieldDO::getType, reqVO.getType())
                .likeIfPresent(PublicDataFieldDO::getFieldName, reqVO.getFieldName())
                .eqIfPresent(PublicDataFieldDO::getGroupId, reqVO.getGroupId())
                .eqIfPresent(PublicDataFieldDO::getDataType, reqVO.getDataType())
                .likeIfPresent(PublicDataFieldDO::getDetailFieldName, reqVO.getDetailTableName())
                .eqIfPresent(PublicDataFieldDO::getSort, reqVO.getSort())
                .betweenIfPresent(PublicDataFieldDO::getCreateTime, reqVO.getCreateTime())
                .and(wrapper->{
                    wrapper.like(PublicDataFieldDO::getField, reqVO.getKeyword())
                            .or()
                            .like(PublicDataFieldDO::getFieldName, reqVO.getKeyword());
                })
                .orderByAsc(PublicDataFieldDO::getSort));
    }

    SysTagConfigVO selectTagsConfig(@Param("tagid") Long tagid);

}
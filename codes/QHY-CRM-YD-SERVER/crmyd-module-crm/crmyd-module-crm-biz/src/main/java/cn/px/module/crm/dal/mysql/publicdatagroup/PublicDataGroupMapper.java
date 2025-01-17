package cn.px.module.crm.dal.mysql.publicdatagroup;

import java.util.*;

import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.module.crm.dal.dataobject.publicdatagroup.PublicDataGroupDO;
import org.apache.ibatis.annotations.Mapper;
import cn.px.module.crm.controller.admin.publicdatagroup.vo.*;

/**
 * 公开数据接口 Mapper
 *
 * @author 品讯科技
 */
@Mapper
public interface PublicDataGroupMapper extends BaseMapperX<PublicDataGroupDO> {

    default PageResult<PublicDataGroupDO> selectPage(PublicDataGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PublicDataGroupDO>()
                .eqIfPresent(PublicDataGroupDO::getInterfacePlatform, reqVO.getInterfacePlatform())
                .likeIfPresent(PublicDataGroupDO::getInterfaceName, reqVO.getInterfaceName())
                .eqIfPresent(PublicDataGroupDO::getInterfaceUrl, reqVO.getInterfaceUrl())
                .eqIfPresent(PublicDataGroupDO::getRemark, reqVO.getRemark())
                .likeIfPresent(PublicDataGroupDO::getDetailTableName, reqVO.getDetailTableName())
                .eqIfPresent(PublicDataGroupDO::getIconUrl, reqVO.getIconUrl())
                .eqIfPresent(PublicDataGroupDO::getSort, reqVO.getSort())
                .betweenIfPresent(PublicDataGroupDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(PublicDataGroupDO::getId));
    }

}
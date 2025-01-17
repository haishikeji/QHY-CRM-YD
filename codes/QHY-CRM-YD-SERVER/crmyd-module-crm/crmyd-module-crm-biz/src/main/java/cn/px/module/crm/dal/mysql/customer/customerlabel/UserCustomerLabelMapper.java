package cn.px.module.crm.dal.mysql.customer.customerlabel;

import java.util.*;

import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.module.crm.controller.admin.customer.customerlabel.vo.UserCustomerLabelPageReqVO;
import cn.px.module.crm.dal.dataobject.customer.customerlabel.UserCustomerLabelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户CRM客户标签数据查阅权限 Mapper
 *
 * @author 品讯科技
 */
@Mapper
public interface UserCustomerLabelMapper extends BaseMapperX<UserCustomerLabelDO> {

    default PageResult<UserCustomerLabelDO> selectPage(UserCustomerLabelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserCustomerLabelDO>()
                .eqIfPresent(UserCustomerLabelDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(UserCustomerLabelDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(UserCustomerLabelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserCustomerLabelDO::getId));
    }

}

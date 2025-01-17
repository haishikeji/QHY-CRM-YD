package cn.px.module.crm.dal.mysql.customer.customermatchcopyworksinfo;

import java.util.*;

import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.module.crm.controller.admin.customer.customermatchcopyworksinfo.vo.CustomerMatchCopyworksInfoPageReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * CRM-客户管理-匹配数据-作品著作信息 Mapper
 *
 * @author 品讯科技
 */
@Mapper
public interface CustomerMatchCopyworksInfoMapper extends BaseMapperX<CustomerMatchCopyworksInfoDO> {

    default PageResult<CustomerMatchCopyworksInfoDO> selectPage(CustomerMatchCopyworksInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerMatchCopyworksInfoDO>()
                .eqIfPresent(CustomerMatchCopyworksInfoDO::getCustomerId, reqVO.getCustomerId())
                .betweenIfPresent(CustomerMatchCopyworksInfoDO::getRegtime, reqVO.getRegtime())
                .betweenIfPresent(CustomerMatchCopyworksInfoDO::getPublishtime, reqVO.getPublishtime())
                .likeIfPresent(CustomerMatchCopyworksInfoDO::getFullname, reqVO.getFullname())
                .eqIfPresent(CustomerMatchCopyworksInfoDO::getTotal, reqVO.getTotal())
                .betweenIfPresent(CustomerMatchCopyworksInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerMatchCopyworksInfoDO::getId));
    }

}

package cn.px.module.crm.dal.mysql.customer.customerpubliccertificateinfo;

import java.util.*;

import cn.hutool.core.util.StrUtil;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.module.crm.controller.admin.customer.customerpubliccertificateinfo.vo.CustomerPublicCertificateInfoPageReqVO;
import cn.px.module.crm.dal.dataobject.customer.customerpublicbidsinfo.CustomerPublicBidsInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccertificateinfo.CustomerPublicCertificateInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CRM-客户管理-公开数据-资质证书 Mapper
 *
 * @author 品讯科技
 */
@Mapper
public interface CustomerPublicCertificateInfoMapper extends BaseMapperX<CustomerPublicCertificateInfoDO> {

    default PageResult<CustomerPublicCertificateInfoDO> selectPage(CustomerPublicCertificateInfoPageReqVO reqVO) {
        // 参数校验
        if (reqVO == null) {
            throw new IllegalArgumentException("Request object cannot be null");
        }
        // 构建查询条件
        LambdaQueryWrapperX<CustomerPublicCertificateInfoDO> queryWrapper = new LambdaQueryWrapperX<>();

        // 处理 keyword 条件
        String keyword = reqVO.getKeyword();
        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.and(w -> w
                    .like(CustomerPublicCertificateInfoDO::getCustomerId, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getTId, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getCertificateName, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getDetail, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getEndDate, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getStartDate, keyword)
                    .or()
                    .like(CustomerPublicCertificateInfoDO::getCerNo, keyword)
            );
        }
        // 其他条件
        queryWrapper.eqIfPresent(CustomerPublicCertificateInfoDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(CustomerPublicCertificateInfoDO::getCerNo, reqVO.getCerNo())
                .betweenIfPresent(CustomerPublicCertificateInfoDO::getEndDate, reqVO.getEndDate())
                .betweenIfPresent(CustomerPublicCertificateInfoDO::getStartDate, reqVO.getStartDate())
                .likeIfPresent(CustomerPublicCertificateInfoDO::getCertificateName, reqVO.getCertificateName())
                .eqIfPresent(CustomerPublicCertificateInfoDO::getTId, reqVO.getTId())
                .eqIfPresent(CustomerPublicCertificateInfoDO::getDetail, reqVO.getDetail())
                .betweenIfPresent(CustomerPublicCertificateInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerPublicCertificateInfoDO::getId);

        // 执行分页查询
        return selectPage(reqVO, queryWrapper);

    }



}

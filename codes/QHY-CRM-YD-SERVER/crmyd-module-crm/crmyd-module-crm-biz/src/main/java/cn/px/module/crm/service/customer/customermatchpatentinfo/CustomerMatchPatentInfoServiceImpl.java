package cn.px.module.crm.service.customer.customermatchpatentinfo;

import cn.px.module.crm.controller.admin.customer.customermatchpatentinfo.vo.CustomerMatchPatentInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customermatchpatentinfo.vo.CustomerMatchPatentInfoSaveReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchpatentinfo.CustomerMatchPatentInfoDO;
import cn.px.module.crm.dal.mysql.customer.customermatchpatentinfo.CustomerMatchPatentInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.common.pojo.PageParam;
import cn.px.framework.common.util.object.BeanUtils;


import static cn.px.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.px.module.crm.enums.ErrorCodeConstants.*;

/**
 * CRM-客户管理-匹配数据-专利信息 Service 实现类
 *
 * @author 品讯科技
 */
@Service
@Validated
public class CustomerMatchPatentInfoServiceImpl implements CustomerMatchPatentInfoService {

    @Resource
    private CustomerMatchPatentInfoMapper customerMatchPatentInfoMapper;

    @Override
    public Long createCustomerMatchPatentInfo(CustomerMatchPatentInfoSaveReqVO createReqVO) {
        // 插入
        CustomerMatchPatentInfoDO customerMatchPatentInfo = BeanUtils.toBean(createReqVO, CustomerMatchPatentInfoDO.class);
        customerMatchPatentInfoMapper.insert(customerMatchPatentInfo);
        // 返回
        return customerMatchPatentInfo.getId();
    }

    @Override
    public void updateCustomerMatchPatentInfo(CustomerMatchPatentInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerMatchPatentInfoExists(updateReqVO.getId());
        // 更新
        CustomerMatchPatentInfoDO updateObj = BeanUtils.toBean(updateReqVO, CustomerMatchPatentInfoDO.class);
        customerMatchPatentInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerMatchPatentInfo(Long id) {
        // 校验存在
        validateCustomerMatchPatentInfoExists(id);
        // 删除
        customerMatchPatentInfoMapper.deleteById(id);
    }

    private void validateCustomerMatchPatentInfoExists(Long id) {
        if (customerMatchPatentInfoMapper.selectById(id) == null) {
            throw exception(CUSTOMER_MATCH_PATENT_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CustomerMatchPatentInfoDO getCustomerMatchPatentInfo(Long id) {
        return customerMatchPatentInfoMapper.selectOne("customer_id",id);
    }

    @Override
    public PageResult<CustomerMatchPatentInfoDO> getCustomerMatchPatentInfoPage(CustomerMatchPatentInfoPageReqVO pageReqVO) {
        return customerMatchPatentInfoMapper.selectPage(pageReqVO);
    }

    /**
     * 根据客户id 查询客户专利信息
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerMatchPatentInfoSaveReqVO getCustomerMatchPatentInfoByCustomerId(Long customerId) {
        return BeanUtils.toBean(customerMatchPatentInfoMapper.selectOne(new LambdaQueryWrapper<CustomerMatchPatentInfoDO>()
                .eq(CustomerMatchPatentInfoDO::getCustomerId, customerId)), CustomerMatchPatentInfoSaveReqVO.class);
    }

}

package cn.px.module.crm.service.customer.customermatchcopyworksinfo;

import java.util.*;
import javax.validation.*;

import cn.px.module.crm.controller.admin.customer.customermatchcopyworksinfo.vo.CustomerMatchCopyworksInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customermatchcopyworksinfo.vo.CustomerMatchCopyworksInfoSaveReqVO;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.common.pojo.PageParam;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyworksinfo.CustomerPublicCopyworksInfoDO;

/**
 * CRM-客户管理-匹配数据-作品著作信息 Service 接口
 *
 * @author 品讯科技
 */
public interface CustomerMatchCopyworksInfoService {

    /**
     * 创建CRM-客户管理-匹配数据-作品著作信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerMatchCopyworksInfo(@Valid CustomerMatchCopyworksInfoSaveReqVO createReqVO);

    /**
     * 更新CRM-客户管理-匹配数据-作品著作信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerMatchCopyworksInfo(@Valid CustomerMatchCopyworksInfoSaveReqVO updateReqVO);

    /**
     * 删除CRM-客户管理-匹配数据-作品著作信息
     *
     * @param id 编号
     */
    void deleteCustomerMatchCopyworksInfo(Long id);

    /**
     * 获得CRM-客户管理-匹配数据-作品著作信息
     *
     * @param id 编号
     * @return CRM-客户管理-匹配数据-作品著作信息
     */
    CustomerMatchCopyworksInfoDO getCustomerMatchCopyworksInfo(Long id);

    /**
     * 获得CRM-客户管理-匹配数据-作品著作信息分页
     *
     * @param pageReqVO 分页查询
     * @return CRM-客户管理-匹配数据-作品著作信息分页
     */
    PageResult<CustomerMatchCopyworksInfoDO> getCustomerMatchCopyworksInfoPage(CustomerMatchCopyworksInfoPageReqVO pageReqVO);

    /**
     * 根据公开数据插入匹配数据-作品著作权
     * @param doList
     * @param total
     */
    void createCustomerMatchCopyworksInfoByPublicDoList(List<CustomerPublicCopyworksInfoDO> doList, int total,Long customerId);

    /**
     * 根据公开数据更新匹配数据-作品著作权
     * @param addList
     * @param total
     */
    void updateCustomerMatchCopyworksInfoByPublicDoList(List<CustomerPublicCopyworksInfoDO> addList, int total,Long customerId);
}

package cn.px.module.crm.service.customer.customermatchcopyrightinfo;

import java.util.*;
import javax.validation.*;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.common.pojo.PageParam;
import cn.px.module.crm.controller.admin.customer.customermatchcopyrightinfo.vo.CustomerMatchCopyrightInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customermatchcopyrightinfo.vo.CustomerMatchCopyrightInfoSaveReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyrightinfo.CustomerMatchCopyrightInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyrightinfo.CustomerPublicCopyrightInfoDO;

/**
 * CRM-客户管理-匹配数据-软件著作信息 Service 接口
 *
 * @author 品讯科技
 */
public interface CustomerMatchCopyrightInfoService {

    /**
     * 创建CRM-客户管理-匹配数据-软件著作信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerMatchCopyrightInfo(@Valid CustomerMatchCopyrightInfoSaveReqVO createReqVO);

    /**
     * 更新CRM-客户管理-匹配数据-软件著作信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerMatchCopyrightInfo(@Valid CustomerMatchCopyrightInfoSaveReqVO updateReqVO);

    /**
     * 删除CRM-客户管理-匹配数据-软件著作信息
     *
     * @param id 编号
     */
    void deleteCustomerMatchCopyrightInfo(Long id);

    /**
     * 获得CRM-客户管理-匹配数据-软件著作信息
     *
     * @param id 编号
     * @return CRM-客户管理-匹配数据-软件著作信息
     */
    CustomerMatchCopyrightInfoDO getCustomerMatchCopyrightInfo(Long id);

    /**
     * 获得CRM-客户管理-匹配数据-软件著作信息分页
     *
     * @param pageReqVO 分页查询
     * @return CRM-客户管理-匹配数据-软件著作信息分页
     */
    PageResult<CustomerMatchCopyrightInfoDO> getCustomerMatchCopyrightInfoPage(CustomerMatchCopyrightInfoPageReqVO pageReqVO);

    /**
     * 根据软件著作公开数据创建-软件著作匹配数据
     * @param doList
     * @param total
     */
    void createCustomerMatchCopyrightInfoByPublicDoList(List<CustomerPublicCopyrightInfoDO> doList,int total,int totals,Long customerId);
    /**
     * 根据软件著作公开数据更新-软件著作匹配数据
     * @param addList
     * @param total
     */
    void updateCustomerMatchBidsInfoByPublicBidsList(List<CustomerPublicCopyrightInfoDO> addList, int total,int totals,Long customerId);
}

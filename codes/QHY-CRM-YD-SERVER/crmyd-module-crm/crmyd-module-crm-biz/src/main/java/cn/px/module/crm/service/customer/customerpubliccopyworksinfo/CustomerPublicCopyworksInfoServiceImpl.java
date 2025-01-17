package cn.px.module.crm.service.customer.customerpubliccopyworksinfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.px.framework.common.exception.ServiceException;
import cn.px.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyrightinfo.vo.CustomerPublicCopyrightInfoSaveReqVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo.vo.CustomerPublicCopyworksInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo.vo.CustomerPublicCopyworksInfoSaveReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyrightinfo.CustomerMatchCopyrightInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyrightinfo.CustomerPublicCopyrightInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyworksinfo.CustomerPublicCopyworksInfoDO;
import cn.px.module.crm.dal.mysql.customer.customerpubliccopyworksinfo.CustomerPublicCopyworksInfoMapper;
import cn.px.module.crm.enums.DictTypeConstants;
import cn.px.module.crm.service.customer.HelpService;
import cn.px.module.crm.service.customer.customermatchcopyrightinfo.CustomerMatchCopyrightInfoService;
import cn.px.module.crm.service.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoService;
import cn.px.module.crm.util.CustomerHttpUtil;
import cn.px.module.crm.util.LimitUtils;
import cn.px.module.system.api.dict.DictDataApi;
import cn.px.module.system.api.dict.dto.DictDataRespDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
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
 * CRM-客户管理-公开数据-作品著作信息 Service 实现类
 *
 * @author 品讯科技
 */
@Service
@Validated
@Slf4j
public class CustomerPublicCopyworksInfoServiceImpl implements CustomerPublicCopyworksInfoService {
    private Map<Long,Boolean> limitMap = new HashMap<>();
    @Resource
    private CustomerPublicCopyworksInfoMapper customerPublicCopyworksInfoMapper;

    @Resource
    private CustomerMatchCopyworksInfoService customerMatchCopyworksInfoService;
    @Resource
    private HelpService helpService;

    @Override
    public Long createCustomerPublicCopyworksInfo(CustomerPublicCopyworksInfoSaveReqVO createReqVO) {
        // 插入
        CustomerPublicCopyworksInfoDO customerPublicCopyworksInfo = BeanUtils.toBean(createReqVO, CustomerPublicCopyworksInfoDO.class);
        customerPublicCopyworksInfoMapper.insert(customerPublicCopyworksInfo);
        // 返回
        return customerPublicCopyworksInfo.getId();
    }

    @Override
    public void updateCustomerPublicCopyworksInfo(CustomerPublicCopyworksInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerPublicCopyworksInfoExists(updateReqVO.getId());
        // 更新
        CustomerPublicCopyworksInfoDO updateObj = BeanUtils.toBean(updateReqVO, CustomerPublicCopyworksInfoDO.class);
        customerPublicCopyworksInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerPublicCopyworksInfo(Long id) {
        // 校验存在
        validateCustomerPublicCopyworksInfoExists(id);
        // 删除
        customerPublicCopyworksInfoMapper.deleteById(id);
    }

    private void validateCustomerPublicCopyworksInfoExists(Long id) {
        if (customerPublicCopyworksInfoMapper.selectById(id) == null) {
            throw exception(CUSTOMER_PUBLIC_COPYWORKS_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CustomerPublicCopyworksInfoDO getCustomerPublicCopyworksInfo(Long id) {
        return customerPublicCopyworksInfoMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerPublicCopyworksInfoDO> getCustomerPublicCopyworksInfoPage(CustomerPublicCopyworksInfoPageReqVO pageReqVO) {
        while (limitMap.get(pageReqVO.getCustomerId())!=null){}
        return customerPublicCopyworksInfoMapper.selectPage(pageReqVO);
    }

    /**
     * 根据天眼查信息添加客户-作品著作信息
     *
     * @param customerName
     * @param customerId
     */
    @Override
    public void createCustomerPublicCopyworksByPublicData(String customerName, Long customerId) {
        log.debug("==================开始处理 - 公开数据--作品著作======================");
        //先查询第一页数据
        Map<String, Object> map = CustomerHttpUtil.getCopyrightWorksInfo(customerName, 1);
        //批量处理数据
        List<CustomerPublicCopyworksInfoDO> doList = CollUtil.newArrayList();
        int total = 0;
        if (MapUtil.isNotEmpty(map)) {
            List<CustomerPublicCopyworksInfoSaveReqVO> bidsList = (List<CustomerPublicCopyworksInfoSaveReqVO>) map.get("info");
            total = Integer.valueOf((String) map.get("total"));
            if (total == 0) {
                //总量为0直接返回
                return;
            }
            int queryNum, num = 1;
            if (total > 40) {
                //总条目数大于 60条 ，查询前3页数据
                queryNum = 2;
            } else {
                //否正查询所有数据
                queryNum = total / 20;
                if (total / 20 > 0) {
                    queryNum += 1;
                }
            }
            while (num < queryNum) {
                num++;
                Map<String, Object> map_ = CustomerHttpUtil.getCopyrightWorksInfo(customerName, num);
                if (MapUtil.isEmpty(map_)) {
                    return;
                }
                //将查询结果全部放入列表
                bidsList.addAll((Collection<? extends CustomerPublicCopyworksInfoSaveReqVO>) map_.get("info"));
            }

            for (CustomerPublicCopyworksInfoSaveReqVO vo : bidsList) {
                vo.setCustomerId(customerId); //客户id
                CustomerPublicCopyworksInfoDO bean = BeanUtils.toBean(vo, CustomerPublicCopyworksInfoDO.class);
                doList.add(bean);
            }
            //批量插入
            customerPublicCopyworksInfoMapper.insertBatch(doList);
        }
        // 插入匹配数据-作品著作权
        customerMatchCopyworksInfoService.createCustomerMatchCopyworksInfoByPublicDoList(doList, total,customerId);
        log.debug("==================公开数据--作品著作--处理完成======================");
    }

    /**
     * 联网更新客户资料-作品著作信息
     *
     * @param customerId
     * @param customerName
     */
    @Override
    public void onlineUpdateCustomerPublicCopyworksInfo(Long customerId, String customerName) {
        //联网更新时将限制状态置为true
        if (limitMap.containsKey(customerId)){
            //已经有人在更新则不允许更新
            throw new ServiceException(GlobalErrorCodeConstants.UPDATE_LIMITED.getCode(), GlobalErrorCodeConstants.UPDATE_LIMITED.getMsg());
        }
        limitMap.put(customerId,true);

        try {
            // 联网更新前先获取联网更新限制时长
            helpService.updateLimit(customerId,6);

            // 联网更新时，同样查询数据，因为不考虑性能，新增的时候一个一个新增，
            Map<String, Object> map = CustomerHttpUtil.getCopyrightWorksInfo(customerName, 1);
            List<CustomerPublicCopyworksInfoDO> addList = CollUtil.newArrayList();
            int total = 0;
            if (MapUtil.isNotEmpty(map)) {
                List<CustomerPublicCopyworksInfoSaveReqVO> bidsList = (List<CustomerPublicCopyworksInfoSaveReqVO>) map.get("info");
                total = Integer.valueOf((String) map.get("total"));
                if (total == 0) {
                    //总量为0直接返回
                    return;
                }
                int queryNum, num = 1;
                if (total > 60) {
                    //总条目数大于 60条 ，查询前3页数据
                    queryNum = 3;
                } else {
                    //否正查询所有数据
                    queryNum = total / 20;
                    if (total / 20 > 0) {
                        queryNum += 1;
                    }
                }
                while (num < queryNum) {
                    num++;
                    Map<String, Object> map_ = CustomerHttpUtil.getCopyrightWorksInfo(customerName, num);
                    if (MapUtil.isEmpty(map_)) {
                        break;
                    }
                    //将查询结果全部放入列表
                    bidsList.addAll((Collection<? extends CustomerPublicCopyworksInfoSaveReqVO>) map_.get("info"));
                }

                //批量处理数据
                for (CustomerPublicCopyworksInfoSaveReqVO vo : bidsList) {
                    //判断是否存在
                    List<CustomerPublicCopyworksInfoDO> existList = customerPublicCopyworksInfoMapper.selectList(new LambdaQueryWrapper<CustomerPublicCopyworksInfoDO>().eq(CustomerPublicCopyworksInfoDO::getRegnum, vo.getRegnum())
                            .eq(CustomerPublicCopyworksInfoDO::getCustomerId, customerId));
                    if ( CollUtil.isNotEmpty(existList)) {
                        //存在则下一个循环
                        continue;
                    }
                    //否则新增
                    vo.setCustomerId(customerId); //客户id
                    CustomerPublicCopyworksInfoDO bean = BeanUtils.toBean(vo, CustomerPublicCopyworksInfoDO.class);
                    addList.add(bean);
                    customerPublicCopyworksInfoMapper.insert(bean);
                }
            }
            // 更新匹配数据-招投标信息
            customerMatchCopyworksInfoService.updateCustomerMatchCopyworksInfoByPublicDoList(addList, total, customerId);
        } finally {
            limitMap.remove(customerId);
        }
    }

    /**
     * 超过一定天数，查询时会自动更新数据
     *
     * @param id
     * @param name
     * @param limit
     */
    @Override
    public void autoUpdateCustomerPubilcopyworksInfo(Long id, String name, int limit) {
        //查询匹配数据
        CustomerMatchCopyworksInfoDO copyworksInfo = customerMatchCopyworksInfoService.getCustomerMatchCopyworksInfo(id);
        if (copyworksInfo==null){
            //为空则联网更新
            onlineUpdateCustomerPublicCopyworksInfo(id,name);
        }else{
            //不为空则判断更新时间是否超过365天，超过则更新
            if (copyworksInfo.getUpdateTime()!=null && LimitUtils.limitUpdate(copyworksInfo.getUpdateTime(),limit)){
                onlineUpdateCustomerPublicCopyworksInfo(id,name);
            }
        }
    }

}

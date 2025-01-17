package cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo;

import cn.hutool.core.util.ObjUtil;
import cn.px.framework.ratelimiter.core.annotation.RateLimiter;
import cn.px.framework.ratelimiter.core.keyresolver.impl.UserRateLimiterKeyResolver;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo.vo.CustomerPublicCopyworksInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo.vo.CustomerPublicCopyworksInfoRespVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyworksinfo.vo.CustomerPublicCopyworksInfoSaveReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyworksinfo.CustomerPublicCopyworksInfoDO;
import cn.px.module.crm.service.customer.customermatchcopyworksinfo.CustomerMatchCopyworksInfoService;
import cn.px.module.crm.service.customer.customerpubliccopyworksinfo.CustomerPublicCopyworksInfoService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.px.framework.common.pojo.PageParam;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.common.pojo.CommonResult;
import cn.px.framework.common.util.object.BeanUtils;
import static cn.px.framework.common.pojo.CommonResult.success;

import cn.px.framework.excel.core.util.ExcelUtils;

import cn.px.framework.apilog.core.annotation.ApiAccessLog;
import static cn.px.framework.apilog.core.enums.OperateTypeEnum.*;



@Tag(name = "管理后台 - CRM-客户管理-公开数据-作品著作信息")
@RestController
@RequestMapping("/crm/customer-public-copyworks-info")
@Validated
public class CustomerPublicCopyworksInfoController {

    @Resource
    private CustomerPublicCopyworksInfoService customerPublicCopyworksInfoService;
    @Resource
    private CustomerMatchCopyworksInfoService customerMatchCopyworksInfoService;
    @PostMapping("/create")
    @Operation(summary = "创建CRM-客户管理-公开数据-作品著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:create')")
    public CommonResult<Long> createCustomerPublicCopyworksInfo(@Valid @RequestBody CustomerPublicCopyworksInfoSaveReqVO createReqVO) {
        return success(customerPublicCopyworksInfoService.createCustomerPublicCopyworksInfo(createReqVO));
    }
    @PutMapping("/onlineUpdate")
    @Operation(summary = "联网更新CRM客户公开数据作品著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    @RateLimiter(count = 1, time = 60,keyResolver = UserRateLimiterKeyResolver.class)
    public CommonResult<Boolean> onlineUpdateCustomerPublicCopyworksInfo(Long customerId,String customerName) {
        customerPublicCopyworksInfoService.onlineUpdateCustomerPublicCopyworksInfo(customerId,customerName);
        return success(true);
    }
    @PutMapping("/update")
    @Operation(summary = "更新CRM-客户管理-公开数据-作品著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> updateCustomerPublicCopyworksInfo(@Valid @RequestBody CustomerPublicCopyworksInfoSaveReqVO updateReqVO) {
        customerPublicCopyworksInfoService.updateCustomerPublicCopyworksInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CRM-客户管理-公开数据-作品著作信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer:delete')")
    public CommonResult<Boolean> deleteCustomerPublicCopyworksInfo(@RequestParam("id") Long id) {
        customerPublicCopyworksInfoService.deleteCustomerPublicCopyworksInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CRM-客户管理-公开数据-作品著作信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<CustomerPublicCopyworksInfoRespVO> getCustomerPublicCopyworksInfo(@RequestParam("id") Long id) {
        CustomerPublicCopyworksInfoDO customerPublicCopyworksInfo = customerPublicCopyworksInfoService.getCustomerPublicCopyworksInfo(id);
        return success(BeanUtils.toBean(customerPublicCopyworksInfo, CustomerPublicCopyworksInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CRM-客户管理-公开数据-作品著作信息分页")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<Map<String,Object>> getCustomerPublicCopyworksInfoPage(@Valid CustomerPublicCopyworksInfoPageReqVO pageReqVO) {
        Map<String,Object> data = new HashMap<>();
        data.put("page",BeanUtils.toBean(customerPublicCopyworksInfoService.getCustomerPublicCopyworksInfoPage(pageReqVO), CustomerPublicCopyworksInfoRespVO.class));
        CustomerMatchCopyworksInfoDO customerMatchCopyworksInfo = customerMatchCopyworksInfoService.getCustomerMatchCopyworksInfo(pageReqVO.getCustomerId());
        if (ObjUtil.isNotEmpty(customerMatchCopyworksInfo)){
            data.put("updateTime",customerMatchCopyworksInfo.getUpdateTime());
            data.put("totals",customerMatchCopyworksInfo.getTotal());
            data.put("back_total",customerMatchCopyworksInfo.getTotals());
        }
        return success(data);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CRM-客户管理-公开数据-作品著作信息 Excel")
    @PreAuthorize("@ss.hasPermission('crm:customer:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCustomerPublicCopyworksInfoExcel(@Valid CustomerPublicCopyworksInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerPublicCopyworksInfoDO> list = customerPublicCopyworksInfoService.getCustomerPublicCopyworksInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CRM-客户管理-公开数据-作品著作信息.xls", "数据", CustomerPublicCopyworksInfoRespVO.class,
                        BeanUtils.toBean(list, CustomerPublicCopyworksInfoRespVO.class));
    }

}

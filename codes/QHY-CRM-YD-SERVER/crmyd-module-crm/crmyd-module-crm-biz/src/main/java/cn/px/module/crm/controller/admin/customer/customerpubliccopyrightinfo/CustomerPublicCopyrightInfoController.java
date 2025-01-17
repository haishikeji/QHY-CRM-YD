package cn.px.module.crm.controller.admin.customer.customerpubliccopyrightinfo;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.px.framework.ratelimiter.core.annotation.RateLimiter;
import cn.px.framework.ratelimiter.core.keyresolver.impl.UserRateLimiterKeyResolver;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyrightinfo.vo.CustomerPublicCopyrightInfoPageReqVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyrightinfo.vo.CustomerPublicCopyrightInfoRespVO;
import cn.px.module.crm.controller.admin.customer.customerpubliccopyrightinfo.vo.CustomerPublicCopyrightInfoSaveReqVO;
import cn.px.module.crm.dal.dataobject.customer.customermatchcopyrightinfo.CustomerMatchCopyrightInfoDO;
import cn.px.module.crm.dal.dataobject.customer.customerpubliccopyrightinfo.CustomerPublicCopyrightInfoDO;
import cn.px.module.crm.service.customer.customermatchcopyrightinfo.CustomerMatchCopyrightInfoService;
import cn.px.module.crm.service.customer.customerpubliccopyrightinfo.CustomerPublicCopyrightInfoService;
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



@Tag(name = "管理后台 - CRM-客户管理-公开数据-软件著作信息")
@RestController
@RequestMapping("/crm/customer-public-copyright-info")
@Validated
public class CustomerPublicCopyrightInfoController {

    @Resource
    private CustomerPublicCopyrightInfoService customerPublicCopyrightInfoService;
    @Resource
    private CustomerMatchCopyrightInfoService customerMatchCopyrightInfoService;
    @PostMapping("/create")
    @Operation(summary = "创建CRM-客户管理-公开数据-软件著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:create')")
    public CommonResult<Long> createCustomerPublicCopyrightInfo(@Valid @RequestBody CustomerPublicCopyrightInfoSaveReqVO createReqVO) {
        return success(customerPublicCopyrightInfoService.createCustomerPublicCopyrightInfo(createReqVO));
    }
    @PutMapping("/onlineUpdate")
    @Operation(summary = "联网更新CRM客户公开数据软件著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    @RateLimiter(keyResolver = UserRateLimiterKeyResolver.class)
    public CommonResult<Boolean> onlineUpdateCustomerPublicCopyrightInfo(@RequestParam("customerId") Long customerId, @RequestParam("customerName") String customerName) {
        customerPublicCopyrightInfoService.onlineUpdateCustomerPublicCopyrightInfo(customerId,customerName);
        return success(true);
    }
    @PutMapping("/update")
    @Operation(summary = "更新CRM-客户管理-公开数据-软件著作信息")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> updateCustomerPublicCopyrightInfo(@Valid @RequestBody CustomerPublicCopyrightInfoSaveReqVO updateReqVO) {
        customerPublicCopyrightInfoService.updateCustomerPublicCopyrightInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CRM-客户管理-公开数据-软件著作信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer:delete')")
    public CommonResult<Boolean> deleteCustomerPublicCopyrightInfo(@RequestParam("id") Long id) {
        customerPublicCopyrightInfoService.deleteCustomerPublicCopyrightInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CRM-客户管理-公开数据-软件著作信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<CustomerPublicCopyrightInfoRespVO> getCustomerPublicCopyrightInfo(@RequestParam("id") Long id) {
        CustomerPublicCopyrightInfoDO customerPublicCopyrightInfo = customerPublicCopyrightInfoService.getCustomerPublicCopyrightInfo(id);
        return success(BeanUtils.toBean(customerPublicCopyrightInfo, CustomerPublicCopyrightInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CRM-客户管理-公开数据-软件著作信息分页")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<Map<String,Object>> getCustomerPublicCopyrightInfoPage(@Valid CustomerPublicCopyrightInfoPageReqVO pageReqVO) {
        Map<String,Object> data = MapUtil.newHashMap();
        data.put("page",BeanUtils.toBean(customerPublicCopyrightInfoService.getCustomerPublicCopyrightInfoPage(pageReqVO), CustomerPublicCopyrightInfoRespVO.class));
        CustomerMatchCopyrightInfoDO customerMatchCopyrightInfo = customerMatchCopyrightInfoService.getCustomerMatchCopyrightInfo(pageReqVO.getCustomerId());
        if (ObjUtil.isNotEmpty(customerMatchCopyrightInfo)){
            data.put("updateTime",customerMatchCopyrightInfo.getUpdateTime());
            data.put("totals",customerMatchCopyrightInfo.getTotal());
            data.put("back_total",customerMatchCopyrightInfo.getTotals());
        }
        return success(data);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CRM-客户管理-公开数据-软件著作信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    @RateLimiter(count = 1, time =  60,keyResolver = UserRateLimiterKeyResolver.class)
    public void exportCustomerPublicCopyrightInfoExcel(@Valid CustomerPublicCopyrightInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerPublicCopyrightInfoDO> list = customerPublicCopyrightInfoService.getCustomerPublicCopyrightInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CRM-客户管理-公开数据-软件著作信息.xls", "数据", CustomerPublicCopyrightInfoRespVO.class,
                        BeanUtils.toBean(list, CustomerPublicCopyrightInfoRespVO.class));
    }

}

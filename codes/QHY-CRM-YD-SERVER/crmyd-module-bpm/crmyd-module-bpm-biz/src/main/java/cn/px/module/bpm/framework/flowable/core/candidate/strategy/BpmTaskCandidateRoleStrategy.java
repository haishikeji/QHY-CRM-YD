package cn.px.module.bpm.framework.flowable.core.candidate.strategy;

import cn.px.framework.common.util.string.StrUtils;
import cn.px.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.px.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.px.module.system.api.permission.PermissionApi;
import cn.px.module.system.api.permission.RoleApi;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 角色 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author 品讯科技
 */
@Component
public class BpmTaskCandidateRoleStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private RoleApi roleApi;
    @Resource
    private PermissionApi permissionApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.ROLE;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> roleIds = StrUtils.splitToLongSet(param);
        roleApi.validRoleList(roleIds);
    }

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        Set<Long> roleIds = StrUtils.splitToLongSet(param);
        return permissionApi.getUserRoleIdListByRoleIds(roleIds);
    }

}
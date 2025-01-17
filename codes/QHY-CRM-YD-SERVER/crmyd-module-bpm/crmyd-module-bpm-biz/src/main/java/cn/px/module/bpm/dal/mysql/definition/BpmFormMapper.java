package cn.px.module.bpm.dal.mysql.definition;


import cn.px.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import cn.px.module.bpm.dal.dataobject.definition.BpmFormDO;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.mybatis.core.mapper.BaseMapperX;
import cn.px.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态表单 Mapper
 *
 * @author 品讯科技
 */
@Mapper
public interface BpmFormMapper extends BaseMapperX<BpmFormDO> {

    default PageResult<BpmFormDO> selectPage(BpmFormPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<BpmFormDO>()
                .likeIfPresent("name", reqVO.getName())
                .orderByDesc("id"));
    }

}

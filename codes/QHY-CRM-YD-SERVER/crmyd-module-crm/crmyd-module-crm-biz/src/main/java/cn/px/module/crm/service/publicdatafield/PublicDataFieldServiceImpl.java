package cn.px.module.crm.service.publicdatafield;

import cn.px.framework.common.pojo.CommonResult;
import cn.px.framework.common.util.json.JsonUtils;
import cn.px.module.crm.dal.dataobject.publicdatafield.TagPublicDataConfigDO;
import cn.px.module.crm.dal.dataobject.publicdatafield.TagPublicDataConfigFronDO;
import cn.px.module.crm.dal.dataobject.publicdatagroup.PublicDataGroupDO;
import cn.px.module.crm.dal.mysql.publicdatafield.TagPublicDataConfigFronMapper;
import cn.px.module.crm.dal.mysql.publicdatafield.TagPublicDataConfigMapper;
import cn.px.module.crm.dal.mysql.publicdatagroup.PublicDataGroupMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.px.module.crm.controller.admin.publicdatafield.vo.*;
import cn.px.module.crm.dal.dataobject.publicdatafield.PublicDataFieldDO;
import cn.px.framework.common.pojo.PageResult;
import cn.px.framework.common.util.object.BeanUtils;

import cn.px.module.crm.dal.mysql.publicdatafield.PublicDataFieldMapper;

import static cn.px.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.px.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;
import static cn.px.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.px.module.crm.enums.ErrorCodeConstants.*;

/**
 * 公开数据字段 Service 实现类
 *
 * @author 品讯科技
 */
@Service
@Validated
@Slf4j
public class PublicDataFieldServiceImpl implements PublicDataFieldService {

    @Resource
    private PublicDataFieldMapper publicDataFieldMapper;
    
    @Resource
    private PublicDataGroupMapper publicDataGroupMapper;

    @Resource
    private TagPublicDataConfigMapper tagPublicDataConfigMapper;

    @Resource
    private TagPublicDataConfigFronMapper tagPublicDataConfigFronMapper;

    @Override
    public Integer createPublicDataField(PublicDataFieldSaveReqVO createReqVO) {
        // 插入
        PublicDataFieldDO publicDataField = BeanUtils.toBean(createReqVO, PublicDataFieldDO.class);
        publicDataFieldMapper.insert(publicDataField);
        // 返回
        return publicDataField.getId();
    }

    @Override
    public void updatePublicDataField(PublicDataFieldSaveReqVO updateReqVO) {
        // 校验存在
        validatePublicDataFieldExists(updateReqVO.getId());
        // 更新
        PublicDataFieldDO updateObj = BeanUtils.toBean(updateReqVO, PublicDataFieldDO.class);
        publicDataFieldMapper.updateById(updateObj);
    }

    @Override
    public void deletePublicDataField(Integer id) {
        // 校验存在
        validatePublicDataFieldExists(id);
        // 删除
        publicDataFieldMapper.deleteById(id);
    }

    private void validatePublicDataFieldExists(Integer id) {
        if (publicDataFieldMapper.selectById(id) == null) {
            throw exception(PUBLIC_DATA_FIELD_NOT_EXISTS);
        }
    }

    @Override
    public PublicDataFieldDO getPublicDataField(Integer id) {
        return publicDataFieldMapper.selectById(id);
    }

    @Override
    public PageResult<PublicDataFieldDO> getPublicDataFieldPage(PublicDataFieldPageReqVO pageReqVO) {
        return publicDataFieldMapper.selectPage(pageReqVO);
    }

    @Override
    public CommonResult getTagOption() {
        List<AdvancedComputingVo> allTagOption = new ArrayList<>();
        allTagOption.addAll(getInputVal());
        allTagOption.addAll(getOperator());
        List<PublicDataGroupDO> groupDOS = publicDataGroupMapper.selectList();
        for (PublicDataGroupDO groupDO : groupDOS) {
            QueryWrapper<PublicDataFieldDO> wrapper = new QueryWrapper();
            wrapper.lambda().eq(PublicDataFieldDO::getGroupId, groupDO.getId());
            List<PublicDataFieldDO> publicDataFieldDOS = publicDataFieldMapper.selectList(wrapper);
            List<AdvancedComputingVo> publicData = new ArrayList<>();
            for (PublicDataFieldDO fieldDO : publicDataFieldDOS) {
                AdvancedComputingVo advancedComputingVo = new AdvancedComputingVo();
                advancedComputingVo.setId(fieldDO.getId());
                advancedComputingVo.setTeam(groupDO.getInterfaceName());
                advancedComputingVo.setValue(fieldDO.getFieldName());
                advancedComputingVo.setTagType("tag");
                advancedComputingVo.setOperatorValue(fieldDO.getField());
                advancedComputingVo.setSynonym(fieldDO.getFieldName());
                if ("5".equals(fieldDO.getDataType())) {
                    advancedComputingVo.setVariables("variablesNumber");
                }else{
                    advancedComputingVo.setVariables("variablesText");
                }
                publicData.add(advancedComputingVo);
            }
            allTagOption.addAll(publicData);
        }
        return CommonResult.success(allTagOption);
    }

    @Override
    public CommonResult saveTagConfig(List<TagOptionConfigVo> configVo) {
        for (TagOptionConfigVo config : configVo) {
            // 储存后端Json
            List<BackConditionVo> backConditionVos = new ArrayList<>();
            try{
                Document document = Jsoup.parse(config.getFrontContent());
                Elements elements = document.select(".mention");
                for (int i = 0; i < elements.size(); i++) {
                    BackConditionVo backConditionVo = analysisHtml(elements.get(i));
                    backConditionVos.add(backConditionVo);
                }
            }catch (Exception e) {
                log.warn("解析前端数据异常",e);
                return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(),"系统异常，请联系管理员");
            }
            String backJson = JsonUtils.toJsonString(backConditionVos);
            TagPublicDataConfigDO publicDataBackCondition = new TagPublicDataConfigDO();
            if (config.getConfigId() != null) {
                publicDataBackCondition.setId(config.getConfigId());
                publicDataBackCondition.setTagId(config.getTagId());
                publicDataBackCondition.setConditionJson(backJson);
                tagPublicDataConfigMapper.updateById(publicDataBackCondition);
            }else{
                publicDataBackCondition.setTagId(config.getTagId());
                publicDataBackCondition.setConditionJson(backJson);
                tagPublicDataConfigMapper.insert(publicDataBackCondition);
            }
            // 储存前端json
            TagPublicDataConfigFronDO fronDO = new TagPublicDataConfigFronDO();
            if (config.getConfigFronId() != null) {
                fronDO.setId(config.getConfigFronId());
                fronDO.setTagConfigId(publicDataBackCondition.getId());
                fronDO.setFrontContent(config.getFrontContent());
                tagPublicDataConfigFronMapper.updateById(fronDO);
            }else{
                fronDO.setTagConfigId(publicDataBackCondition.getId());
                fronDO.setFrontContent(config.getFrontContent());
                tagPublicDataConfigFronMapper.insert(fronDO);
            }
        }
        return CommonResult.success(true);
    }



    public List<AdvancedComputingVo> getOperator(){
        List<AdvancedComputingVo> operatorList = new ArrayList<>();
        operatorList.add(getOperatorAdvancedComputingVo(0,"运算符","+","operator","+","加法加号+jiafajiahao"));
        operatorList.add(getOperatorAdvancedComputingVo(0,"运算符","-","operator","-","减法减号-jianfajianhao"));
        operatorList.add(getOperatorAdvancedComputingVo(0,"运算符","÷","operator","/","÷/除以除chuchuyi"));
        operatorList.add(getOperatorAdvancedComputingVo(0,"运算符","*","operator","*","*乘乘以chengchengyi"));
        operatorList.add(getOperatorAdvancedComputingVo(0,"运算符","组合","operator","composition","组合zuhe"));
        return operatorList;
    }
    public AdvancedComputingVo getOperatorAdvancedComputingVo(int id,String team,String value,String tagType,String operatorValue, String synonym){
        AdvancedComputingVo optionVo = new AdvancedComputingVo();
        optionVo.setId(id);
        optionVo.setTeam(team);
        optionVo.setValue(value);
        optionVo.setTagType(tagType);
        optionVo.setOperatorValue(operatorValue);
        optionVo.setSynonym(synonym);
        return optionVo;
    }
    public  List<AdvancedComputingVo> getInputVal(){
        List<AdvancedComputingVo> inputVal = new ArrayList<>();

        inputVal.add(getOperatorAdvancedComputingVo(0,"输入值","数值","number","","数值数字整数shuzhishuzizhengshu"));

        inputVal.add(getOperatorAdvancedComputingVo(0,"输入值","文本","text","","文本字符串文字汉字中文wenbenzifuchuanwenzihanzizhongwen"));

        return inputVal;
    }

    public BackConditionVo analysisHtml(Element span){
        BackConditionVo backSysProjectCondition = new BackConditionVo();
        //判断是什么类型的标签
        System.out.println(span);
        String dataTagType = span.attr("data-tag-type");
        backSysProjectCondition.setType(dataTagType);
        switch (dataTagType) {
            case "text":
                Elements inputs = span.getElementsByTag("input");
                String val = inputs.get(0).attr("value");

                backSysProjectCondition.setValue(val);
                break;
            case "number":
                Elements numbers = span.getElementsByTag("input");
                String number = numbers.get(0).attr("value");

                backSysProjectCondition.setValue(number);
                break;
            default:
                String tagVal = span.attr("data-value");
                String publicDataid = span.attr("data-id");
                backSysProjectCondition.setValue(tagVal);
                backSysProjectCondition.setPublicDataId(Integer.valueOf(publicDataid));
                break;
        }
        return backSysProjectCondition;
    }

}
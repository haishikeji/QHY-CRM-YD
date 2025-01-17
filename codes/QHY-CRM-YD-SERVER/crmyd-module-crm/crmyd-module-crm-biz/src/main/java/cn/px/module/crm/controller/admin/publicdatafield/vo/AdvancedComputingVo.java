package cn.px.module.crm.controller.admin.publicdatafield.vo;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

@Data
public class AdvancedComputingVo {

    private String uuid = RandomUtil.randomNumbers(10);

    private int id;

    //分组
    private String team;

    //html类型
    private String tagType;

    //前端搜索索引
    private String synonym;

    //运算符代码，用于检验格式
    private String operatorValue;

    //选项清单
    private String radio;

    private String variables;

    private String value;

    private String dataType;

    private String subDataType;

    private Long optionId;
}

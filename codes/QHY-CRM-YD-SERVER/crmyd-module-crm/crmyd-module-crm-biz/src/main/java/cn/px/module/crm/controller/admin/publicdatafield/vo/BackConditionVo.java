package cn.px.module.crm.controller.admin.publicdatafield.vo;

import lombok.Data;

@Data
public class BackConditionVo {
    /**
     * ID
     */
    private Long id;

    /**
     * publicDataId ID
     */
    private Integer publicDataId;

    /**
     * Value
     */
    private String value;

    /**
     * Operator
     */
    private String operator;

    /**
     * Type
     */
    private String type;

    /**
     * Unit
     */
    private String unit;

    /**
     * Region
     */
    private String region;

    /**
     * Code
     */
    private String code;

    /**
     * Option ID
     */
    private Integer optionId;



}
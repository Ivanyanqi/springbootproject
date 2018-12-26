package cn.ivan.bean;

import lombok.Data;

import java.util.List;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/3
 */
@Data
public class RuleCondition {

    private List<String> conditions;

    private int leafIndex;

    private String leafOrder;

    private int leafLevel;

    private String ownConditions;
}

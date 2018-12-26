package cn.ivan.convert;

import cn.ivan.bean.RuleCondition;

import java.util.List;

/**
 *  转换为drl
 * @author : yanqi
 * @version : 1.0
 * 2018/12/4
 */
public interface DrlConvert {

    /**
     * 将java bean 转化为drl
     * @param ruleConditions 文件中获取的条件表达式集合
     * @return drl集合
     */
    List<String> convert(List<RuleCondition> ruleConditions);
}

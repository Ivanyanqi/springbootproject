package cn.ivan.parser;

import cn.ivan.bean.RuleCondition;
import cn.ivan.bean.RuleNode;

import java.util.List;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/4
 */
public interface ModelParser {

    /**
     *  节点解析为条件集合
     * @param ruleNodes 规则节点
     * @return 条件集合
     */
    List<RuleCondition> parser(List<RuleNode> ruleNodes);

}

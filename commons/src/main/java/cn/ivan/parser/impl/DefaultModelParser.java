package cn.ivan.parser.impl;

import cn.ivan.bean.RuleCondition;
import cn.ivan.bean.RuleNode;
import cn.ivan.parser.ModelParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 节点解析默认实现
 * @author : yanqi
 * @version : 1.0
 * 2018/12/4
 */
public class DefaultModelParser implements ModelParser {


    @Override
    public List<RuleCondition> parser(List<RuleNode> ruleNodes) {
        List<RuleCondition> rules = new ArrayList<>();
        for(int i = 0 ; i<ruleNodes.size() ; i++){
            RuleNode node = ruleNodes.get(i);
            if(node.isLeaf()){
                RuleCondition condition = new RuleCondition();
                condition.setConditions(new ArrayList<>());
                condition.setLeafIndex(i);
                condition.setLeafOrder(node.getNodeOrder());
                condition.setLeafLevel(node.getLevel());
                findPath(ruleNodes,condition);
                condition.getConditions().add(node.getNodeCondition());
                rules.add(condition);
            }
        }

        return rules;
    }


    private void findPath(List<RuleNode> ruleNodes,RuleCondition condition){
        int currentIndex = condition.getLeafLevel();
        for(int i = condition.getLeafIndex()-1  ; i >= 0 ;i-- ){
            RuleNode node = ruleNodes.get(i);
            if(node.getLevel() < currentIndex){
                currentIndex = node.getLevel();
                condition.getConditions().add(node.getNodeCondition());
            }
        }
        Collections.reverse(condition.getConditions());
    }
}

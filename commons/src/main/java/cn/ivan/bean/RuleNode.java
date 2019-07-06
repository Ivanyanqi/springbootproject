package cn.ivan.bean;

import lombok.Data;

import java.util.Objects;

/**
 * 解析文件为模型节点
 * @author : yanqi
 * @version : 1.0
 * 2018/12/3
 */
@Data
public class RuleNode {

    private String nodeOrder;

    private String nodeCondition;

    private int level;

    private RuleNode parent;

    /**
     *  是否是叶子节点
     */
    private boolean isLeaf;

}

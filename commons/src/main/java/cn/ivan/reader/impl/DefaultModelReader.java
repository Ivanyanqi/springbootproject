package cn.ivan.reader.impl;

import cn.ivan.bean.RuleNode;
import cn.ivan.reader.ModelReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 模型文件读取类
 *
 * @author : yanqi
 * @version : 1.0
 * 2018/12/3
 */
public class DefaultModelReader implements ModelReader {

    /**
     * 匹配模型条件的正则表达式
     */
    private final Pattern LINE_PATTERN = Pattern.compile("\\s+(\\d+)\\)\\s+(\\w+[<|>|=][\\s|=|<|>]\\d+\\.\\d+)\\s.*");

    private final static int LEVEL_GROUP = 1;

    private final static int CONDITION_GROUP = 2;


    /**
     * 读取模型文件
     *
     * @param filePath 文件路径
     * @return 返回封装的bean
     */
    @Override
    public List<RuleNode> read(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .filter(DefaultModelReader::apply)
                    .map(this::addRuleNode)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 正则匹配每一行，提取出所需要的信息封装成java bean
     *
     * @param line 读取文件的每一行
     */
    private RuleNode addRuleNode(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);
        if (matcher.matches()) {
            RuleNode node = new RuleNode();
            node.setNodeOrder(matcher.group(LEVEL_GROUP));
            node.setLevel(matcher.start(LEVEL_GROUP));
            node.setNodeCondition(matcher.group(CONDITION_GROUP));
            node.setLeaf(line.endsWith("*"));
            return node;
        }
        return null;
    }

    /**
     * 去掉无用的叶子节点
     *
     * @param line 文件的每一行呢哦荣
     * @return 是否是无用的叶子节点
     */
    private static boolean apply(String line) {
        return !(line.endsWith("*") && line.contains(" no "));
    }
}

package cn.ivan.reader;

import cn.ivan.bean.RuleNode;

import java.util.List;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/12/3
 */
public interface ModelReader {

    /**
     *  文件读取方法
     * @param filePath 问价路径
     * @return 节点封装
     */
    List<RuleNode> read(String filePath);

}

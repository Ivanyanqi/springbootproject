package cn.ivan;

import cn.ivan.bean.RuleCondition;
import cn.ivan.bean.RuleNode;
import cn.ivan.parser.ModelParser;
import cn.ivan.reader.ModelReader;

import java.util.List;

/**
 *  模型解析器
 * @author : yanqi
 * @version : 1.0
 * 2018/12/4
 */
public class ModelAnalyzer {

    /**
     *  模型文件路径
     */
    private final String filePath;

    /**
     *  模型文件读取器
     */
    private final ModelReader modelReader;

    /**
     *  模型节点解析器
     */
    private final ModelParser modelParser;

    /**
     *  builder模式 方法构造不可变解析器
     */
    public static class Builder{
        private ModelReader modelReader;
        private ModelParser modelParser;
        private String filePath;

        public Builder(String filePath){
            this.filePath =filePath;
        }

        public Builder setModelReader(ModelReader modelReader){
            this.modelReader = modelReader;
            return this;
        }

        public Builder setModelParser(ModelParser modelParser){
            this.modelParser = modelParser;
            return this;
        }

        public ModelAnalyzer build(){
            return new ModelAnalyzer(this);
        }
    }

    private ModelAnalyzer(Builder builder){
        this.modelReader = builder.modelReader;
        this.modelParser = builder.modelParser;
        this.filePath = builder.filePath;
    }

    public List<RuleNode> reader(){
        if(modelReader == null){
            throw new RuntimeException("请设置文件读取类 : cn.ivan.reader.ModelReader");
        }
        return modelReader.read(filePath);
    }


    public List<RuleCondition> parser(){
        List<RuleNode> reader = reader();
        if(modelParser == null){
            throw new RuntimeException("请设置模型解析类 : cn.ivan.parser.ModelParser");
        }
        return modelParser.parser(reader);
    }


}

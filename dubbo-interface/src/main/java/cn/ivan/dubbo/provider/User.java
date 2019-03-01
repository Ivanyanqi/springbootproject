package cn.ivan.dubbo.provider;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Accessors(chain = true) 生成链式调用的api
 * RequiredArgsConstructor  生成有NonNull 注解或者final 修饰的字段的构造方法
 * staticName,有这个属性，则生成静态构造方法
 * dubbo 实体类要实现Serializable接口
 */
@Accessors(chain = true)
@Data
@RequiredArgsConstructor(staticName = "of")
public class User implements Serializable {


    private static final long serialVersionUID = -4586428006721621854L;
    @NonNull
    private int userId;
    private String username;
    private int age;

}

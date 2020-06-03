package cn.ivan.webflux.demo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author yanqi69
 * @date 2020/5/31
 */
public class FuncationDemo {

    public static void main(String[] args) {
        // 断言 输入是泛型， 输出boolean值
        Predicate<String> predicate = s -> s.startsWith("q");
        System.out.println(predicate.test("qwer"));

        //消费
        Consumer<String> consumer = System.out::println;
        consumer.accept("123Test");
        Dog dog = new Dog();

        // 级联表达式与柯里化
        // 柯里化 : 把多个参数的函数转化为只有一个参数的函数
        // 高阶函数: 返回函数的函数
        Function<Integer,Function<Integer,Integer>> func = x ->y -> x + y;
        func.apply(2).apply(2);
    }


    public static class Dog{



        // 非静态方法  默认传入当前对象实例,参数this，未知第一个 ，和底下的方法是同一个，不是重载
        public int eat(Dog this,int i){
            return  i -1;
        }

        /*public int eat(int i){
            return  i -1;
        }*/

    }
}

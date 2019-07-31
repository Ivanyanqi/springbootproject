package cn.ivan.code;

import java.math.BigDecimal;

/**
 *
 */
public class Test {

    public static void main(String[] args) {
        // 0.100000024
        float a = 1.0f - 0.9f;
        //  0.099999964
        float b = 0.9f - 0.8f;
        if (a == b) {
            System.out.println(true);
        }else {
            System.out.println(false);
        }
        //0.100000024
        Float c = Float.valueOf(1.0f - 0.9f);
        //0.099999964
        Float d = Float.valueOf(0.9f - 0.8f);
        if(c.equals(d)){
            System.out.println(true);
        }else {
            System.out.println(false);
        }

        String param = "null";
        // NullPointException
        switch (param){
            case "null":
                System.out.println(param);
                break;
             default:
                 System.out.println("default");
        }

        BigDecimal e = new BigDecimal(0.1);
        //0.1000000000000000055511151231257827021181583404541015625
        System.out.println(e);
        BigDecimal f = new BigDecimal("0.1");
        //0.1
        System.out.println(f);

    }
}

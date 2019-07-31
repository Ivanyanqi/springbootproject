package cn.ivan.code.leetcode;

public class Saris {

    public static void main(String[] args) {
        long i = new Saris().sarisM(50L);
        System.out.println(i);

    }



    public long sarisM(long n){
        if (n ==1 || n==2){
            return n;
        }
        return sarisM(n-1) + sarisM(n-2);
    }


}

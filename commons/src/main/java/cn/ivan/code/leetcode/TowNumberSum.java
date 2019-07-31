package cn.ivan.code.leetcode;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *   两数之和
 *   问题描述 : 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *            你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 */

public class TowNumberSum {


    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums,target)));
    }

    /**
     *  一遍hash表法   时间复杂度 o(n) 空间复杂度o(n)
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer>  cache = new HashMap<>();
        for (int i=0 ;i<nums.length;i++){
            int temp = target -nums[i];
            Integer integer = cache.get(temp);
            if(integer != null){
                return new int[]{i, integer};
            }else {
                cache.put(nums[i],i);
            }
        }
        return null;
    }
}

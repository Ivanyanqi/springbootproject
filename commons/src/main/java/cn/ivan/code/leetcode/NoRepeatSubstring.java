package cn.ivan.code.leetcode;

/**
 * 问题描述:给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */

public class NoRepeatSubstring {

    public static void main(String[] args) {
        String s = "aaaasdfqwdser";
        int length = lengthOfLongestSubstring(s);
        System.out.println(length);
    }


    /**
     * 找出不含重复字符的最长子串
     *
     * @param s 给定一个字符串
     * @return 不重复最长子串的长度
     */
    private static int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        int longest = 1;
        int l = 1;
        int start = 0;
        int end = 1;
        boolean flag = true;
        //dvdf
        while (end < s.length()) {
            if (s.charAt(end - 1) != s.charAt(end)) {
                int k = end - 2;
                while (k >= start) {
                    if (s.charAt(end) == s.charAt(k)) {
                        start = k + 1;
                        flag = false;
                        break;
                    }
                    k--;
                }
                if (flag) {
                    l++;
                } else {
                    longest = longest > l ? longest : l;
                    // 此时l的长度应该为 end - start +1
                    l = end - start + 1;
                    flag = true;
                }
            } else {
                start = end;
                longest = longest > l ? longest : l;
                l = 1;
            }
            end++;
        }
        // 循环到最后一个，返回longest和l的最大值
        return longest > l ? longest : l;
    }
}

package cn.ivan.code.leetcode;

import java.math.BigInteger;

/**
 *  给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 *  如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例 ：
 *  输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 *  输出：7 -> 0 -> 8
 *  原因：342 + 465 = 807
 */

public class AddTwoNums {


    public static void main(String[] args) {
      /*  ListNode l1 = new ListNode(5);
        l1.next = new ListNode(6);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        ListNode temp = l2;
        for(int i= 0 ;i< 29 ;i++){
            temp.next = new ListNode(0);
            temp = temp.next;
        }
        temp.next = new ListNode(1);*/
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode listNode = addTwoNumbers(l1, l2);
        StringBuilder sb  = new StringBuilder();
        while (listNode != null){
            sb.append(listNode.val);
            listNode = listNode.next;
        }
        System.out.println(sb.toString());
    }



    static class ListNode{

        private int val;
        private ListNode next;

        public ListNode(int val){
            this.val = val;
        }

    }

    /**
     *  每一位相加，进位的要补位,因为是逆序存储，所以能够相加
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode addTwoNumbers(ListNode l1, ListNode l2){

        if(l1 == null || l2 == null) {
            return null;
        }

        int a = l1.val;
        int b = l2.val;
        int sum = a + b;
        ListNode result ;
        // 进位数
        int carry = 0 ;
        if (sum >= 10){
            result = new ListNode(sum-10);
            carry = sum / 10;
        }else {
            result = new ListNode(sum);
        }
        l1 = l1.next;
        l2 = l2.next;
        // carry 大于 0 要进位，所以也要循环
        ListNode temp = result;
        while (l1 != null || l2 != null || carry > 0){
            int l1Val = l1 == null ? 0 :l1.val;
            int l2Val = l2 == null ? 0 :l2.val;
            int sumVal = l1Val + l2Val + carry;
            //相加大于10要进位
            if (sumVal >= 10){
                temp.next = new ListNode(sumVal-10);
                carry = sumVal / 10;
            }else {
                temp.next = new ListNode(sumVal);
                //没有进位要将carry 置为0
                carry = 0;
            }
            temp = temp.next;
            l1 = l1 == null ? null:l1.next;
            l2 = l2 == null ? null:l2.next;
        }
        return result;
    }


    /**
     *  做法错误值可能溢出
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode addTwoNumbersError(ListNode l1, ListNode l2) {
        // 值可能超出范围，导致溢出
        BigInteger num1 = new BigInteger("0");
        int i = 0;
        while (l1 != null){
            num1 = num1.add(BigInteger.valueOf(l1.val).multiply(BigInteger.valueOf(10).pow(i)));
            i++;
            l1 = l1.next;
        }
        System.out.println(num1);
        BigInteger num2 = new BigInteger("0");
        int j = 0;
        while (l2 != null){
            num2 = num2.add(BigInteger.valueOf(l2.val).multiply(BigInteger.valueOf(10).pow(j)));
            j++;
            l2 = l2.next;
        }
        System.out.println(num2);
        BigInteger result = num1.add(num2);
        System.out.println(result);
        ListNode head = new ListNode(result.mod(BigInteger.valueOf(10)).intValue());
        ListNode next  = head;
        BigInteger len = result ;
        do{
            len = len.divide(BigInteger.valueOf(10));
            if(len.compareTo(BigInteger.valueOf(0)) > 0){
                next.next = new ListNode(len.mod(BigInteger.valueOf(10)).intValue());
                next = next.next;
            }

        }while (len.longValue() != 0);
        return head;
    }



}

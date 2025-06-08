package com.example.demo;

/**
 * ClassName: Leetcode142
 * Package: com.example.demo
 * Description:
 *
 * @Author 浙工大-黄泳涛
 * @Create 2025/1/18 19:32
 * @Version 1.0
 */
public class LeetCode142 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        ListNode node = detectCycle(node1);
    }
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode p = head;

        while (fast.next != null && fast.next.next != null) {

            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }

        if (fast.next != null || fast.next.next != null) {
            return null;
        }

        while (true) {
            if (p == slow) {
                return p;
            }
            p = p.next;
            slow = slow.next;
        }
    }

}

class A implements Comparable<A> {
        @Override
    public int compareTo(A o) {
        return 0;
    }
}

class ListNode {
   int val;
    ListNode next;
   ListNode(int x) {
      val = x;
      next = null;
    }
 }




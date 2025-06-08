package com.example.demo;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * ClassName: leetcode763
 * Package: com.example.demo
 * Description:
 *
 * @Author 浙工大-黄泳涛
 * @Create 2025/2/6 16:56
 * @Version 1.0
 */
public class leetcode763 {

    public static void main(String[] args) {
        String s = "ababcbacadefegdehijhklij";
        System.out.println(partitionLabels(s));
    }

    public static List<Integer> partitionLabels(String s) {
        Map<Character,Integer> left = new HashMap<>();
        Map<Character,Integer> right = new HashMap<>();
        int length = s.length();
        for(int i = 0;i<length;i++){
            right.put(s.charAt(i),right.getOrDefault(s.charAt(i), 0)+1);
        }
        List<Integer> res = new ArrayList<>();
        for(int i=0;i<length;i++){
            left.put(s.charAt(i),left.getOrDefault(s.charAt(i), 0)+1);
            right.put(s.charAt(i),right.getOrDefault(s.charAt(i), 0)-1);
            if(right.get(s.charAt(i)) == 0){
                right.remove(s.charAt(i));
            }
            Set<Character> set1 =  new HashSet<>(right.keySet());
            Set<Character> set2 = new HashSet<>(left.keySet());
            if(Collections.disjoint(set1,set2)){
                res.add(left.values().stream().mapToInt(Integer::intValue).sum());
                left.clear();
            }
        }
        return res;
    }
}


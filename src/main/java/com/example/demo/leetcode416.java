package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: leetcode416
 * Package: com.example.demo
 * Description:
 *
 * @Author 浙工大-黄泳涛
 * @Create 2025/2/7 15:11
 * @Version 1.0
 */
public class leetcode416 {
    Map<String,Boolean> map = new HashMap<>();

    public static void main(String[] args) {
        int[] nums = {9,1,2,4,10};
        leetcode416 leetcode416 = new leetcode416();
        System.out.println(leetcode416.canPartition(nums));
    }
    public boolean canPartition(int[] nums) {
        int sum =0;
        for(int num : nums){
            sum += num;
        }
        if(sum%2 !=0){
            return false;
        }
        int l =0;
        int r = 0;
        return dfs(nums,l,r,sum/2,0);

    }
    public boolean dfs(int[] nums,int l,int r,int m,int index){
        if(l > m || r >m){
            return false;
        }
        if(index == nums.length && l == r){
            return true;
        }
        boolean flag;
        String s1 = String.format("%d_%d", index,Math.max(l-r,r-l));
        if(map.containsKey(s1)){
            return map.get(s1);
        }
        String s2 = String.format("%d_%d", index+1,Math.max(l+nums[index]-r,r-l-nums[index]));
        String s3 = String.format("%d_%d", index,Math.max(l-r-nums[index],r+nums[index]-l));
        if(map.containsKey(s2) && map.containsKey(s3)){
            flag = map.get(s2) || map.get(s1);
        }else if(map.containsKey(s2)){
            flag = map.get(s2) || dfs(nums,l,r+nums[index],m,index+1);
        }else if(map.containsKey(s3)){
            flag = map.get(s3) || dfs(nums,l+nums[index],r,m,index+1);
        }else{
            flag =  dfs(nums,l+nums[index],r,m,index+1) || dfs(nums,l,r+nums[index],m,index+1);
        }
        map.put(s1,flag);
        return flag;
    }
}

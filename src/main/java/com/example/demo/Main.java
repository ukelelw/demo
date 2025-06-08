package com.example.demo;

import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
// public class Main {
//     public static void main(String[] args) {
//         Scanner in = new Scanner(System.in);
//         int n = in.nextInt();
//         int m = in.nextInt();
//         int[] dp = new int[m];
//         int[][] packages = new int[m][3];
//         for (int i = 0; i < m; i++) {
//             packages[i][0] = in.nextInt();
//             packages[i][1] = in.nextInt();
//             packages[i][2] = in.nextInt();
//         }
//         Arrays.sort(packages, (a, b) -> Integer.compare(a[1], b[1]));
//         int[] endpoints = new int[m];
//         for (int i = 0; i < m; i++) {
//             endpoints[i] = packages[i][1];
//         }
//         for (int i = 0; i < m; i++) {
//             int s = packages[i][0];
//             int e = packages[i][1];
//             int b = packages[i][2];
//             int pro = e - s + b;
//             int j = binary(endpoints,s) - 1;
//             int best = (j>=0) ? dp[j] : 0;
//             dp[i] = Math.max((i>0 ? dp[i-1] : 0),b + best);
//         }
//         System.out.println(dp[m-1]);
//     }

//     public static int binary(int[] endpoints,int target){
//         int left = 0;
//         int right = endpoints.length-1;
//         while(left<=right){
//             int mid = left + (right-left)/2;
//             if(endpoints[mid] <= target){
//                 left = mid+1;
//             }else{
//                 right = mid -1;
//             }
//         }
//         return left;
//     }
// }




public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);


        int[] dp1 = new int[10001];
        int n = in.nextInt();
        int m = in.nextInt();
        int[] dp = new int[n+1];
        int[][] packages = new int[m][3];
        for (int i = 0; i < m; i++) {
            packages[i][0] = in.nextInt();
            packages[i][1] = in.nextInt();
            packages[i][2] = in.nextInt();
        }
        Arrays.sort(packages, (a, b) -> Integer.compare(a[1], b[1]));
        int index = 0;
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            while (index < packages.length && packages[index][1] == i) {
                int s = packages[index][0];
                int e = packages[index][1];
                int b = packages[index][2];
                dp[e] = Math.max(dp[e], dp[s - 1] + e - s + b);
                index ++;
            }
        }
        System.out.println(dp[n]);

    }
}
package com.example.demo;

import java.util.*;


public class Solution {

    public static void main(String[] args) {

        String ZH = "大多数 的 纵向 方法 学家 建议 应 不惜 一切 代价 避免 减员 。";
        String EN = "Most longitudinal methodologists recommend that attrition should be avoided at all costs .";
        String id_map_of_cn2en = "0-0 2-1 2-2 3-2 4-2 5-3 5-4 6-6 6-7 7-8 8-10 9-9 9-11 10-8 11-5 12-12";
        String[][] res = get_words_pair(ZH, EN, id_map_of_cn2en);
        for(String[] strings : res){
            System.out.println(Arrays.toString(strings));
        }
    }



    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param ZH string字符串 分词后中文字符串
     * @param EN string字符串 分词后英文字符串
     * @param id_map_of_cn2en string字符串 中文词id - 英文词id （ZH\EN按空格分割后的词列表id）
     * @return string字符串二维数组
     */
    public static String[][] get_words_pair (String ZH, String EN,
                                      String id_map_of_cn2en) {
        Map<Set<String>, Set<String>> map = new HashMap<>();
        String[] a1 = ZH.split(" ");
        String[] a2 = EN.split(" ");
        String[] id_map = id_map_of_cn2en.split(" ");
        for (String s : id_map) {
            String[] sss = s.split("-");
            String zz = a1[Integer.parseInt(sss[0])];
            String ee = a2[Integer.parseInt(sss[1])];
            boolean flag = false;
            for (Set<String> set : map.keySet()) {
                if (set.contains(zz)) {
                    map.get(set).add(ee);
                    flag = true;
                    break;
                }
                if(map.get(set) != null && map.get(set).contains(ee)){
                    set.add(zz);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Set<String> set1 = new HashSet<String>();
                set1.add(zz);
                Set<String> set2 = new HashSet<String>();
                set2.add(ee);
                map.put(set1, set2);
                flag = false;
            }

        }
        StringBuilder sb = new StringBuilder();
        List<String> res1 = new ArrayList<>();
        List<String> res2 = new ArrayList<>();
        for (Map.Entry<Set<String>, Set<String>> entry : map.entrySet()) {
            Set<String> set1 = entry.getKey();
            Set<String> set2 = entry.getValue();
            for (String zz : set1) {
                sb.append(zz + " ");
            }
            res1.add(sb.toString().trim());
            sb.setLength(0);
            for (String ee : set2) {
                sb.append(ee + " ");
            }
            res2.add(sb.toString().trim());
            sb.setLength(0);
        }
        String[][] res = new String[2][res1.size()];
        res[0] = res1.toArray(new String[res1.size()]);
        res[1] = res2.toArray(new String[res2.size()]);
        return res;
    }
}
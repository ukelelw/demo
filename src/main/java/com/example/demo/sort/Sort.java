package com.example.demo.sort;

import java.util.*;

/**
 * ClassName: quickSort
 * Package: com.example.demo.sort
 * Description:
 *
 * @Author 浙工大-黄泳涛
 * @Create 2025/2/5 10:17
 * @Version 1.0
 */
public class Sort {

    public static void main(String[] args) {
        int[] array = {16, 9, 40, 27, 40, 23};
        float[] arr = new float[] { 0.12f, 2.2f, 8.8f, 7.6f, 7.2f, 6.3f, 9.0f, 1.6f, 5.6f, 2.4f };
        bucketSort(arr);
        printArray(arr);
        quickSort(array, 0, array.length - 1);
    }

    // 快速排序
    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int pointer = low;
        for (int i = low; i < high; i++) {  // i<high
            if (array[i] <= pivot) {
                int temp = array[i];
                array[i] = array[pointer];
                array[pointer] = temp;
                pointer++;
            }
            System.out.println(Arrays.toString(array));
        }
        int temp = array[pointer];   // i<high 所以最后一次需要在交换一下 high和pointer
        array[pointer] = array[high];
        array[high] = temp;
        return pointer;
    }
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int position = partition(array, low, high);
            quickSort(array, low, position - 1);
            quickSort(array, position + 1, high);
        }
    }


    // 计数排序   计数排序假设了输入数据都属于一个小区间内的整数
    // 获取最大最小值
    private static int[] getMinAndMax(int[] array) {
        int min = array[0];
        int max = array[0];
        for(int i=0;i<array.length;i++){
            if(array[i]<min){
                min = array[i];
            }
            if(array[i]>max){
                max = array[i];
            }
        }
        return new int[]{min,max};
    }
    public static int[] countSort(int[] array) {
        // 获取数组最大最小值
        int[] minAndMax = getMinAndMax(array);
        int min = minAndMax[0];
        int max = minAndMax[1];
        // 新建一个数组，长度为 最大值-最小值 + 1
        int[] count = new int[max - min + 1];
        // 遍历原数组，统计每个数字出现的次数。 每个数字的出现次数记录在count数组上的下标为数字本身减去最小值的位置。
        for(int i=0;i<array.length;i++){
            count[array[i] - min]++;  // 类似于一个数字映射到数组的一个位置上
        }
        // 遍历count数组，执行count[i] += count[i-1]。此时数组上元素表示 原数组的对应数字 在原数组中排序的位置（从一开始）。
        // 如果有相同数字，则表示相同数字排序位置的最大值
        for(int i=1;i<count.length;i++){
            count[i] += count[i-1];
        }
        // 结果数组
        int[] result = new int[array.length];
        // 倒序遍历原数组，从count数组中获取原数组中当前数字在原数组中的排序位置（从1开始），则需要减去1即为正确的排序索引
        for(int i=array.length-1;i>=0;i--){
            result[count[array[i] - min]-1] = array[i];
            count[array[i] - min]--;
        }
        return result;
    }


    // 桶排序

    // 基本思路
   /* public static List<Integer> bucketSort(List<Integer> arr, int bucket_size) {
        if (arr.size() < 2 || bucket_size == 0) {
            return arr;
        }
        int[] extremum = getMinAndMax(arr);
        int minValue = extremum[0];
        int maxValue = extremum[1];
        int bucket_cnt = (maxValue - minValue) / bucket_size + 1;
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucket_cnt; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        for (int element : arr) {
            int idx = (element - minValue) / bucket_size;
            buckets.get(idx).add(element);
        }
        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.get(i).size() > 1) {
                buckets.set(i, sort(buckets.get(i), bucket_size / 2));
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (List<Integer> bucket : buckets) {
            for (int element : bucket) {
                result.add(element);
            }
        }
        return result;
    }*/


    // 桶排序实现
    public static void bucketSort(float[] arr) {
        // 新建一个桶的集合
        ArrayList<LinkedList<Float>> buckets = new ArrayList<LinkedList<Float>>();
        for (int i = 0; i < 10; i++) {  // 桶的数目
            // 新建一个桶，并将其添加到桶的集合中去。
            // 由于桶内元素会频繁的插入，所以选择 LinkedList 作为桶的数据结构
            buckets.add(new LinkedList<Float>());
        }
        // 将输入数据全部放入桶中并完成排序
        for (float data : arr) {
            int index = getBucketIndex(data);
            insertSort(buckets.get(index), data);
        }
        // 将桶中元素全部取出来并放入 arr 中输出
        int index = 0;
        for (LinkedList<Float> bucket : buckets) {
            for (Float data : bucket) {
                arr[index++] = data;
            }
        }
    }

    /**
     * 计算得到输入元素应该放到哪个桶内
     */
    public static int getBucketIndex(float data) {
        // 这里例子写的比较简单，仅使用浮点数的整数部分作为其桶的索引值
        // 实际开发中需要根据场景具体设计
        return (int) data;
    }

    /**
     * 我们选择插入排序作为桶内元素排序的方法 每当有一个新元素到来时，我们都调用该方法将其插入到恰当的位置
     */
    public static void insertSort(List<Float> bucket, float data) {
        ListIterator<Float> it = bucket.listIterator();
        boolean insertFlag = true;
        while (it.hasNext()) {
            if (data <= it.next()) {
                it.previous(); // 把迭代器的位置偏移回上一个位置
                it.add(data); // 把数据插入到迭代器的当前位置
                insertFlag = false;
                break;
            }
        }
        if (insertFlag) {
            bucket.add(data); // 否则把数据插入到链表末端
        }
    }

    public static void printArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }


    // 桶排序（寻找第K大元素）
    public int findKthLargest(int[] nums, int k) {
        int[] minAndMax = getMinAndMax(nums);
        int min = minAndMax[0];
        int max = minAndMax[1];
        int buckets_size = 2;  // 预计每个桶中元素个数
        int bucket_cnt= (max - min) / buckets_size + 1; // 桶的个数(假设数据均匀分布)
        List<LinkedList<Integer>> buckets = new ArrayList<>();
        // 桶初始化
        for(int i =0;i<bucket_cnt;i++){
            buckets.add(new LinkedList<>());
        }
        // 将数据插入桶
        for(int e : nums){
            // 计算索引(这个计算索引是一个映射函数,可以根据具体进行调整,目的是保证数据均匀分布在各个桶中)
            int idx = getBucketIndex(e,buckets_size,min);
            insertSort(buckets.get(idx),e);  //桶内排序的方式(可灵活变动)(这里使用插入排序)
        }

        int[] result = new int[nums.length];
        int index = 0;
        for(List<Integer> bucket : buckets){
            for(int e : bucket){
                result[index++] = e;
            }
        }
        return result[nums.length - k];

    }

    public int getBucketIndex(int e,int buckets_size,int min){
        return (e - min) / buckets_size;
    }

    public void insertSort(List<Integer> bucket,int e){
        ListIterator<Integer> it = bucket.listIterator();
        boolean flag = true;
        while(it.hasNext()){
            if(e <= it.next()){
                it.previous(); // 回退
                it.add(e);
                flag = false;
                break;
            }
        }
        if(flag){
            bucket.add(e); // 插入末尾
        }

    }
}

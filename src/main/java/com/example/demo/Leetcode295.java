package com.example.demo;

import java.util.ArrayList;
import java.util.List;

class Leetcode295 {

    private List<Integer> minHeap;
    private List<Integer> maxHeap;
    private int minHeapSize;
    private int maxHeapSize;

    public static void main(String[] args) {
        Leetcode295 obj = new Leetcode295();
        obj.addNum(40);
        double median = obj.findMedian();
        obj.addNum(12);
        double median1 = obj.findMedian();
        obj.addNum(16);
        double median2 = obj.findMedian();
        obj.addNum(4);
        double median3 = obj.findMedian();
        obj.addNum(5);
        double median4 = obj.findMedian();
        obj.addNum(6);
        double median5 = obj.findMedian();
        obj.addNum(7);
        double median6 = obj.findMedian();
        obj.addNum(8);
        double median7 = obj.findMedian();
        obj.addNum(9);
        double median8 = obj.findMedian();
        obj.addNum(10);
        double median9 = obj.findMedian();

    }

    public Leetcode295() {
        minHeap = new ArrayList<>();
        maxHeap = new ArrayList<>();
        minHeapSize = 0;
        maxHeapSize = 0;
    }

    public void addNum(int num) {
        // 插入大顶堆
        if(maxHeap.size()==0 || num <= maxHeap.get(0)) {
            // 插入大顶堆
            maxHeapSize++;
            insertMaxHeap(maxHeap, num);
            if (maxHeapSize > minHeapSize + 1) {
                // 删除大顶堆堆顶元素
                int l = maxHeap.get(0);
                maxHeap.set(0, maxHeap.get(maxHeapSize - 1));
                maxHeap.remove(maxHeapSize - 1);
                maxHeapSize--;
                maxHeapify(0);
                //  放入小顶堆中
                minHeapSize++;
                insertMinHeap(minHeap, l); // 将堆顶元素插入小顶堆
            }
        }else{  // 插入小顶堆
            minHeapSize++;
            insertMinHeap(minHeap,num);
            if(minHeapSize > maxHeapSize){
                // 删除小顶堆堆顶元素
                int r = minHeap.get(0);
                minHeap.set(0,minHeap.get(minHeapSize - 1));
                minHeap.remove(minHeapSize - 1);
                minHeapSize--;
                minHeapify(0);
                // 放入大顶堆中
                maxHeapSize++;
                insertMaxHeap(maxHeap,r);
            }
        }
    }

    public void maxHeapify(int index){  // 从堆顶开始调整
        int l = index*2+1,r = index*2+2,largest=index;
        if(l<maxHeapSize && maxHeap.get(l) > maxHeap.get(largest)){
            largest = l;
        }
        if(r<maxHeapSize && maxHeap.get(r) > maxHeap.get(largest)){
            largest = r;
        }
        if(largest != index){
            int tmp = maxHeap.get(index);
            maxHeap.set(index,maxHeap.get(largest));
            maxHeap.set(largest,tmp);
            maxHeapify(largest);
        }
    }

    public void minHeapify(int index){  // 从堆顶开始调整
        int l = index*2+1,r = index*2+2,smallest=index;
        if(l<minHeapSize && minHeap.get(l) < minHeap.get(smallest)){
            smallest = l;
        }
        if(r<minHeapSize && minHeap.get(r) < minHeap.get(smallest)){
            smallest = r;
        }
        if(smallest != index){
            int tmp = minHeap.get(index);
            minHeap.set(index,minHeap.get(smallest));
            minHeap.set(smallest,tmp);
            minHeapify(smallest);
        }
    }


    public void insertMinHeap(List<Integer> heap,int num){
        heap.add(num); // 放到最后
        int currentIndex = minHeapSize-1; 
        while(currentIndex > 0){
            int parentIndex = currentIndex % 2 == 0 ? currentIndex/2-1 : currentIndex /2;
            if(heap.get(parentIndex) > num){
                int tmp = heap.get(parentIndex);
                heap.set(parentIndex,num);
                heap.set(currentIndex,tmp);
                currentIndex = parentIndex;
            }else{
                break;
            }
        }
    }

    public void insertMaxHeap(List<Integer> heap,int num){
        heap.add(num); // 放到最后
        int currentIndex = maxHeapSize-1; 
        while(currentIndex > 0){
            int parentIndex = currentIndex % 2 == 0 ? currentIndex/2-1 : currentIndex /2;
            if(heap.get(parentIndex) < num){
                int tmp = heap.get(parentIndex);
                heap.set(parentIndex,num);
                heap.set(currentIndex,tmp);
                currentIndex = parentIndex;
            }else{
                break;
            }
        }
    }

    public double findMedian() {
        if(maxHeapSize > minHeapSize){
            return maxHeap.get(0);
        }else{
            return (maxHeap.get(0) + minHeap.get(0)) / 2.0;
        }
    }
}
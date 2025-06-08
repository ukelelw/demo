package com.example.demo;

import java.util.*;

/**
 * @author zk
 * @version 1.0.0
 * @ClassName Grap.java
 * @Description TODO 图(深度优先遍历和广度优先遍历)
 * @createTime 2021年09月28日 17:05:00
 */
public class Graph {
    private List<String> vertexList; // 存储图的顶点
    private int[][] edges; // 存储图对应的邻接矩阵
    private int numOfEdges; // 表示边的个数
    boolean[] isVisited; // 表示对应下表的顶点是否遍历过


    public static void main(String[] args) {

        String[] vertexs = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(vertexs.length);
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.showGraph();
        //graph.dfs();
        graph.bfs();
    }

    // 有参构造
    public Graph(int n) {
        this.vertexList = new ArrayList<>(n);
        this.edges = new int[n][n];
        this.numOfEdges = 0;
        this.isVisited = new boolean[n];
    }

    // 插入节点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    // 添加边
    public void insertEdge(int v1, int v2, int weight) {
        this.edges[v1][v2] = weight;
        this.edges[v2][v1] = weight;
        this.numOfEdges++;
    }

    // 返回顶点个数
    public int getNumOdVertex() {
        return this.vertexList.size();
    }

    // 返回边的个数
    public int getNumOfEdge() {
        return this.numOfEdges;
    }

    // 返回结点i(下标)对应得数据 0 -> A  1->B
    public String getValueByIndex(int i) {
        return this.vertexList.get(i);
    }

    // 返回 v1 v2 的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    // 显示图对应的矩阵
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    //得到第一个邻接结点的下标 w
    /**
     *
     * @param index
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index){
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i]>0){
                return i;
            }
        }
        // 不存在
        return -1;
    }

    //根据前一个邻接结点的下标来获取下一个邻接结点
    public int getNextNeighbor(int v1,int v2){
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i]>0){
                return i;
            }
        }
        return -1;
    }

    //从某个节点i开始的 深度优先遍历算法
    public void dfs(boolean[] isVisited,int i){
        System.out.println(getValueByIndex(i)+"->");
        isVisited[i] = true;
        int w = getFirstNeighbor(i);
        while(w!=-1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            w = getNextNeighbor(i,w);
        }
    }
    //对 dfs 进行一个重载, 遍历我们所有的结点，并进行 dfs。防止图非联通的情况。
    public void dfs(){
        for (int i = 0; i < vertexList.size(); i++) {
            if (!isVisited[i]){
                dfs(this.isVisited,i);
            }
        }
    }

    // 广度优先搜索算法
    public void  bfs(boolean[] isVisited,int i){
        int u; // 队列的头节点
        int w; // 下一个邻接点
        Deque<Integer> queue = new LinkedList<>();  // 双端队列  记录节点的访问顺序
        System.out.println(getValueByIndex(i));
        isVisited[i] = true;
        queue.addLast(i);
        while(!queue.isEmpty()){
            u = queue.removeFirst();
            w = getFirstNeighbor(u);
            while(w!=-1){
                if(!isVisited[w]) {
                    System.out.println(getValueByIndex(w));
                    isVisited[w] = true;
                    queue.addLast(w);
                }
                w = getNextNeighbor(u,w);
            }
        }
    }

    // 对所有节点进行广度优先搜索，防止图非联通的情况
    public void bfs(){
        for (int i = 0; i < vertexList.size(); i++) {
            if (!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }


}

package com.example.demo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
   }
}
public class OrderTree {
    // 递归
    public List<Integer> orderRecur(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        orderRecur(root, ans);
        return ans;

    }
    public void orderRecur(TreeNode root,List<Integer> ans){
        if(root == null){
            return;
        }
        // 前序
        ans.add(root.val);
        orderRecur(root.left, ans);
        orderRecur(root.right, ans);

        // 中序
        orderRecur(root.left, ans);
        ans.add(root.val);
        orderRecur(root.right, ans);

        // 后序
        orderRecur(root.left,ans);
        orderRecur(root.right, ans);
        ans.add(root.val);
    }

    // 迭代 本质上通过使用栈对递归的模拟
    // 前序
    public List<Integer> preOrderIteration(TreeNode root){
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.push(root); // 先入根
        while(!queue.isEmpty()){
            TreeNode node = queue.pop();
            ans.add(node.val); // 访问根
            if(node.right != null){
                queue.push(node.right);  // 右节点入栈
            }
            if(node.left != null){
                queue.push(node.left);   // 左节点入栈
            }
        }
        return ans;
    }

    // 中序
    public List<Integer> inOrderIteration(TreeNode root){
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        while(!queue.isEmpty() || root != null){
            // 先找最左的节点
            while(root != null){
                queue.push(root);
                root = root.left;
            }
            // 左子树没有了，此时为根
            root = queue.pop();
            // 访问根
            ans.add(root.val);
            // 右子树
            root = root.right;
        }
        return ans;
    }

    // 后序遍历 ***
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode prev = null;
        while( root != null || !queue.isEmpty()){
            while(root != null){
                queue.push(root);
                root = root.left;
            }
            root = queue.pop(); // 没有左子树了，拿出根
            // 若没有右子树 / 右子树已经访问过了
            if(root.right == null || root.right == prev){
                ans.add(root.val); // 访问根
                prev = root; // 每访问一个元素，记录到prev
                root = null; // 访问完一个元素，下一次需要从栈中拿出下一个，root设置为null
            }else{
                // 有右子树且右子树没有访问过，访问右子树
                queue.push(root);
                root = root.right;
            }
        }
        return ans;
    }



    // morris
    // 前序
    public List<Integer> preOrderMorris(TreeNode root){
        List<Integer> ans = new ArrayList<>();
        TreeNode predecessor = null;  // 记录左子树是否遍历完！！！
        while(root != null){
            if(root.left != null){ // 左子树不为空
                predecessor = root.left;
                // 寻找左子树的最右边的节点，也就是中序遍历时当前root的前面节点
                while(predecessor.right != null && predecessor.right != root){
                    predecessor = predecessor.right;
                }
                if(predecessor.right == null){ // 没遍历过左子树
                    ans.add(root.val); // 访问根
                    // 将根赋给 前面节点的 右子树
                    predecessor.right = root;
                    root = root.left; // 访问左子树
                }else{
                    // 已经遍历过左子树，访问右子树
                    predecessor.right = null; // 断开
                    root = root.right;
                }
            }else{
                // 左子树为空，访问右子树
                ans.add(root.val); // 访问根
                root = root.right; // 遍历右子树
            }
        }
        return ans;
    }

    // 中序
    public List<Integer> inOrderMorris(TreeNode root){
        List<Integer> ans = new ArrayList<>();

        TreeNode predecessor = null;  // 记录左子树是否遍历完！！！
        while(root != null){
            if(root.left != null){ // 左子树不为空
                predecessor = root.left;
                // 寻找左子树的最右边的节点，也就是中序遍历时当前root的前面节点
                while(predecessor.right != null && predecessor.right != root){
                    predecessor = predecessor.right;
                }
                if(predecessor.right == null){ // 没遍历过左子树
                    // 将根赋给 前面节点的 右子树
                    predecessor.right = root;
                    root = root.left; // 访问左子树
                }else{
                    // predecessor.right == root  左子树已经访问完
                    ans.add(root.val); // 记录根
                    predecessor.right = null;  // 断开
                    root = root.right;  // 访问右子树
                }
            }else{
                // 左子树为空，访问右子树
                ans.add(root.val);
                root = root.right;
            }
        }
        return ans;
    }

    // 后序





}



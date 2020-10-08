package 树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 翻转一棵二叉树。
 * https://leetcode-cn.com/problems/invert-binary-tree/
 */
public class _226_翻转二叉树 {

    public Node invertTree(Node root) {
        if (root == null) return null;
        //要翻转一颗二叉树首先应该遍历到每一个元素

        //1.使用递归，前序遍历
        //先调换根节点的左右子树
        /*Node temp = root.right;
        root.right = root.left;
        root.left = temp;
        //然后递归调用左右子树的翻转方法继续翻转
        invertTree(root.right);
        invertTree(root.left);*/

        //2.使用递归，中序遍历
        /*invertTree(root.left);
        Node temp = root.right;
        root.right = root.left;
        root.left = temp;
        invertTree(root.left);//因为刚刚翻转了根节点的左右子树，因此这里的left是之前的right*/

        //3.使用递归，后序遍历
        /*invertTree(root.left);
        invertTree(root.right);
        Node temp = root.right;
        root.right = root.left;
        root.left = temp;*/

        //4.使用迭代方式，层序遍历
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node Node = queue.poll();
            Node temp = Node.right;
            Node.right = Node.left;
            Node.left = temp;
            if (Node.left != null){
                queue.offer(Node.left);
            }
            if (Node.right != null){
                queue.offer(Node.right);
            }
        }
        return root;
    }
}

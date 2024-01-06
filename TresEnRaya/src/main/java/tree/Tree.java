/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

/**
 *
 * @author ander
 */
public class Tree<E> {
    
    private TreeNode<E> root;
    
    public Tree () {
        this.root = null;
    }    
    
    public Tree (E e) {
        TreeNode<E> n = new TreeNode<E>(e);
        this.root = n;
    }
    
    public boolean isEmpty () {
        return this.root == null;
    }

    public E getRoot() {
        return root.getContent();
    }
    
    public TreeNode getRootNode () {
        return this.root;
    }

    public void setRootNode(TreeNode<E> root) {
        this.root = root;
    }
    
    public void setRoot (E content) {
        this.root.setContent(content);
    }
    
    public boolean isLeaf () {
        return this.root.getChildren().isEmpty();
    }    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
public class Tree<E> {

    private TreeNode<E> root;

    public Tree() {
        this.root = null;
    }

    public Tree(E e) {
        TreeNode<E> n = new TreeNode<E>(e);
        this.root = n;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public E getRoot() {
        return root.getContent();
    }

    public TreeNode getRootNode() {
        return this.root;
    }

    public void setRootNode(TreeNode<E> root) {
        this.root = root;
    }

    public void setRoot(E content) {
        this.root.setContent(content);
    }

    public boolean isLeaf() {
        return this.root.getChildren().isEmpty();
    }

    public String toString() {
        return treeToString(root, 0);
    }

    private String treeToString(TreeNode<E> node, int level) {
        StringBuilder result = new StringBuilder();

        if (node != null) {
            for (int i = 0; i < level; i++) {
                result.append("\t");
            }

            result.append(node.getContent()).append("\n");

            for (Tree<E> child : node.getChildren()) {
                result.append(treeToString(child.getRootNode(), level + 1));
            }
        }

        return result.toString();
    }
}

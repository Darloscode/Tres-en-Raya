/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alvarez Orellana Moises
 * @author Flores González Carlos
 * @author Maldonado Jaramillo Paulette
 */
public class TreeNode<E> {
    
    private E content;
    private List<Tree<E>> children;

    public TreeNode(E content) {
        this.content = content;
        this.children = new LinkedList<Tree<E>>();
    }
    
    public TreeNode() {
        this.content = null;
        this.children = new LinkedList<Tree<E>>();
    }    

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public List<Tree<E>> getChildren() {
        return children;
    }
    
    public E getPrimero() {
        return (E) this.children.get(0);
    }

    public void setChildren(List<Tree<E>> children) {
        this.children = children;
    }   
    public void addChildren(Tree<E> content){
        this.children.add(content);
        
    }
    public String toString() {
        return content.toString();
    }
}

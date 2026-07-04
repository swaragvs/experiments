import java.io.*;
import java.util.Scanner;
class Node
{
 public int data;
 public Node lchild;
 public Node rchild;
 public Node(int d)
 {
  data=d;
  lchild=null;
  rchild=null;
 }
}
class Tree
{
 public Node root;
 public Tree()
 {
  root=null;
 }
 public void insert(int j)
 {
  Node nl=new Node(j);
  if(root==null)
  {
   root=nl;
  }
  else
  {
   Node curr=root;
   Node parent;
   while(true)
   {
    parent=curr;
    if(j<curr.data)
    {
     curr=curr.lchild;
     if(curr==null)
     {
      parent.lchild=nl;
      return;
     }
    }
    else
    {
     curr=curr.rchild;
     if(curr==null)
     {
      parent.rchild=nl;
      return;
     }
    }
   }
  }
 }
 public void inOrder(Node lroot)
 {
  if(lroot!=null)
  {
   inOrder(lroot.lchild);
   System.out.print(lroot.data+"  ");
   inOrder(lroot.rchild);
  }
 }
 public void getinOrder()
 {
  inOrder(root);
 }
 public void search(int key)
 {
  if(root==null)
  {
   System.out.println("EMPTY TREE!!!");
  }
  else
  {
   Node curr=root;
   while(curr!=null)
   {
    if(key==curr.data)
    {
     System.out.print(key+" IS PRESENT!!!");
     return;
    }
    else if(key<curr.data)
    {
     curr=curr.lchild;
    }
    else
    {
     curr=curr.rchild;
    }
   }
   System.out.println(key+" NOT FOUND!!!");
  }
 }
}
class BinSerTree
{
 public static void main(String str[])
 {
  int a,n,d,key,choice;
  Tree tr=new Tree();
  Scanner sc=new Scanner(System.in);
  Node lroot=null;
  do
  {
   System.out.print("BINARY SEARCH TREE OPERATION:\n1.Insert\n2.Inorder Traversal\n3.Search\nEnter your choice:");
   choice=sc.nextInt();
   switch(choice)
   {
    case 1:
     System.out.print("Enter the number of nodes:");
     n=sc.nextInt();
     for(int i=0; i<n; i++)
     {
      System.out.print("Enter the value for node "+(i+1)+":");
      d=sc.nextInt();
      tr.insert(d);
     }
     break;
    case 2:
     System.out.println("Inorder Traversal:");
     tr.getinOrder();
     System.out.println();
     break;
    case 3:
     System.out.print("Enter value to search in the BST:");
     key=sc.nextInt();
     tr.search(key);
     break;
    default:System.out.println("INVALID CHOICE!!!");
   }
   System.out.print("\nDo you wish to continue?(if yes, press 1, else press 0)");
   a=sc.nextInt();
  }while(a==1);
 }
}




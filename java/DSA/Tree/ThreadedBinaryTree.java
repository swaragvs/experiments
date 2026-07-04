import java.io.*;
import java.util.Scanner;
class TreeLink
{
 public int data;
 public TreeLink lchild;
 public TreeLink rchild;
 public boolean rthread;
 public TreeLink(int d)
 {
  data=d;
  lchild=rchild=null;
  rthread=true;
 }
 public void displayLink()
 {
  System.out.print(data+"  ");
 }
}
class Tree
{
 public TreeLink root;
 public void insert(int d)
 {
  TreeLink nl=new TreeLink(d);
  if(root==null)
  {
   root=nl;
   return;
  }
  TreeLink cur=root;
  TreeLink par=null;
  while(true)
  {
   par=cur;
   if(d<cur.data)
   {
    cur=cur.lchild;
    if(cur==null)
    {
     par.lchild=nl;
     nl.rchild=par;
     nl.rthread=true;
     return;
    }
   }
   else
   {
    if(!par.rthread)
    {
     cur=cur.rchild;
     if(cur==null)
     {
      par.rchild=nl;
      par.rthread=false;
      nl.rchild=par;
      nl.rthread=true;
      return;
     }
    }
    else
    {
     nl.rchild=par.rchild;
     par.rchild=nl;
     par.rthread=false;
     nl.rthread=true;
     return;
    }
   }
  }
 }
 public void inOrder()
 {
  TreeLink q,p=root;
  do
  {
   q=null;
   while(p!=null)
   {
    q=p;
    p=p.lchild;
   }
   if(q!=null)
   {
    q.displayLink();
    p=q.rchild;
    while(q.rthread && p!=null)
    {
     p.displayLink();
     q=p;
     p=p.rchild;
    }
   }
  }while(q!=null);
 }
}
class ThreadTree
{
 public static void main(String str[])
 {
  int a,n,d,choice;
  Scanner sc=new Scanner(System.in);
  Tree tr=new Tree();
  do
  {
   System.out.print("THREADED TREE OPERATION:\n1.Insert\n2.Inorder Traversal\nEnter your choice:");
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
     System.out.println("INORDER TRAVERSAL!!!");
     tr.inOrder();
     break;
    default: System.out.println("INVALID CHOICE!!!");
   }
   System.out.println("\nDo you wish to continue?(If yes, press 1, else press 0)");
   a=sc.nextInt();
  }while(a==1);
 }
}




import java.util.Scanner;
import java.io.*;

class DLink
{
 public int data;
 public DLink next;
 public DLink prev;
 public DLink(int d)
 {
  data=d;
  next=null;
  prev=null;
 }
 public void displayLink()
 {
  System.out.println(data+" ");
 }
}

class DLinkedList
{
  public DLink first;
  public DLink last;
  public DLinkedList()
  {
   first=null; last=null;
  }
  public void insertLast(int a)
  {
   DLink nl=new DLink(a);
   if(first==null)
   {  first=nl;}
   else
   {
    last.next=nl;
    nl.prev=last;
    }
   last=nl;
  }
 public void insert(int d,DLink cur)
 {
  DLink nl=new DLink(d);
  if(cur==null)
  {
   nl.prev=last;
   last.next=nl;
   last=nl;
  }
 else
 {
  nl.prev=cur.prev;
  cur.prev.next=nl;
  nl.next=cur;
  cur.prev=nl;
 }
}

public void displayForward()
{
 DLink cur=first;
 while(cur!=null)
 {
  cur.displayLink();
  cur=cur.next;
 }
}
}

class DLL2
{
 public static void main(String st[])
 {
  Scanner sc=new Scanner(System.in);
  DLinkedList list1=new DLinkedList();
  DLinkedList list2=new DLinkedList();
  System.out.println("Enter the number of elements in list 1:");
  int n=sc.nextInt();
  System.out.println("Enter "+n+" elements:");
  for(int i=0;i<n;i++)
  {
   int x=sc.nextInt();
   list1.insertLast(x);
  }
  System.out.println("Enter the number of elements in list 2:");
  int m=sc.nextInt();
  System.out.println("Enter "+m+" elements:");
  for(int i=0;i<m;i++)
  {
   int y=sc.nextInt();
   list2.insertLast(y);
  }
  System.out.println("List 1:");
  list1.displayForward();
  System.out.println("List 2:");
  list2.displayForward();
  DLink temp1=list1.first;
  DLink temp2=list2.first;
  while(temp1!=null || temp2!=null)
  {
   if(temp1!=null)
     temp1=temp1.next;
   if(temp2!=null)
   {
    list1.insert(temp2.data,temp1);
    temp2=temp2.next;
   }
  }
 System.out.println("The merged list is:");
 list1.displayForward();
}
}
  


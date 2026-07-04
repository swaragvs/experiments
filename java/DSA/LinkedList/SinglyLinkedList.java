
import java.io.*;
import java.util.Scanner;
class Link
 {
  public int data;
  public Link next;
  public Link(int d)
   {
    data=d;
    next=null;
   }
 }
class LinkedList
 {
  Link first;
  public void insertFirst(int d)
   {
    Link nl=new Link(d);
    nl.next=first;
    first=nl;
   }
  public Link getLink(int n)
   {
    Link cur=first;
    int count=0;
    while(cur!=null&&count<n)
     {
      cur=cur.next;
      count++;
     }
    return cur;
   }
  public void insertAt(int d,int n)
   {
    if(n<0)
      System.out.println("INVALID POSITION!!");
    Link nl=new Link(d);
    if(n==0)
     {
      nl.next=first;
      first=nl;
     }
    Link prev=getLink(n-1);
    if(prev==null)
      System.out.println("OUT OF BOUND!!");
    nl.next=prev.next;
    prev.next=nl;
   }
  public Link deleteFirst()
   {
    Link temp=first;
    if(first==null)
      System.out.println("EMPTY LIST!!");
    else
     {
      first=first.next;
     }
    return temp;
   }
  public void replaceAt(int d,int n)
   {
    Link l=first;
    for(int i=1;(i<(n)&&(l!=null));i++)
     {
      l=l.next;
     }
    if(l==null)
      System.out.println("INVALID CHOICE!!");
    else 
      l.data=d;
   }
  public void displayList()
   {
    Link cur=first;
    while(cur!=null)
     {
      System.out.print(cur.data+"   ");
      cur=cur.next;
     }
   }
 }
class ListInsDelRep
 {
  public static void main(String str[])
   {
    LinkedList ob=new LinkedList();
    Scanner sc=new Scanner(System.in);
    int choice, d,n,a,s;
    System.out.println("Enter the number of elements in the list:");
    s=sc.nextInt();
    System.out.println("Enter "+s+" elements:");
    for(int i=0;i<s;i++)
     {
       d=sc.nextInt();
       ob.insertFirst(d);
     }
    System.out.println("The given list is:");
    ob.displayList();
    do
     {
      System.out.print("\nMENU\n1.Insert at nth position\n2.Delete the first node\n3.Replace value of nth node\n4.Display");
      System.out.println("\nEnter your choice:");
      choice=sc.nextInt();
      switch(choice)
       {
        case 1:
          System.out.print("Enter the data to be inserted:");
          d=sc.nextInt();
          System.out.print("Enter the position:");
          n=sc.nextInt();
          ob.insertAt(d,n);
          break;
        case 2:
          Link ln=ob.deleteFirst();
          System.out.print("Deleted element "+ln.data+"!!");
          break;
        case 3:
          System.out.print("Enter data to be replaced:");
          d=sc.nextInt();
          System.out.print("Enter the position:");
          n=sc.nextInt();
          ob.replaceAt(d,n);
          break;
        case 4:
          System.out.println("List:");
          ob.displayList();
          break;
        default:System.out.println("INVALID CHOICE!!");
       }
      System.out.println("\nDo you wish to continue?(If yes, press 1, else press 0)");
      a=sc.nextInt();
     }while(a==1);
   }
 }
 
 
 



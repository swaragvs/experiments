import java.io.*;
import java.util.Scanner;
class Link
{
public int data;
public Link next;
public Link (int d)
{
data=d;
next=null;
}
public void displaylink()
{
System.out.print(data+"  ");
}
}
class linkedlist
{
public Link first;
public linkedlist()
{
first=null;
}
public void insertfirst(int d)
{
Link nl=new Link(d);
nl.next=first;
first=nl;
}
public Link deletefirst()
{int d;
Link temp=first;
first=first.next;
return temp;
}
public boolean isEmpty()
{
return (first==null);
}
public void displaylist()
{
Link cur=first;
while(cur!=null)
{
cur.displaylink();
cur=cur.next;
}}}

class Linkstack
{
private linkedlist l;
public Linkstack()
{
l=new linkedlist();
}
public boolean isEmpty()
{return l.isEmpty();}

public void push(int j)
{
l.insertfirst(j);
}
public  Link pop()
{
return (l.deletefirst());
}
public void displaylist()
{
l.displaylist();
}
}
class Linked
{
public static void main(String st[])
{
int n,a;
Scanner sc=new Scanner(System.in);
System.out.println("enter the no of elements to be inserted:");
n=sc.nextInt();
Linkstack s=new Linkstack();
for(int i=1;i<=n;i++)
{
System.out.println("enter the elements:");
int m=sc.nextInt();
s.push(m);
}
System.out.println("list of integers are:");
s.displaylist();
do
{
if(!s.isEmpty())
{
Link c=s.pop();
System.out.println("deleted elements are:"+c.data);
System.out.println("list of integers are:");
s.displaylist();
}
if(s.isEmpty())
{System.out.println("empty");}
System.out.println("do you want to continue?if yes press 1 else press 0");
a=sc.nextInt();
}
while(a==1);
}}



















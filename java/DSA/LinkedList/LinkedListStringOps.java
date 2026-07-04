import java.io.*;
import java.util.Scanner;
class Node
{
public char data;
public Node next;
public Node(char d)
{
next=null;
data=d;
}
}
class Linkedlist
{
Node first;
public void Insertfirst(char d)
{
Node nl=new Node(d);
nl.next=first;
first=nl;
}
public void reverse()
{
Node pre=null;
Node cur=first;
while(cur!=null)
{
Node next=cur.next;
cur.next=pre;
pre=cur;
cur=next;
}
first=pre;
}
public void sort()
{
Node cur=first;
while(cur!=null)
{
Node next=cur.next;
boolean swap=false;
while(next!=null)
{
if(cur.data>next.data)
{
char temp=cur.data;
cur.data=next.data;
next.data=temp;
swap=true;
}
cur=cur.next;
next=next.next;
}
if(!swap)
{break;
}
cur=first;
}}

public void display()
{
Node cur=first;
while(cur!=null)
{
System.out.print(cur.data+"");
cur=cur.next;
}
System.out.println();
}}
class Rev
{
public static void main(String [] arg)

{
Scanner sc=new Scanner(System.in);
Linkedlist ob=new Linkedlist();
System.out.println("enter characters one by one(- to stop)");
char ch;
do{
ch=sc.next().charAt(0);
if(ch!='-')
{
ob.Insertfirst(ch);
}}

while(ch!='-');
ob.reverse();
System.out.println("enter what to do with the string:\n1.reverse\n2.sort\n3.display\n4.exit");
int choice;
boolean q=true;
do{
choice=sc.nextInt();
switch(choice)
{
case 1:
{ob.reverse();
ob.display();}
break;

case 2:
{
ob.sort();
ob.display();
break;
}
case 3:
{ob.display();
break;
}
case 4:
q=false;
}
}while(q);
}}





















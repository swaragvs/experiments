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
}}
class Linkedlist
{
public Link first;
public Linkedlist()
{
first=null;
}
public void insertfirst(int d)
{
Link nl=new Link(d);
nl.next=first;
first=nl;
}
public int frequency(int key)
{int count =0;
Link cur=first;

while(cur!=null)
{
if(cur.data==key)
{count++;
}cur=cur.next;
}
return count;}
public void removedup()
{
Link cur=first ; 
while(cur!=null)
{
Link temp=cur;
while(temp.next!=null)
{
if(temp.next.data==cur.data)
{
temp.next=temp.next.next;
}
else

temp=temp.next;
}
cur=cur.next;
}}
public void displaylist()
{
Link cur=first;
while(cur!=null)
{
System.out.print(cur.data+" ");
cur=cur.next;
}}}
class Integer
{
public static void main(String st[])
{
int ch,a=1;
Scanner sc=new Scanner(System.in);
Linkedlist List=new Linkedlist();
while(a==1)
{
System.out.println("\n1.insert\n2.count for frequency\n3.remove duplicates\n4.display\nenter choice:");
ch=sc.nextInt();
switch(ch)
{
case 1:{
System.out.println("enter element:");
int elem=sc.nextInt();
List.insertfirst(elem);}
break;
case 2:
{
System.out.println("enter key element:");
int key=sc.nextInt();
int freq=List.frequency(key);
System.out.println(+freq);
}break;
case 3:
{
List.removedup();
System.out.println("duplicate removed from the list"); 
}break;
case 4:
{
List.displaylist();
}break;
}

}}}



























import java.util.*;
class node
{
int data;
node pre,next;
public node(int data)
{
this.data=data;
pre=next=null;
}
}
class dll
{
node first,last;
public void insertFirst(int data)
{
node nl=new node(data);
if(isEmpty())
first=last=nl;
else
{
nl.next=first;
first.pre=nl;
first=nl;
}
}
public void insertLast(int d)
{
node nl=new node(d);
nl.pre=last;
last.next=nl;
last=nl;
}
public int deleteFirst()
{
int t=first.data;
first=first.next;
first.pre=null;
return t;
}
public int deleteLast()
{
int t=last.data;
last=last.pre;
last.next=null;
return t;
}
public int peek()
{
return first.data;
}
public boolean isEmpty()
{
if(first==last &&first== null)
return true;
else 
return false;
}
public void display()
{
node temp=first;
while(temp!=null)
{
System.out.print(temp.data+" ");
temp=temp.next;
}
System.out.println();
}
}
class Dequeue
{
public static void main(String[]args)
{
Scanner sc=new Scanner (System.in);
dll queue=new dll();
int ch;
System.out.println("Enter 1 to insert first, 2 to insert last, 3 to delete first, 4 to delete last, 5 to peek, 6 to display, 7 to exit");
do
{
System.out.println("Enter choice");
ch=sc.nextInt();
switch(ch)
{
case 1: System.out.println("Enter element");
int e=sc.nextInt();
queue.insertFirst(e);break;
case 2: System.out.println("Enter element");
 e=sc.nextInt();
queue.insertLast(e);break;
case 3:if(!queue.isEmpty()) 
{int d=queue.deleteFirst();
System.out.println("Dequeued element= "+d);
}break;
case 4: if(!queue.isEmpty())
{int d=queue.deleteLast();
System.out.println("Dequeued element= "+d);
}break;
case 5: int p=queue.peek();
System.out.println("Peeked element= "+p);break;
case 6:System.out.println("Queue elements: ");
queue.display();break;
case 7: break;
default: System.out.println("Invalid Choice");
}
}
while(ch!=7);
}
}


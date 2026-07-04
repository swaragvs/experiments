import java.util.Scanner;
class Queue
{
private int max;
private int[] arr;
private int f;
public int r;
public Queue(int s)
{
max=s;
arr=new int[max];
f=0;
r=-1;
}
public void insert(int d)
{
r=(r+1)%max;
arr[r]=d;
}
public int remove()
{
int temp=arr[f];
f=(f+1)%max;
return temp;
}
public int peek()
{
return arr[f];
}
public boolean isEmpty()
{
if((r+1)%max==f)
return true;
else
 return false;
 }
public boolean isFull()
{
return ((r+2)%max==f);
}
public int size()
{
if(r>=f)
return r-f+1;
else
return ((max-f)+(r+1));
}
public void displayque()
{
System.out.println("Queue");
if(r>=f)
{
for(int i=f;i<max;i++)
{
System.out.println(arr[i]+" ");
}}

else{
for(int i=f;i<max;i++)
{System.out.println(arr[i]+" ");}
for(int i=0;i<r;i++)
{
System.out.println(arr[i]+" ");
}
}}}


class MyQueue
{
public static void main(String st[])
{
int k=1;
Scanner sc=new Scanner(System.in);
System.out.println("enter the size of the queue:");
int s=sc.nextInt();
Queue ob=new Queue(s);

while(k!=0)
{
System.out.println("MENU\n1.insert\n2.remove\n3peek\n4.size\n5.display\nenter your choice:");
int ch=sc.nextInt();
switch(ch)
{case 1:{

if(!ob.isFull())
{System.out.println("enter elements:");
int m=sc.nextInt();
ob.insert(m);
}
else
{
System.out.println("ERROR!!QUEUE OVERFLOW");
}}break;
case 2:{
if(!ob.isEmpty())
{
int deld=ob.remove();
System.out.println(deld);
}
else{
System.out.println("ERROR!!QUEUE underFLOW");
}}break;
case 3:
{
int topd=ob.peek();
System.out.println("top element is"+topd);
}break;
case 4:
{
int el=ob.size();
System.out.println("size is"+el);
}break;
case 5:
{
ob.displayque();
}break;
default:System.out.println("invalid choice");
}
}}}
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 



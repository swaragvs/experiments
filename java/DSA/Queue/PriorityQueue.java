import java.util.*;
class Link{
	public int data;
	public int pri;
	public Link next;
	public Link(int d,int p){
		data=d;
		pri=p;
		next=null;}
	}
class Queue{
	public Link first;
	public Queue(){
		first=null;}
	public void insert(int d,int p){
		Link nl=new Link(d,p);
		if(first==null || first.pri<p){
			nl.next=first;
			first=nl;}
		else{
			Link temp=first;
			while(temp.next!=null && temp.next.pri>=p){
					temp=temp.next;}
				nl.next=temp.next;
				temp.next=nl;}
			}
	public int remove(){
		if(isEmpty()){System.out.println("Priority queue underflow");
			return -1;}
		int del=first.data;
		Link max=first;
		Link prev=null;
		Link cur=first;
		while(cur.next!=null){
			if(cur.next.pri>max.pri){
			max=cur.next;
			prev=cur;}
			cur=cur.next;
			}
			
			if(prev!=null){
				del=max.data;
				prev.next=max.next;}
			else{
				del=first.data;
				first=first.next;}
			return del;}
	public int peek(){
		if(isEmpty()){System.out.println("Priority queue is Empty");}
		return first.data;
			}
	public boolean isEmpty(){return (first==null);}
	public void displayQueue(){
		if(isEmpty()){System.out.println("Priority queue is Empty");}
		Link temp=first;
		System.out.println("Priority Queue:");
		while(temp!=null){
			System.out.println("Data:"+temp.data+ " " +"Priority:"+temp.pri);
			temp=temp.next;}
			}
		}
class Priority{
	public static void main(String[] st){
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter the size of the queue");
	int max=sc.nextInt();
	Queue ob=new Queue();
	int x=0;
	int y;
	while(x==0){
	System.out.println("\nEnter your choice \n1-Insert\t2-Delete\t3-peek\t4-Display\t5-Exit");
	y=sc.nextInt();
	if(y==1){
			System.out.println("Enter element to insert:");
			int d=sc.nextInt();
			System.out.println("Enter its priority");
			int p=sc.nextInt();
			ob.insert(d,p);
			}
	else if(y==2){
			int del=ob.remove();
			if(del!=-1)
				System.out.println("{Deleted item:"+del);
			}
	else if(y==3){int top=ob.peek();
			System.out.println("item:"+top);
			}
	else if(y==4){ob.displayQueue();
			}
	else if(y==5){break;}
		}
		}
		}
		

import java.util.*;

class node{
int coeff;
int exp;
node next;
node prev;

public node(int coeff,int exp){
this.coeff=coeff;
this.exp=exp;
this.next=null;
this.prev=null;}
}

class polynomial{
node head;
node tail;
public polynomial(){
this.head=null;
this.tail=null;
}

public void insert(int coeff,int exp){
node newnode=new node(coeff,exp);
if(head==null){
head=tail=newnode;
return;
}

node curr=head;
while(curr!=null && curr.exp>exp){
curr=curr.next;
}
if(curr==null){
tail.next=newnode;
newnode.prev=tail;
tail=newnode;
}
else 
if(curr.exp==exp){
curr.coeff+=coeff;
}
else{
newnode.next=curr;
newnode.prev=curr.prev;
if(curr.prev!=null){
curr.prev.next=newnode;
}
else{
head=newnode;
}
curr.prev=newnode;
}
}

public static polynomial addPoly(polynomial poly1,polynomial poly2){
polynomial res=new polynomial();
node p1=poly1.head;
node p2=poly2.head;
while(p1!=null && p2!=null){
if(p1.exp==p2.exp){
res.insert(p1.coeff+p2.coeff,p1.exp);
p1=p1.next;
p2=p2.next;
}

else
if(p1.exp>p2.exp){
res.insert(p1.coeff,p1.exp);
p1=p1.next;
}
else{
res.insert(p2.coeff,p2.exp);
p2=p2.next;
}
}

while(p1!=null){
res.insert(p1.coeff,p1.exp);
p1=p1.next;
}
while(p2!=null){
res.insert(p2.coeff,p2.exp);
p2=p2.next;
}
return res;
}
public void display(){
node curr=head;
while(curr!=null){
if(curr.coeff!=0){
if(curr!=head && curr.coeff>0){
System.out.print("+");
}
System.out.print(curr.coeff+"x^"+curr.exp);
}
curr=curr.next;
}
System.out.println();
}


public static void main(String [] args){
Scanner sc=new Scanner(System.in);
polynomial poly1=new polynomial();
polynomial poly2=new polynomial();

System.out.println("ENTER NO OF TERMS OF POLY1");
int terms1;
terms1=sc.nextInt();
System.out.println("ENTER THE COFF AND EXP OF POLY1");
for(int i=0;i<terms1;i++)
{
int coeff=sc.nextInt();
int exp=sc.nextInt();
poly1.insert(coeff,exp);
}

System.out.println("ENTER NO OF TERMS OF POLY2");
int terms2;
terms2=sc.nextInt();
System.out.println("ENTER THE COFF AND EXP OF POLY2");
for(int i=0;i<terms2;i++)
{
int coeff=sc.nextInt();
int exp=sc.nextInt();
poly2.insert(coeff,exp);
}

System.out.println("POLY1:");
poly1.display();
System.out.println("POLY2:");
poly2.display();

polynomial res=addPoly(poly1,poly2);
System.out.println("SUM:");
res.display();
sc.close();
}
}



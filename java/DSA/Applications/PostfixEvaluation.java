import java.io.*;
import java.util.Scanner;
import java.lang.Math;
class postevv
 {
  private int max;
  private int[] arr;
  private int top;
  public postevv(int s)
   {
    max=s;
    arr=new int[max];
    top=-1;
   }
  public void push(int item)
   {
    top++;
    arr[top]=item;
   }
  public int pop()
   {
    int temp=arr[top];
    top--;
    return temp;
   }
  public int peek()
   {
    int temp=arr[top];
    return temp;
   }
  public boolean isEmpty()
   {
    if(top==-1)
     return true;
    else
     return false;
   }
  public boolean isFull()
   {
    if(top==(max-1))
     return true;
    else
     return false;
   }
 }
class postev
 {
  public static void main(String str[])
   {
    Scanner sc=new Scanner(System.in);
    String exp="";
    System.out.println("Enter the postfix expression:");
    exp=sc.nextLine();
    postevv st=new postevv(exp.length());
    for(int i=0;i<exp.length();i++)
     {
      char c=exp.charAt(i);
      if(c>='0'&&c<='9')
       {
        int temp=(int)(c-'0');
        st.push(temp);
       }
      else
       {
        int op1=st.pop();
        int op2=st.pop();
        switch(c)
         {
          case '+':st.push(op2+op1);
          	   break;
          case '-':st.push(op2-op1);
          	   break;
          case '*':st.push(op2*op1);
          	   break;
          case '/':st.push(op2/op1);
          	   break;
          case '^':double res=Math.pow(op2,op1);
          	   int resconv=(int)res;
          	   st.push(resconv);
          	   break;
          default:System.out.println("INVALID CHOICE!!!");
         }
       }
     }
    int result=st.peek();
    System.out.println("The result of the Postfix Evaluation is "+result);
   }
 }

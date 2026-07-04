import java.util.*;
import java.lang.Math;
class OurStack
{
	private int max;
	private char[]arr;
	private int top;
	public OurStack(int s)
	{
		max=s;
	        arr=new char[max];
		top=-1;
	}
	public boolean isFill()
	{
		if(top+1==max)
			return true;
		else
			return false;
	}
	public void Push(char item)
	{
		if (!isFill())
		{
			top++;
			arr[top]=item;
		}
		else
			System.out.println("Stack is Full");
	}
	public boolean isEmpty()
	{
		if(top==-1)
			return true;
		else
			return false;
	}
	public char Pop()
	{
		if (!isEmpty())
		{
			char temp=arr[top];
			top--;
			return temp;
		}
		else
			return 0;
	}
	public char Peek()
	{
		if (!isEmpty())
		{
			char temp=arr[top];
			return temp;
		}
		else
			return 0;	
	}
}
class OurStack2
{
	private int max;
	private String[]arr;
	private int top;
	public OurStack2(int s)
	{
		max=s;
	        arr=new String[max];
		top=-1;
	}
	public boolean isFill()
	{
		if(top+1==max)
			return true;
		else
			return false;
	}
	public void Push(String item)
	{
		if (!isFill())
		{
			top++;
			arr[top]=item;
		}
		else
			System.out.println("Stack is Full");
	}
	public boolean isEmpty()
	{
		if(top==-1)
			return true;
		else
			return false;
	}
	public String Pop()
	{
		if (!isEmpty())
		{
			String temp=arr[top];
			top--;
			return temp;
		}
		else
			return null;
	}
	public String Peek()
	{
		if (!isEmpty())
		{
			String temp=arr[top];
			return temp;
		}
		else
			return null;
	}
}
class Conversions
{
	public int preced(char c)
	{
		switch(c)
		{
			case '+':
			case '-':return 1;
			case '*':
			case '/':return 2;
			case '^':return 3;
			default: return 0;
		}
	}
	public String InfixtoPostfix(String inExp)
	{
		char c;
		OurStack st=new OurStack(inExp.length());
		String resultPost="";
		for(int i=0;i<inExp.length();++i)
		{
			c=inExp.charAt(i);
			if((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9'))
				resultPost=resultPost+c;
			else if(c=='(')
				st.Push(c);
			else if(c==')')
			{
				while(!st.isEmpty() && st.Peek()!='(')
					resultPost=resultPost+st.Pop();
					st.Pop();
			}
			else
			{
				while(!st.isEmpty() && !(st.Peek()!='(') && (preced(c)<=preced(st.Peek())))
					resultPost=resultPost+st.Pop();
					st.Push(c);
			}
		}
		while(!st.isEmpty())
			resultPost=resultPost+st.Pop();
		return resultPost;
	}
	public String InfixtoPrefix(String inExp)
	{
		String modiPre="";
		OurStack st=new OurStack(inExp.length());
		String resultpost="";
		for(int i=inExp.length()-1;i>=0;--i)
		{
			char c=inExp.charAt(i);
			if(preced(c)>0)
			{
				while(!st.isEmpty() && preced(st.Peek())>preced(c))
				{
					modiPre+=st.Pop();
				}
				st.Push(c);
			}
			else if(c=='(')
			{
				char x=st.Pop();
				while(x!=')')
				{
					modiPre+=x;
					x=st.Pop();
				}
			}
			else if(c==')')
			{
				st.Push(c);
			}
			else
			{
				modiPre+=c;
			}
		}
		while(!st.isEmpty())
		{
			modiPre+=st.Pop();
		}
		String resultPre="";
		for(int i=modiPre.length()-1;i>=0;i--)
		{
			resultPre+=modiPre.charAt(i);
		}
		return resultPre;
	}	
	public String PrefixtoPostfix(String preExp)	
	{
		String resultPost="";
		int l=preExp.length();
		OurStack2 st=new OurStack2(l);
		for(int i=l-1;i>=0;i--)
		{
			char c=preExp.charAt(i);
			if(c=='+' || c=='-' || c=='*' || c=='/' || c=='^')
			{
				String op1=st.Pop();
				String op2=st.Pop();
				resultPost=op1+op2+c;
				st.Push(resultPost);
			}
			else
				st.Push(c+"");
		}
		return resultPost;
	}	
}
class StringConv
{
	public static void main(String str[])
	{
		int choice,a;
		int c=1;
		String inExp="";
		String preExp="";
		Scanner ob1=new Scanner(System.in);
		Conversions ob=new Conversions();
		System.out.println("MENU:\n1.Infix to Postfix\n2.Infix to Prefix\n3.Prefix to Postfix\n4.Exit");
		while(c>0)
		{
			System.out.println("Enter your choice:");
			choice=ob1.nextInt();
			switch(choice)
			{
				case 1: System.out.println("Enter the expression:");
					inExp=ob1.next();
					System.out.println("Postfix expression is:\n"+ob.InfixtoPostfix(inExp));
					break;
				case 2: System.out.println("Enter the expression:");
					inExp=ob1.next();
					System.out.println("Prefix expression is:\n"+ob.InfixtoPrefix(inExp));
					break;
				case 3: System.out.println("Enter the expression:");
					preExp=ob1.next();
					System.out.println("Postfix expression is:\n"+ob.PrefixtoPostfix(preExp));
					break;
				case 4: c=0;
	   				System.out.println("Exiting...");
	   				break;
	   			default: System.out.println("Invalid choice");
			}
		}
	}
	
}





import java.io.*;
import java.util.Scanner;
class Calc
{
  public static void main(String s[])
    {
      Scanner sc=new Scanner(System.in);
      double num1,num2,result;
      char operator;
      
      System.out.println("Enter first number :");
      num1=sc.nextDouble();
      System.out.println("Enter an operator (+,-,*,/) :");
      operator=sc.next().charAt(0);
      System.out.println("Enter second number :");
      num2=sc.nextDouble();
      switch(operator)
      {
        case '+':
             result=num1+num2;
             System.out.println("Result :"+result);
             break;
        case '-':
             result=num1-num2;
             System.out.println("Result :"+result);
             break;
        case '*':
             result=num1*num2;
             System.out.println("Result :"+result);
             break;
        case '/':
             if(num2 != 0)
             {
               result=num1/num2;
               System.out.println("Result :"+result);
             }
             else
             {
               System.out.println("Cannot divide");
             }
             break;
        default:
               System.out.println("INVALID OPERATOR!!!!!");
      }
    }                     
}

/*
Enter first number :
5
Enter an operator (+,-,*,/) :
+
Enter second number :
7
Result :12.0


Enter first number :
5
Enter an operator (+,-,*,/) :
*
Enter second number :
7
Result :35.0*/


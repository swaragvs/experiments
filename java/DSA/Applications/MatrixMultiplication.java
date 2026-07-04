import java.io.*;
import java.util.Scanner;
class matmul
{
public static void main(String st[])
{
int arr[][]=new int[20][20];
int arr1[][]=new int[20][20];
int arr2[][]=new int[20][20];
int m,n,i,j,k,l,sum;
Scanner ob=new Scanner(System.in);
System.out.println("Enter size of row and column of 1st matrix");
m=ob.nextInt();
n=ob.nextInt();
System.out.println("Enter size of row and column of 2nd matrix");
k=ob.nextInt();
l=ob.nextInt();
if(n==k)
{
System.out.println("Enter elements of 1st array");
for(i=0;i<m;i++)
{
for(j=0;j<n;j++)
{
arr[i][j]=ob.nextInt();
}}

System.out.println("The 1st matrix is");
for(i=0;i<m;i++)
{
for(j=0;j<n;j++)
{
System.out.print(arr[i][j]+"  ");}
System.out.println(" ");}

System.out.println("Enter elements of 2nd array");
for(i=0;i<n;i++)
{
for(j=0;j<m;j++)
{
arr1[i][j]=ob.nextInt();
}}

System.out.println("The 2nd matrix is");
for(i=0;i<n;i++)
{
for(j=0;j<m;j++)
{
System.out.print(arr1[i][j]+"  ");}
System.out.println(" ");}

for(i=0;i<m;i++)
{
for(j=0;j<l;j++)
{
sum=0;
for(k=0;k<m;k++)
{
sum=sum+arr[i][k]*arr1[k][j];
arr2[i][j]=sum;}}}
System.out.println("The matrix is");
for(i=0;i<m;i++)
{
for(j=0;j<n;j++)
{
System.out.print(arr2[i][j]+"  ");}
System.out.println(" ");}
}
else
{
System.out.println("Not possible");
}}};



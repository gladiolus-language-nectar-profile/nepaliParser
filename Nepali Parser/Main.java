// Java program to demonstrate working of Comparator
// interface
import java.util.*;
import java.lang.*;
import java.io.*;
 
// A class to represent a student.
class Student
{
    int rollno;
    int value;
   // String name, address;
 
    // Constructor
    public Student(int value, int rollno)
    {
        this.rollno = rollno;
        this.value = value;
        
    }
 
    // Used to print student details in main()
    public String toString()
    {
        return this.rollno + " " + this.value;
    }
}
 
class Sortbyroll implements Comparator<Student>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Student a, Student b)
    {
        return a.rollno - b.rollno;
    }
}
 
// Driver class
class Main
{
    public void Main(){}
    public Student[] getsorted(Student[] arr1){
    	
 	Student[] arr = new Student[arr1.length];
      /*  System.out.println("Unsorted");
        for (int i=0; i<arr1.length; i++)
            System.out.println(arr1[i]);*/
 
        Arrays.sort(arr1, new Sortbyroll());
 
       // System.out.println("\nSorted by rollno");
      /*  for (int i=0; i<arr1.length; i++)
            System.out.println(arr1[i]);*/
        return arr1;
    }
    public void getsorted(){
    	 Student [] arr = {new Student(95,111),
                          new Student(3,131),
                          new Student(9,121)};
 
        System.out.println("Unsorted");
       /* for (int i=0; i<arr.length; i++)
            System.out.println(arr[i]);*/
 
        Arrays.sort(arr, new Sortbyroll());
 
        System.out.println("\nSorted by rollno");
       /* for (int i=0; i<arr.length; i++)
            System.out.println(arr[i]);*/
    }
    public static void main (String[] args) throws IOException
    {
 	Main m = new Main();
 	m.getsorted();
    }
}

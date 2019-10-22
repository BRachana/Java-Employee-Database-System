/**
 * Main.java
 * @author  Rachana Bandapalle
 * @version September 26, 2019
 */
import java.io.IOException;

public class Main {
/** Create a very simple online store with a shopping cart and items for sale.
 *  @param args  Command-line arguments are not used.
 */
	
    public static void main(String[] args) { 
    	System.out.println("Developer : Rachana Bandapalle \nProgram 4: An Employee Database \n");
    	try {
    		Payroll py = new Payroll();
			py.doMenu();
		}catch(IOException ioe) {
			System.out.println("Error: abort application!");
			ioe.printStackTrace(); System.exit(0);
		}
    	finally {
			System.exit(0);
		}
    }
}

/*----------------------------------------------------------------------
Developer : Rachana Bandapalle 
Program 4: An Employee Database 

Employee Datasase file is missing
Enter the details of Boss 
Enter Employee Full Name
Rachana Bandapalle
Enter Employee Login Name
Rachana
Enter Employee Salary
70000
Congratulations!!! New employee is successfully created
ID: 00000	Login Name: Rachana	Salary: 70000.0	Date: Fri Oct 04 16:16:48 EDT 2019	Full Name: Rachana Bandapalle
Congratulations!!! Boss is successfully created
--------------------------------------------------
Payroll Menu 
	1. Log In
	2. Enter employees
	3. List Employees
	4. Change employee data
	5. Terminate employees
	6. Pay employees 
	0. Exit system
--------------------------------------------------
Enter the option : 
1
Enter the Login Name : 
Rachana
You Logged in Successfully with Login Rachana
--------------------------------------------------
Payroll Menu 
	1. Log In
	2. Enter employees
	3. List Employees
	4. Change employee data
	5. Terminate employees
	6. Pay employees 
	0. Exit system
--------------------------------------------------
Enter the option : 
2
Enter Employee Full Name
Enter Employee Login Name
Sharddha
Enter Employee Salary
35000
Congratulations!!! New employee is successfully created
ID: 00001	Login Name: Sharddha	Salary: 35000.0	Date: Fri Oct 04 16:17:14 EDT 2019	Full Name: 
--------------------------------------------------
Payroll Menu 
	1. Log In
	2. Enter employees
	3. List Employees
	4. Change employee data
	5. Terminate employees
	6. Pay employees 
	0. Exit system
--------------------------------------------------
Enter the option : 
0

------------------------------------------------------------------*/

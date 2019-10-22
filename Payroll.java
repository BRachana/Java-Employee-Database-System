
/**
 * Payroll.java
 * @author  Rachana Bandapalle
 * @version September 26, 2019
 */

import java.io.*;
import java.util.*;

public class Payroll {
	// Data objects to be used by the Payroll.
	private ArrayList<Employee> empList;
	private int currentEmpID;
	private static String menu = "Payroll Menu \n\t" 
									+ "1. Log In\n\t" 
									+ "2. Enter employees\n\t"
									+ "3. List Employees\n\t" 
									+ "4. Change employee data\n\t" 
									+ "5. Terminate employees\n\t"
									+ "6. Pay employees \n\t" 
									+ "0. Exit system";
	private Scanner scanner = new Scanner(System.in);
	private String fileName = "EmployeeData.txt";
	private FileInputStream fis;
	private ObjectInputStream readDB;
	File f;

	// The controller for this application
	// Loads the application along with the employees in the database
	// Throw a file not found exception if you can't open it.
	public Payroll() throws FileNotFoundException, IOException {
		empList = new ArrayList();
		try {
			f = new File(fileName);
			Scanner sc = new Scanner(f); //--------Open the file and set up a pointer to it. 
			while(sc.hasNext()) { 
				int empId = sc.nextInt();
				String login = sc.next(); 
				double salary = sc.nextDouble(); 
				Date date = new Date(sc.next()); 
				String empName = sc.next(); 
				empList.add(new Employee(empId, login, salary, date, empName)); } sc.close();
			 
			//------------------------------------Close the file
		}
		catch (FileNotFoundException e) {
			f = new File("demo.txt"); 
			System.out.println("Employee Datasase file is missing");
			System.out.println("Enter the details of Boss ");
			
			newEmployee();
			System.out.println("Congratulations!!! Boss is successfully created");
		} finally {
			if (readDB != null) {
				readDB.close();
				fis.close();
			}
		}
	}

	
	// An employee can login only if he is there in the employee database
	private void doLogin() {
		System.out.println("Enter the Login Name : ");
		String loginName = scanner.next();
		boolean validLogin = false;
		for (Employee employee : empList) {
			if (loginName.equals(employee.getEmpLoginName())) {
				validLogin = true;
				System.out.println("You Logged in Successfully with Login " + loginName);
				break;
			}
		}
		if (!validLogin)
			System.out.println("Employee Not found in database\n");
	}

	// Add a new employees
	private void newEmployee() {
		if (currentEmpID == 0) {
			System.out.println("Enter Employee Full Name");
			String name = scanner.nextLine();
			System.out.println("Enter Employee Login Name");
			String login = scanner.next();
			System.out.println("Enter Employee Salary");
			double salary = scanner.nextDouble();

			Employee emp = new Employee(login, salary, name);
			empList.add(emp);
			System.out.println("Congratulations!!! New employee is successfully created");
			emp.toString();
			System.out.println(emp.toString());
		} else System.out.println("Sorry, you dont have access to add new employees.\n");
		
	}


	// stub menu functions
	private void listEmps() {
	}

	private void changeEmpData() {
	}

	private void terminateEmp() {
	}

	private void payEmp() {
	}
	

	// Display a menu
	public void doMenu() throws IOException {
		while (true) {
			System.out.println("--------------------------------------------------");
			System.out.println(menu);
			System.out.println("--------------------------------------------------");
			System.out.println("Enter the option : ");
			int option = scanner.nextInt();
			switch (option) {
			case 1:doLogin();			break;
			case 2:newEmployee();		break;
			case 3:listEmps();			break;
			case 4:changeEmpData();		break;
			case 5:terminateEmp();		break;
			case 6:payEmp();			break;
			case 0: 					break;
			}
			if (option == 0) break;
		}
	}
}

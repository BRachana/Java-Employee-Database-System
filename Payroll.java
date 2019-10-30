
/**
 * Payroll.java
 * @author  Rachana Bandapalle
 * @version September 26, 2019
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Payroll {
	//---------------------------------------------Data objects to be used by the Payroll
	private ArrayList<Employee> employees;		  //Collection of employees in the system
	private ArrayList<Employee> employeesLeft;	  //employees who have quit or been fired
	private int currentEmpID = -99999;
	private boolean bossCreated = false;
	private boolean LoggedIn = false;
	private Employee currentUser = null;
	private Scanner scanner;
	private static String menu = "Payroll Menu \n\t" +
								"1. Log In\n\t" +
								"2. Enter employees\n\t" +
								"3. List Employees\n\t" +
								"4. Change employee data\n\t" +
								"5. Terminate employees\n\t" +
								"6. Pay employees \n\t" +
								"0. Exit system";

	private String outputFileName;
	private PrintWriter writePayroll;
	private FileOutputStream fos;
	private ObjectOutputStream writeEmployeeDB;

	private String inputFileName;
	private FileInputStream fis;
	private ObjectInputStream readEmployeeDB;


	// The controller for this application
	// Loads the application along with the employees in the database
	// Throw a file not found exception if you can't open it.
	public Payroll() throws IOException {
		scanner = new Scanner(System.in);
		inputFileName = "EmployeeData.txt";
		outputFileName = "payroll.txt";
		employees = new ArrayList();
		employeesLeft = new ArrayList();
		try {
			writePayroll = new PrintWriter(outputFileName);
			fis = new FileInputStream(inputFileName);
			readEmployeeDB = new ObjectInputStream(fis);
			employees = (ArrayList<Employee>)readEmployeeDB.readObject();
			int employeeID=-999;
			for(Employee e:employees) 
				if(e.getEmployeeId()>employeeID)
					employeeID=e.getEmployeeId();
			Employee.nextId=employeeID+1;
		}
		catch(FileNotFoundException fnfe){
			if(new File(inputFileName).createNewFile()){
				System.out.println("Employee Datasase file is missing");
				System.out.println("Enter your details as a Boss ");
				bossCreated = true;
				newEmployee();
				bossCreated = false;
			}
		} 
		catch (ClassNotFoundException cnfe) {
			// TODO Auto-generated catch block
			cnfe.printStackTrace();
		}
		catch(EOFException eofe) {
			System.out.println("Employee Datasase file is missing");
			System.out.println("Enter your details as a Boss \n");
			bossCreated = true;
			newEmployee();
			bossCreated = false;

		}
		finally {
			if(readEmployeeDB!=null) {
				readEmployeeDB.close();
				fis.close();
			}
		}
	}
	
	private void error_message() {
		if(!LoggedIn) {
			System.out.println("Error: Please login before performing any operation\n");
		}else if(currentEmpID != 0) {
			System.out.println("Error: You are not authorized. \n");
		}
	}


	// An employee can login only if he is there in the employee database
	private void doLogin() {
		System.out.println("Enter the Login Name : ");
		String loginName = scanner.next();
		boolean validLogin = false;
		for (Employee employee : employees) {
			if (loginName.equals(employee.getEmpLoginName())) {
				validLogin 	  = true;
				currentUser   = employee;
				currentEmpID  = employee.getEmployeeId();
				LoggedIn	  = true;
				System.out.println("You Logged in Successfully with Login " + loginName);
				break;
			}
		}
		if (!validLogin)
			System.out.println("Employee Not found in database\n");
	}

	// Add a new employees
	private void newEmployee() {
		if (currentEmpID == 0 || bossCreated) {
			boolean loginNameExists = false;
			System.out.println("Enter Employee Full Name");
			String name = scanner.nextLine();
			String login;
			do {
				loginNameExists = false;
				System.out.println("Enter Employee Login Name");
				login = scanner.next();
				for(Employee e : employees) {
					if(login.equalsIgnoreCase(e.getEmpLoginName())) {
						loginNameExists = true;
						System.out.println("Login name already exists in the database, please use another one");
						break;
					}
				}
			}while(loginNameExists);

			System.out.println("Enter Employee Salary");
			double salary = scanner.nextDouble();
			Employee emp;
			while(true) {
				System.out.println("Is the employee Hourly or Salaried ?");
				System.out.println("1. Hourly");
				System.out.println("2. Salaried");
				System.out.println("Enter 1 for Hourly or 2 for Salaried : ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				if(choice==1) {
					emp = new Hourly(login, salary, name); 	 break;
				}else if (choice==2) {
					emp = new Salaried(login, salary, name); break;
				}else System.out.println("Wrong Input. Try again");
			}
			employees.add(emp);
			System.out.println("Congratulations!!! employee " + name +" is successfully created");
		} else error_message();
	}


	private void listEmployees() {
		if(LoggedIn) {
			System.out.println("\nThe employees details are : ");
			System.out.println("------------------------------------------------------------------------------------------------------------");
			System.out.println(String.format("%-15s","Employee ID") 
					+ String.format("%-20s", " Login Name")
					+ String.format("%-20s", " Salary") 
					+ String.format("%-35s", " Created Date")
					+ String.format("%-20s", " Employee Name"));
			System.out.println("------------------------------------------------------------------------------------------------------------");
			if(currentEmpID==0)  //----------------------for boss, show all the employees
				for(Employee e : employees)  System.out.println(e);
			else  
				System.out.println(currentUser);//------else only show personal details
			System.out.println("------------------------------------------------------------------------------------------------------------");
		}else error_message();
	}

	
	private void changeEmpData() {
		if(LoggedIn && currentEmpID==0) {
			System.out.println("Enter the employee ID whose data needs to be changed: ");
			int employeeID = scanner.nextInt();
			boolean empFound = false;
			for(int i=0;i<employees.size();i++)
				if(employees.get(i).getEmployeeId()==employeeID) {
					empFound=true;
					System.out.println("Select an option to update the data");
					System.out.println("1. Employee Name");
					System.out.println("2. Salary");
					System.out.println("Enter 1 to update name and 2 for salary: ");
					int choice=scanner.nextInt();
					scanner.nextLine();
					if(choice==1) {
						System.out.println("Enter the new name : ");
						String name=scanner.nextLine();
						employees.get(i).setName(name);
					}else if(choice==2) {
						System.out.println("\nEnter the new salary : ");
						double salary=scanner.nextDouble();
						employees.get(i).setSalary(salary);
					}else 
						System.out.println("Wrong Input. Try again\n");
				}
			if(!empFound)
				System.out.println("There is no employee with this ID \n");
		}
		else error_message();
	}

	private void terminateEmp() {
		if(LoggedIn) {
			if(currentEmpID==0) {
				System.out.println("Enter the employee ID to fire : ");
				int employeeID=scanner.nextInt();
				if(employeeID==0)
					System.out.println("Boss can not leave the organization");
				else{
					boolean found = false;
					for(int i=0;i<employees.size();i++)
						if(employees.get(i).getEmployeeId() == employeeID) {
							System.out.println("Employee "+employeeID+" is fired\n");
							employeesLeft.add(employees.get(i));
							employees.remove(i);
							found=true;
							break;
						}
					if(!found)
						System.out.println("There is no employee with ID : "+employeeID+"\n");
				}
			}else {
				for(int i=0;i<employees.size();i++) {
					if(employees.get(i).getEmployeeId()==currentEmpID) {
						System.out.println("You have successfully quit the organization\n");
						employeesLeft.add(employees.get(i));
						employees.remove(i);
						currentEmpID = -9999;
						currentUser = null;
						break;
					}
				}
			}
		}else error_message();
	}

	private String writePay(String payData) {
		writePayroll.println(payData);
		return payData;
	}
	
	private void payEmployee() {
		if(LoggedIn && currentEmpID==0) {
			ArrayList pay = new ArrayList();
			for(Employee e : employees)
				pay.add(e.getPay());
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date=new Date();
			System.out.println(writePay("\nPayroll Report : " + format.format(date)));
			System.out.println("-------------------------------------------------");
			System.out.println(writePay(String.format("%-15s", "Employee ID")
					+String.format("%-20s", "Employee Name")
					+String.format("%-20s", "Employee Pay")));
			System.out.println("-------------------------------------------------");
			for(int i=0;i<employees.size();i++)
				System.out.println(writePay(String.format("%-15s", 
						 String.format("%05d", employees.get(i).getEmployeeId()))
						+String.format("%-20s", employees.get(i).getEmpName())
						+String.format("%-20s", String.format("%.2f",pay.get(i)))));
			System.out.println("-------------------------------------------------");
		}else error_message();
	}

	//Log out of system and close all the file connections
	private void quit() throws IOException{
		try {
			fos = new FileOutputStream(inputFileName);
			writeEmployeeDB  = new ObjectOutputStream(fos);
			writeEmployeeDB.writeObject(employees);
			System.out.println("Successfully logged out of the application.\n");
			if(employeesLeft.size()>0){
				System.out.println("People who have quit or fired in this session are as below");
				System.out.println("-----------------------------------");
				System.out.println(String.format("%-15s", "Employee ID")+String.format("%-20s", "Employee Name"));
				System.out.println("-----------------------------------");
				for(Employee e : employeesLeft)
					System.out.println(String.format("%-15s", String.format("%05d", e.getEmployeeId()))+String.format("%-20s", e.getEmpName()));
				System.out.println("-----------------------------------");
			}
		} 
		finally {
			try {
				writeEmployeeDB.close();
				writePayroll.close();
				scanner.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// Display a menu
	public void doMenu() throws IOException {
		while (true) {
			System.out.println("------------------------------------------------------");
			System.out.println(menu);
			System.out.println("------------------------------------------------------");
			System.out.println("Enter the option : ");
			int option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
			case 1:doLogin();			break;
			case 2:newEmployee();		break;
			case 3:listEmployees();		break;
			case 4:changeEmpData();		break;
			case 5:terminateEmp();		break;
			case 6:payEmployee();		break;
			case 0:quit();				break;
			default: System.out.println("Error: Wrong input. Try once again");
			}
			if(option==0) break;
		}
	}
}

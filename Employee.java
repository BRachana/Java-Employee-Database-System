/**
 * Employee.java
 * @author  Rachana Bandapalle
 * @version September 26, 2019
 */

import java.io.Serializable;
import java.util.Date;

public abstract class Employee implements Serializable{
	protected String login;			//--------------------------------Name used to log in
	protected double salary;		//-----------------------------Salary of the employee
	protected String name;  //--------------------------Full name of the employee
	protected Date createdDate;		//---------------------------Joining date of employee
	protected final int empID;		//-----------------------------Five Digit Employee ID
	protected static int nextId = 0;//-------------------------No two ID's should be same

	//----------------------------------------- Create a new employee by assigning new ID
	public Employee(String login, double salary, String name) {
		empID = nextId++; 		 //This will increment employee ID for every new employee
		this.name 	= name;
		this.login 	= login;
		this.salary = salary;
		createdDate = new Date();
	}
	
	//--------------------------Create employee object from Employee Database object file
	public Employee(int ID, String loginName, double salary, Date date, String name) {
		this.empID 	= ID;
		this.name  	= name;
		this.login 	= loginName;
		this.salary = salary;
		createdDate = new Date();
	}

	public void   setSalary(double salary){ this.salary = salary; }
	public void   setName(String name){ this.name = name; }
	public int 	  getEmployeeId() { return empID; } 
	public String getEmpLoginName() { return login; } 
	public String getEmpName() { return name; } 
	public String toString(){
		return String.format("%-16s", String.format("%05d", empID)) + 
				String.format("%-20s", login) + 
				String.format("%-20s", String.format("%.2f",salary)) + 
				String.format("%-35s", createdDate) + 
				String.format("%-20s", name);
	}
	public abstract double getPay();
}

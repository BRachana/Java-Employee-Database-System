/**
 * Employee.java
 * @author  Rachana Bandapalle
 * @version September 26, 2019
 */
import java.util.*;
public class Employee {
	private String login;
	private double salary;
	private String name;
	private Date createdDate;
	private final int empID;
	protected static int nextId = 0;

	// Create a new employee by assigning new ID
	public Employee(String loginName, double salary, String name) {
		empID = nextId++; //This will increment employee ID for every new employee
		createdDate = new Date();
		this.login = loginName;
		this.salary = salary;
		this.name = name;
	}
	
	// creating employee object from Employee Database with already available details
	public Employee(int ID, String loginName, double salary, Date date, String name) {
		this.empID = ID;
		this.createdDate = new Date();
		this.login = loginName;
		this.salary = salary;
		this.name = name;
	}

	public void setSalary(double salary){ this.salary = salary; }
	public String getEmpLoginName() { return login; } 
	public String toString(){
		return "ID: "+String.format("%05d", empID)+"\tLogin Name: "
				+login+"\tSalary: "+salary+"\tDate: "+createdDate+"\tFull Name: "+name;
	}
}

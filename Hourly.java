/**
 * @author racha
 *
 */
import java.util.Scanner;

public class Hourly extends Employee {
	public Hourly(String loginName, double salary, String employeeName) {
		super(loginName, salary, employeeName);
	}

	public double getPay() {
		Scanner Sc = new Scanner(System.in);
		System.out.println("Enter the number of hours of Employee with ID - "+ empID +" : ");
		int hours = Sc.nextInt();
		return (hours*salary);
	}
}

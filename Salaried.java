
public class Salaried extends Employee {
	public Salaried(String loginName, double salary, String employeeName) {
		super(loginName, salary, employeeName); }

	public double getPay() { return salary/24; }
}

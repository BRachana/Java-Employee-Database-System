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
    }
}
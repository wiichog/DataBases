/**
 * 
 */
package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Perry
 *
 */
public class mainTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		
		Twitter tw = new Twitter("Ale_Crts");
		
		tw.getAnalisis("Ale_Crts");
		
		Reporteria reporte = new Reporteria("test");
		
		List<String> test = new ArrayList<String>();
		test.add("Ale_Crts");
		test.add("JuanLuisGarciaZ");
		
		reporte.makeTwitterReports(test);
		
		int[] tint = {1,2,3,4,5,6,7,8,9,0};
		String[] tnam = {"Santiago", "Wicho", "Fredd", "Perry", "Santiago", "Wicho", "Fredd", "Perry", "Santiago", "Wicho"};
		
		reporte.bars(tint, tnam, "Test");
		
		reporte.display("Testinggg", tnam, "Estos son los m�s papus:");
	}

}

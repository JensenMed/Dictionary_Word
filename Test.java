import java.io.FileNotFoundException;

public class Test {

	public static void main(String[] args) {
		
		try {
			Spell2 test = new Spell2("C:\\Users\\Jensen Medeiros\\OneDrive\\Desktop\\CS2210\\Assignment_1\\src\\Test.txt","C:\\Users\\Jensen Medeiros\\OneDrive\\Desktop\\CS2210\\Assignment_1\\src\\Test2.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

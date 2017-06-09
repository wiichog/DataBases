package BackEnd;

public class ProbandoCosasDelBackend {

	public static void main(String args[]) {
		String password;
		String userName;
		String[] test;
		CommandsSQL impl = new implementCommands();

		//CONNECT
		userName = "postgres"; 		
		password = "";
		impl.Connect(userName, password, "CRM");
		test = impl.SELECT("id", "cliente");
		for(int i = 0; i < test.length; i++){
			System.out.println(test[i].toString());
		}
		/*test = impl.SELECT("nombre", "cliente");
		for(int i = 0; i < test.length; i++){
			System.out.println(test[i].toString());
		}*/

	}
}


package pack;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("initialisation..");

		Application myApp = new Application();
		// CWindow myWindow = new CWindow();

		// System.out.println(myApp.toString());
		// System.out.println("----------------");
		for (int i = 0; true; i++) {
			// System.out.println(myApp.toString());
			myApp.progress();
			myApp.updateWindow();
		}
		// System.out.println(myApp.toString());
		//System.out.println("fin");
	}

}


public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("initialisation..");
		System.setProperty("sun.java2d.opengl", "True");

		Application myApp = new Application();

		while (true) {
			myApp.progress();
			myApp.updateWindow();

		}
	}

}

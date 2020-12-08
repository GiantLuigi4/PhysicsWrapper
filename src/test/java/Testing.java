import org.jbox2d.testbed.framework.TestbedMain;

import java.io.IOException;
import java.net.URL;

public class Testing {
	public static void main(String[] args) throws IOException {
		ClassLoader loader = new ClassLoader(new URL[0],Testing.class.getClassLoader());
		try {
			loader.loadClass("Testing").getMethod("run",String[].class).invoke(null, (Object) args);
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
	}
	
	public static void run(String[] args) {
		TestbedMain.main(args);
	}
}

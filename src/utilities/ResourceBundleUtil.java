package utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleUtil {

	private static ResourceBundle rb = ResourceBundle.getBundle("Utilities.default");
	private static ResourceBundle rb1;

	private static void getUserConfig() {
		try {
			File file = new File("./");
			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);
			rb1 = ResourceBundle.getBundle("MAF", Locale.getDefault(), loader);
		} catch (MissingResourceException e) {
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	public static String getProperty(String prop) {
		try {
			if(rb1 == null) {
				getUserConfig();
			}
			return rb1.getString(prop);
		} catch (Exception e) {
			return rb.getString(prop);
		}
	}


}

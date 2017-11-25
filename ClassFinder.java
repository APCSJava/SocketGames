import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassFinder {

	/**
	 * Looks inside the current working directory and collects all file names having
	 * the extension .class
	 * 
	 * @return an array of files
	 */
	private static File[] findClassFilesInWorkingDirectory() {
		File directory = new File(".");
		File[] files = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("class");
			}
		});
		return files;
	}

	/**
	 * Collects all files in the current working directory which implement the
	 * Servable interface
	 * 
	 * @return a list holding all classes that implement Servable
	 * @throws ClassNotFoundException
	 */
	public static List<Class<Servable>> findServableClasses()
			throws ClassNotFoundException {
		List<Class<Servable>> servableClasses = new ArrayList<Class<Servable>>();
		for (File f : findClassFilesInWorkingDirectory()) {
			String nameWithExtension = f.getName();
			int idx = nameWithExtension.lastIndexOf(".class");
			Class classObj = Class
					.forName(nameWithExtension.substring(0, idx));
			Class[] interfaces = classObj.getInterfaces();
			if (interfaces.length > 0) {
				for (Class c : interfaces) {
					if (Servable.class.isAssignableFrom(c)) {
						servableClasses.add(classObj);
						break;
					}
				}
			}
		}
		return servableClasses;
	}

	private static String formatAuthorString(String[] authors) {
		String authorString = "";
		for (int i = 0; i<authors.length-1; i++) {
			authorString+=authors[i];
			if (i<authors.length-2) authorString+=", ";
			else authorString+=" and "; // no Oxford commas
		}
		authorString+=authors[authors.length-1];
		return authorString;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		List<Class<Servable>> classes = findServableClasses();
		for (Class<Servable> c : classes) {
			Annotation[] annotations = c.getAnnotations();
			for (Annotation a : annotations) {
				if (a instanceof GameInfo) {
					GameInfo info = (GameInfo) a;
					String authors=formatAuthorString(info.authors());
					System.out.println(info.gameTitle()+". Written by "+authors+ ".  Version: "+info.version());
				}
			}
			File f = new File(c.getName()+".class");
			Date d = new Date(f.lastModified());
			System.out.println("Last modified :"+d);
		}

	}
}

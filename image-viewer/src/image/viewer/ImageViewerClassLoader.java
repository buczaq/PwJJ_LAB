package image.viewer;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
public class ImageViewerClassLoader extends ClassLoader {
 private Hashtable classes = new Hashtable();
 public ImageViewerClassLoader() { 
 }
 /**
 * This sample function for reading class implementations reads
 * them from the local file system
 */
 private byte getClassImplFromDataBase(String className)[] {
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; Fetching the implementation of "+className);
 byte result[];
 try {
 FileInputStream fi = new FileInputStream("plugins/" + className + ".class");
 result = new byte[fi.available()];
 fi.read(result);
 return result;
 } catch (Exception e) {
 /*
 * If we caught an exception, either the class wasnt found or it
 * was unreadable by our process.
 */
 return null;
 }
 }
 /**
 * This is a simple version for external clients since they
 * will always want the class resolved before it is returned
 * to them.
 */
 public Class loadClass(String className) throws ClassNotFoundException {
 return (loadClass(className, true));
 }
 /**
 * This is the required version of loadClass which is called
 * both from loadClass above and from the internal function
 * FindClassFromClass.
 */
 public synchronized Class loadClass(String className, boolean resolveIt)
 throws ClassNotFoundException {
 Class result;
 byte classData[];
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; Load class : "+className);
 /* Check our local cache of classes */
 result = (Class)classes.get(className);
 if (result != null) {
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; returning cached result."); 
 return result;
 }
 /* Check with the primordial class loader */
 try {
 result = super.findSystemClass(className);
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; returning system class (in CLASSPATH).");
 return result;
 } catch (ClassNotFoundException e) {
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; Not a system class.");
 }
 /* Try to load it from our repository */
 classData = getClassImplFromDataBase(className);
 if (classData == null) {
 throw new ClassNotFoundException();
 }
 /* Define it (parse the class file) */
 result = defineClass(classData, 0, classData.length);
 if (result == null) {
 throw new ClassFormatError();
 }
 if (resolveIt) {
 resolveClass(result);
 }
 classes.put(className, result);
 System.out.println(" &gt;&gt;&gt;&gt;&gt;&gt; Returning newly loaded class " + className);
 return result;
 }
 
 	public void invokeClassMethod(String classBinName, String methodName, BufferedImage img, File f){
		
		try {
			// Load the target class using its binary name
	        Class loadedMyClass = loadClass(classBinName);
	        
	        System.out.println("Loaded class name: " + loadedMyClass.getName());
	        
	        // Create a new instance from the loaded class
	        Constructor constructor = loadedMyClass.getConstructor();
	        Object myClassObject = constructor.newInstance();
	        
	        // Getting the target method from the loaded class and invoke it using its name
	        Method method = loadedMyClass.getMethod(methodName, BufferedImage.class, File.class);
	        System.out.println("Invoked method name: " + method.getName());
	        method.invoke(myClassObject, img, f);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
} 
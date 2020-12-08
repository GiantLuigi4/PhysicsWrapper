import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.Enumeration;

public class ClassLoader extends URLClassLoader {
	public ClassLoader(URL[] urls, java.lang.ClassLoader parent) throws IOException {
		super(urls, parent);
		
		Enumeration<URL> urlEnumeration = parent.getResources("");
		while (urlEnumeration.hasMoreElements()) this.addURL(urlEnumeration.nextElement());
	}
	
	public ClassLoader(URL[] urls) {
		super(urls);
	}
	
	public ClassLoader(URL[] urls, java.lang.ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		super.loadClass(name);
		synchronized (getClassLoadingLock(name)) {
			// First, check if the class has already been loaded
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				long t0 = System.nanoTime();
//				try {
//					c = findBootstrapClass(name);
//					if (parent != null) {
//						c = parent.loadClass(name, false);
//					} else {
//						c = findBootstrapClassOrNull(name);
//					}
//				} catch (ClassNotFoundException e) {
					// ClassNotFoundException thrown if class not found
					// from the non-null parent class loader
//				}
//
				if (c == null) {
					// If still not found, then invoke findClass in order
					// to find the class.
					long t1 = System.nanoTime();
					try {
						c = findClass(name);
						
						// this is the defining class loader; record the stats
						sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
						sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
						sun.misc.PerfCounter.getFindClasses().increment();
					} catch (Throwable ignored) {
					}
				}
				
				if (c == null) {
					c = getParent().loadClass(name);
				}
			}
			if (false) {
				resolveClass(c);
			}
			return c;
		}
	}
	
	@Override
	public URL getResource(String name) {
		return getParent().getResource(name);
	}
	
	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return getParent().getResources(name);
	}
	
	@Override
	public URL findResource(String name) {
		return getResource(name);
	}
	
	@Override
	public Enumeration<URL> findResources(String name) throws IOException {
		return getResources(name);
	}
	
	@Override
	protected String findLibrary(String libname) {
		System.out.println(libname);
		String out = super.findLibrary(libname);
		System.out.println(out);
		return out;
	}
	
	@Override
	public InputStream getResourceAsStream(String name) {
		if (name.endsWith(".dll"))
			System.out.println("hi");
		return super.getResourceAsStream(name);
	}
}

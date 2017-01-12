package com.ck.util;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 类加载帮助类
 *
 * @author: xieweili
 * @since: 2016年12月31日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class ClassLoaderUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtils.class);

    // class path
    private static String classPath = "";

    private static final String OS_NAME = "os.name";

    private static final String WINDOWS = "indow";

    // loader
    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    //
    // get class path
    //
    static {

        if (loader == null) {
            LOGGER.info("using system class loader!");
            loader = ClassLoader.getSystemClassLoader();
        }

        try {

            java.net.URL url = loader.getResource("");
            // get class path
            classPath = url.getPath();
            classPath = URLDecoder.decode(classPath, "utf-8");

            // 如果是jar包内的，则返回当前路径
            if (classPath.contains(".jar!")) {
                LOGGER.warn("using config file inline jar!" + classPath);
                classPath = System.getProperty("user.dir");

                //
                addCurrentWorkingDir2Classpath(classPath);
            }

        } catch (Exception e) {
            LOGGER.warn("cannot get classpath using getResource(), now using user.dir");
            classPath = System.getProperty("user.dir");

            //
            addCurrentWorkingDir2Classpath(classPath);
        }

        LOGGER.info("classpath: {}", classPath);
    }

    /**
     * only support 1.7 or higher
     * http://stackoverflow.com/questions/252893/how-do
     * -you-change-the-classpath-within-java
     */
    private static void addCurrentWorkingDir2Classpath(String path2Added) {

        // Add the conf dir to the classpath
        // Chain the current thread classloader
        URLClassLoader urlClassLoader;
        try {
            urlClassLoader = new URLClassLoader(new URL[] { new File(path2Added).toURI().toURL() }, loader);
            // Replace the thread classloader - assumes
            // you have permissions to do so
            Thread.currentThread().setContextClassLoader(urlClassLoader);
        } catch (Exception e) {
            LOGGER.warn(e.toString());
        }
    }

    public static String getClassPath() {
        return classPath;
    }

    public static ClassLoader getLoader() {
        return loader;
    }

    public static String getFilePath(String fileName) {
        String path = getClassPath().concat(fileName);
        String osAppropriatePath = System.getProperty(OS_NAME).contains(WINDOWS) ? path.substring(1) : path;

        return osAppropriatePath;
    }

    public static Class<?> forNameWithThreadContextClassLoader(String name) throws ClassNotFoundException {
        return forName(name, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> forNameWithCallerClassLoader(String name, Class<?> caller) throws ClassNotFoundException {
        return forName(name, caller.getClassLoader());
    }

    public static ClassLoader getCallerClassLoader(Class<?> caller) {
        return caller.getClassLoader();
    }

    /**
     * get class loader
     * 
     * @param cls
     * @return class loader
     */
    public static ClassLoader getClassLoader(Class<?> cls) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system
            // class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = cls.getClassLoader();
        }
        return cl;
    }

    /**
     * Return the default ClassLoader to use: typically the thread context
     * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
     * class will be used as fallback.
     * <p>
     * Call this method if you intend to use the thread context ClassLoader in a
     * scenario where you absolutely need a non-null ClassLoader reference: for
     * example, for class path resource loading (but not necessarily for
     * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
     * reference as well).
     * 
     * @return the default ClassLoader (never <code>null</code>)
     * @see java.lang.Thread#getContextClassLoader()
     */
    public static ClassLoader getClassLoader() {
        return getClassLoader(ClassLoaderUtils.class);
    }

    /**
     * Same as <code>Class.forName()</code>, except that it works for primitive
     * types.
     */
    public static Class<?> forName(String name) throws ClassNotFoundException {
        return forName(name, getClassLoader());
    }

    /**
     * Replacement for <code>Class.forName()</code> that also returns Class
     * instances for primitives (like "int") and array class names (like
     * "String[]").
     * 
     * @param name
     *            the name of the Class
     * @param classLoader
     *            the class loader to use (may be <code>null</code>, which
     *            indicates the default class loader)
     * @return Class instance for the supplied name
     * @throws ClassNotFoundException
     *             if the class was not found
     * @throws LinkageError
     *             if the class file could not be loaded
     * @see Class#forName(String, boolean, ClassLoader)
     */
    public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {

        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz != null) {
            return clazz;
        }

        // "java.lang.String[]" style arrays
        if (name.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[Ljava.lang.String;" style arrays
        int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
        if (internalArrayMarker != -1 && name.endsWith(";")) {
            String elementClassName = null;
            if (internalArrayMarker == 0) {
                elementClassName = name.substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - 1);
            } else if (name.startsWith("[")) {
                elementClassName = name.substring(1);
            }
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = getClassLoader();
        }
        return classLoaderToUse.loadClass(name);
    }

    /**
     * Resolve the given class name as primitive class, if appropriate,
     * according to the JVM's naming rules for primitive classes.
     * <p>
     * Also supports the JVM's internal class names for primitive arrays. Does
     * <i>not</i> support the "[]" suffix notation for primitive arrays; this is
     * only supported by {@link #forName}.
     * 
     * @param name
     *            the name of the potentially primitive class
     * @return the primitive class, or <code>null</code> if the name does not
     *         denote a primitive class or primitive array class
     */
    public static Class<?> resolvePrimitiveClassName(String name) {
        Class<?> result = null;
        // Most class names will be quite long, considering that they
        // SHOULD sit in a package, so a length check is worthwhile.
        if (name != null && name.length() <= 8) {
            // Could be a primitive - likely.
            result = (Class<?>) primitiveTypeNameMap.get(name);
        }
        return result;
    }

    /** Suffix for array class names: "[]" */
    public static final String ARRAY_SUFFIX = "[]";
    /** Prefix for internal array class names: "[L" */
    private static final String INTERNAL_ARRAY_PREFIX = "[L";

    /**
     * Map with primitive type name as key and corresponding primitive type as
     * value, for example: "int" -> "int.class".
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(16);

    /**
     * Map with primitive wrapper type as key and corresponding primitive type
     * as value, for example: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);

        Set<Class<?>> primitiveTypeNames = new HashSet<Class<?>>(16);
        primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
        primitiveTypeNames.addAll(Arrays.asList(new Class<?>[] { boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class }));
        for (Iterator<Class<?>> it = primitiveTypeNames.iterator(); it.hasNext();) {
            Class<?> primitiveClass = (Class<?>) it.next();
            primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
        }
    }

    public static String toShortString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName() + "@" + System.identityHashCode(obj);

    }
}

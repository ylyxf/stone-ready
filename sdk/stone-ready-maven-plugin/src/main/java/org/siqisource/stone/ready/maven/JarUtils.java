package org.siqisource.stone.ready.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {

	private static final String CLASS_SUFFIX = ".class";

	/**
	 * 
	 * 递归获取jar所有class文件的名字
	 * 
	 * @param jarFile
	 * 
	 * @param packageName 包名
	 * 
	 * @param flag        是否需要迭代遍历
	 * 
	 * @return List
	 * @throws IOException
	 * 
	 */

	public static List<String> getAllClassNames(File file) throws IOException {
		JarFile jarFile = new JarFile(file);
		List<String> result = new ArrayList<String>();

		Enumeration<JarEntry> entries = jarFile.entries();

		while (entries.hasMoreElements()) {

			JarEntry jarEntry = entries.nextElement();

			String name = jarEntry.getName();

			// 判断是不是class文件

			if (name.endsWith(CLASS_SUFFIX)) {

				name = name.replace(CLASS_SUFFIX, "").replace("/", ".");
				result.add(name);
			}

		}
		jarFile.close();
		return result;

	}

	public static void repackage(File file, List<String> classNames) throws IOException {
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();

		while (entries.hasMoreElements()) {

			JarEntry jarEntry = entries.nextElement();

			String name = jarEntry.getName();

			name = name.replace(CLASS_SUFFIX, "").replace("/", ".");
			if (contains(name, classNames)) {
				System.out.println("delete::+++++++++++++++++>>>>>>>>>" + name);
			}
		}

	}

	private static boolean contains(String classNameInJar, List<String> classNames) {
		for (String className : classNames) {
			if (classNameInJar.equals(className) || classNameInJar.startsWith(className + "$")) {
				return true;
			}
		}

		return false;
	}

}
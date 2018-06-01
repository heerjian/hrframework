package com.hframework.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件处理常用类。
 * 
 * @author heerjian
 * @date 2016年11月21日
 */
public class FileUtils {

	/**
	 * 从文件路径中获取文件名。
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		int p = StringUtils.lastIndexOfAny(filePath, "/", "\\");
		if (p == -1)
			return filePath;
		return filePath.substring(p + 1);
	}

	/**
	 * 获取文件扩展名。
	 * 
	 * @param file
	 *            文件。
	 * @return 如果文件为null，或没有扩展名时返回null。
	 */
	public static String getExtension(File file) {
		if (file == null)
			return "";
		return getExtension(file.getName());
	}

	/**
	 * 获取文件名中的扩展名。可能有多级扩展名，如example.tar.gz的扩展名为tar.gz。
	 * 
	 * @param fileName
	 *            文件名。
	 * @return 如果文件名为null，或没有扩展名时返回空字符串。
	 */
	public static String getExtension(String fileName) {
		int p = StringUtils.indexOf(fileName, '.');
		if (p == -1)
			return "";
		return fileName.substring(p + 1);
	}

	/**
	 * 获取文件名中的最后一级扩展名。
	 * 
	 * @param file
	 *            文件。
	 * @return 如果文件为null，或没有扩展名时返回null。
	 */
	public static String getLastExtension(File file) {
		if (file == null)
			return "";
		return getLastExtension(file.getName());
	}

	/**
	 * 获取文件名中的最后一级扩展名。
	 * 
	 * @param fileName
	 *            文件名。
	 * @return 如果文件名为null，或没有扩展名时返回空字符串。
	 */
	public static String getLastExtension(String fileName) {
		int p = StringUtils.lastIndexOf(fileName, '.');
		if (p == -1)
			return "";
		return fileName.substring(p + 1);
	}

	/**
	 * 如果文件的扩展名是指定的扩展名中的一个，则返回true；<br>
	 * 如果扩展名列表为null则当文件名没有扩展名时返回true；<br>
	 * 其他 情况返回false。
	 * 
	 * @param file
	 *            文件。
	 * @param extensions
	 *            扩展名，不带点。
	 * @return
	 */
	public static boolean isExtension(File file, String... extensions) {
		if (file == null)
			return StringUtils.isAnyEmpty(extensions);

		return isExtension(file.getName(), extensions);
	}

	/**
	 * 如果文件名的扩展名是指定的扩展名中的一个，则返回true；<br>
	 * 如果扩展名列表为null则当文件名没有扩展名时返回true；<br>
	 * 其他 情况返回false。
	 * 
	 * @param fileName
	 *            文件名。
	 * @param extensions
	 *            扩展名，不带点。
	 * @return
	 */
	public static boolean isExtension(String fileName, String... extensions) {
		if (extensions == null) {
			return StringUtils.indexOf(fileName, '.') == -1;
		}
		fileName = fileName != null ? fileName.toLowerCase() : null;
		boolean noExt = StringUtils.indexOf(fileName, '.') == -1;
		for (String extension : extensions) {
			if (StringUtils.isEmpty(extension)) {
				if (noExt)
					return true;
			} else if (StringUtils.endsWith(fileName, "." + extension.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static void copyFile(String src, String target) {
		File file = new File(src);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File fp = new File(file.getParent());
		if (!fp.exists()) {
			fp.mkdirs();
		}
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(target);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			byte[] datas = new byte[1024];
			int len = 0;
			while ((len = bis.read(datas)) != -1) {
				bos.write(datas, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtils.close(bos);
			CloseUtils.close(bis);
		}

	}


	/*
	 * 通过递归得到某一路径下所有的目录及其文件
	 */
	public static void getFiles(String filePath, List<String> filelist) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				getFiles(file.getAbsolutePath(), filelist);
				// System.out.println( "显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath()
				// );
			} else {
				// System.out.println( "显示" + filePath + "下所有子目录" + file.getAbsolutePath() );
				filelist.add(file.getAbsolutePath());
			}
		}
	}

}

package com.hframework.lang;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩与解压缩。
 * 
 * @author heerjian
 * @date 2016年10月24日
 */
public class ZipUtils {
	public static final String FILE_SEPARATOR = "/";

	/**
	 * 压缩文件。
	 * 
	 * @param outFile
	 *            压缩后输出的文件。
	 * @param inFile
	 *            要压缩的文件。
	 */
	public static void zip(String outFile, String inFile) throws IOException {
		zip(outFile, inFile, true, null);
	}

	/**
	 * 压缩文件。
	 * 
	 * @param outFile
	 *            压缩后输出的文件。
	 * @param inFile
	 *            要压缩的文件。
	 * @param wrap
	 *            是否包含根目录名。
	 * @param excludeRegex
	 *            排除文件的表达式。
	 * @throws IOException
	 */
	public static void zip(String outFile, String inFile, boolean wrap, String excludeRegex) throws IOException {
		ZipOutputStream out = null;
		try {
			zip(out = new ZipOutputStream(new FileOutputStream(outFile)), new File(inFile), wrap,
					excludeRegex == null ? null : Pattern.compile(excludeRegex));
		} finally {
			CloseUtils.close(out);
		}
	}

	/**
	 * 
	 * 压缩文件。
	 * 
	 * @param out
	 *            压缩流。
	 * @param file
	 *            要压缩的文件。
	 * @param wrap
	 *            是否包含根目录名。
	 * @param exclude
	 *            排除文件的表达式。
	 * @throws IOException
	 */
	public static void zip(ZipOutputStream out, File file, boolean wrap, Pattern exclude) throws IOException {
		if (wrap || file.isFile()) {
			zip(out, file, file.getName(), exclude);
		} else if (file.isDirectory()) {
			zip(out, file, "", exclude);
		}
	}

	/**
	 * 压缩文件。
	 * 
	 * @param out
	 *            压缩流。
	 * @param file
	 *            要压缩的文件。
	 * @param path
	 *            压缩文件在压缩包中的目录。
	 * @param exclude
	 *            排除文件的表达式。
	 * @throws IOException
	 */
	public static void zip(ZipOutputStream out, File file, String path, Pattern exclude) throws IOException {
		if (!file.exists())
			return;
		if (path == null)
			path = "";
		if (exclude != null && exclude.matcher(path).find())
			return;

		if (file.isFile()) {
			out.putNextEntry(new ZipEntry(path));
			InputStream input = null;
			try {
				input = new FileInputStream(file);
				byte[] buf = new byte[4096];
				int c;
				while ((c = input.read(buf)) != -1) {
					out.write(buf, 0, c);
				}
			} finally {
				CloseUtils.close(input);
			}
		} else if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				zip(out, f, path + FILE_SEPARATOR + f.getName(), exclude);
			}
		}
	}

	/**
	 * 解压。
	 * 
	 * @param inFile
	 *            解压的zip文件。
	 * @param outDir
	 *            解压目录。
	 */
	public static void unzip(String inFile, String outDir) throws IOException {
		unzip(new File(inFile), outDir);
	}

	/**
	 * 解压。
	 * 
	 * @param inFile
	 *            解压的zip文件。
	 * @param outDir
	 *            解压目录。
	 */
	public static void unzip(File inFile, String outDir) throws IOException {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(inFile);
			byte[] buf = new byte[4096];
			int c;
			for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry entry = entries.nextElement();
				String name = entry.getName().replace("\\", FILE_SEPARATOR);
				File file = new File(outDir + name);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				InputStream in = null;
				OutputStream out = null;
				try {
					in = zipFile.getInputStream(entry);
					out = new FileOutputStream(file);
					while ((c = in.read(buf)) != -1) {
						out.write(buf, 0, c);
					}
				} finally {
					CloseUtils.close(in);
					CloseUtils.close(out);
				}
			}
		} finally {
			CloseUtils.close(zipFile);
		}
	}
}

package com.base.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.base.common.string.StringUtil;

/**
 * 
 * 
 * @author: <a href="luzaoyang@base.com.cn">Lu Zaoyang</a>
 * @version: 1.0, 2011-4-29
 * 
 */
public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * 获得文件名后缀
     * 
     * @param fName
     * @return
     */
    public static String getFileExtention(String fName) {
        if (StringUtil.isEmptyOrNull(fName)) {
            return "";
        }
        return fName.lastIndexOf(".") == -1 ? "" : fName.substring(fName.lastIndexOf(".") + 1);
    }

    /**
     * 获得去掉后缀的文件名
     * 
     * @param fName
     *            文件名
     * @return
     */
    public static String getFileName(String fName) {
        if (StringUtil.isEmptyOrNull(fName)) {
            return "";
        }
        return fName.lastIndexOf(".") == -1 ? fName : fName.substring(0, fName.lastIndexOf("."));
    }

    /**
     * 输出字符串到制定路径
     * 
     * @param str
     *            字符串
     * @param filePath
     *            文件路径
     * @return
     * @throws IOException
     */
    public static boolean writeStr2File(String str, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

        bw.write(str);
        bw.flush();
        bw.close();

        return true;
    }

    /**
     * 输出序列号对象到指定路径
     * 
     * @param obj
     *            序列号对象
     * @param filePath
     *            文件路径
     * @return
     * @throws IOException
     */
    public static boolean writeObj2File(Serializable obj, String filePath) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));

        oos.writeObject(obj);
        oos.flush();
        oos.close();

        return true;
    }

    /**
     * 读取文件中对象
     * 
     * @param filePath
     *            文件路径
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObject(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));

        Object obj = oos.readObject();
        oos.close();

        return obj;
    }

    /**
     * 从指定目录下获得所有指定后缀的的文件
     * 
     * @param path
     *            指定目录
     * @param ext
     *            文件后缀 如果为空则读取所有文件
     * @return
     */
    public static List<File> getPathFiles(String path, String ext) {
        List<File> pathFiles = new ArrayList<File>();

        File file = new File(path);
        if (!file.exists()) {
            return pathFiles;
        }

        if (file.isFile()) {
            pathFiles.add(file);
        }

        File[] fileList = file.listFiles();
        try {
            if (null != fileList) {
                for (File f : fileList) {
                    if (f.isFile()) {
                        if (StringUtil.isEmptyOrNull(ext)) {
                            pathFiles.add(f);
                        } else {
                            if (f.getName().endsWith("." + ext) || f.getName().endsWith(ext)) {
                                pathFiles.add(f);
                            }
                        }
                    } else if (f.isDirectory()) {
                        // 如果是目录，往下继续查找
                        pathFiles.addAll(getPathFiles(f.getAbsolutePath(), ext));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取文件失败", e);
        }

        return pathFiles;
    }

    /**
     * 从指定目录下获得所有指定后缀的的文件路径
     * 
     * @param path
     *            指定目录
     * 
     * @param ext
     *            文件后缀 如果为空则读取所有文件
     * 
     * @return
     */
    public static List<String> getAllFilesPath(String path, String ext) {
        List<String> pathFiles = new ArrayList<String>();

        File file = new File(path);
        if (!file.exists()) {
            return pathFiles;
        }

        if (file.isFile()) {
            pathFiles.add(file.getAbsolutePath());
        }

        File[] fileList = file.listFiles();
        try {
            if (null != fileList) {
                for (File f : fileList) {
                    if (f.isFile()) {
                        if (StringUtil.isEmptyOrNull(ext)) {
                            pathFiles.add(f.getAbsolutePath());
                        } else {
                            if (f.getName().endsWith("." + ext) || f.getName().endsWith(ext)) {
                                pathFiles.add(f.getAbsolutePath());
                            }
                        }
                    } else if (f.isDirectory()) {
                        // 如果是目录，往下继续查找
                        pathFiles.addAll(getAllFilesPath(f.getAbsolutePath(), ext));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取文件失败", e);
        }

        return pathFiles;
    }

    /**
     * 从指定目录下获得所有的文件路径
     * 
     * @param path
     *            指定目录
     * @return
     */
    public static List<String> getAllFilesPath(String path) {
        return getAllFilesPath(path, null);
    }

    /**
     * 从指定目录下获得所有的文件
     * 
     * @param path
     *            指定目录
     * @return
     */
    public static List<File> getPathFiles(String path) {
        return getPathFiles(path, null);
    }

    /**
     * 将文件按行读取为列表， 并转化为指定编码
     * 
     * @param file
     *            文件对象
     * @param fEncoding
     *            源编码
     * @param encoding
     *            目标编码
     * @return
     */
    public static List<String> readFile2List(File file, String fEncoding, String encoding) {
        List<String> list = new ArrayList<String>();
        if (file == null || !file.exists()) {
            return list;
        }

        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);

            // 并按编码读取文件
            if ("UTF-8".equalsIgnoreCase(fEncoding)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new UnicodeReader(bis, fEncoding));
            }

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (StringUtil.isEmptyOrNull(line.trim())) {
                    continue;
                }

                if (StringUtil.isEmptyOrNull(encoding)) {
                    list.add(line.trim());
                } else {
                    list.add(StringUtil.changeEncoding(line.trim(), fEncoding, encoding));
                }
            }

            is.close();
        } catch (Exception e) {
            logger.info(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info(e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.info(e);
                }
            }
        }

        return list;
    }

    /**
     * 刪除指定文件
     * 
     * @param filePath
     *            指定文件路径
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File f = new File(filePath);

        return deleteFile(f);
    }

    /**
     * 删除指定文件
     * 
     * @param f
     *            文件
     * @return
     */
    public static boolean deleteFile(File f) {
        if (f == null) {
            return true;
        }

        if (f.exists()) {
            return f.delete();
        }

        return true;
    }

    /**
     * 将源文件移动到指定目录
     * 
     * @param sourceFile
     *            源文件
     * @param destFile
     *            目标文件
     */
    public static boolean moveFile(String sourceFile, String destFile) {
        File sFile = new File(sourceFile);
        return moveFile(sFile, destFile);
    }

    /**
     * 将源文件移动到指定目录
     * 
     * @param sFile
     *            源文件
     * @param destFile
     *            目标文件
     */
    public static boolean moveFile(File sFile, String destFile) {
        if (!sFile.exists()) {
            return false;
        }

        File dFile = new File(destFile);
        return sFile.renameTo(dFile);
    }

    /**
     * 删除多个文件
     * 
     * @param files
     *            文件列表
     */
    public static void deleteMultiFiles(List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        for (File f : files) {
            f.delete();
        }
    }

    /**
     * 删除多个文件
     * 
     * @param files
     *            文件列表
     */
    public static void delMultiFiles(List<String> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        for (String file : files) {
            File f = new File(file);
            f.delete();
        }
    }

    /**
     * 删除多个文件
     * 
     * @param files
     *            文件列表
     */
    public static void deleteMultiFiles(File[] files) {
        if (files == null || files.length < 1) {
            return;
        }

        deleteMultiFiles(Arrays.asList(files));
    }

    /**
     * 创建文件路径
     * 
     * @param dir
     *            文件路径
     */
    public static File makeDirs(String dir) {
        File dirF = new File(dir);

        if (!dirF.exists()) {
            dirF.mkdirs();
        }

        return dirF;
    }

    /**
     * 获得指定目录下指定后缀的所有文件的绝对路径
     * 
     * @param filePath
     *            文件目录
     * @param ext
     *            文件后缀
     * @return 文件路径列表
     */
    public static List<String> getAbsolutePathList(String filePath, String ext) {
        List<String> filePaths = new ArrayList<String>();

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        File[] fileList = file.listFiles();
        try {
            if (fileList != null && fileList.length != 0) {
                for (File f : fileList) {
                    if (f.isFile() && f.getName().endsWith(ext)) {
                        RandomAccessFile raf = null;
                        FileChannel channel = null;
                        FileLock lock = null;

                        try {
                            raf = new RandomAccessFile(f, "rw");
                            channel = raf.getChannel();
                            lock = channel.tryLock();

                            // 如果能锁定,则添加
                            if (lock == null || !lock.isValid()) {
                                continue;
                            }

                            filePaths.add(f.getAbsolutePath());
                        } catch (Exception e) {
                            logger.error("转移文件程序发生错误 lock file error: " + f.getName() + ">>", e);
                        } finally {
                            if (lock != null) {
                                lock.release();
                            }

                            if (channel != null) {
                                channel.close();
                            }

                            if (raf != null) {
                                raf.close();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取需要移动的文件失败", e);
        }

        return filePaths;
    }
    
    /**
     * 获得程序当前路径
     * @param parentDir
     * @param fileName
     * @return
     */
    public static String getPath(String parentDir, String fileName) {
      String path = null;
      String userdir = System.getProperty("user.dir");
      String userdirName = new File(userdir).getName();
      if ((userdirName.equalsIgnoreCase("lib")) || (userdirName.equalsIgnoreCase("bin"))) {
        File newf = new File(userdir);
        File newp = new File(newf.getParent());
        if (fileName.trim().equals("")) {
          path = newp.getPath() + File.separator + parentDir;
        } else {
          path = newp.getPath() + File.separator + parentDir + File.separator + fileName;
        }
      } else if (fileName.trim().equals("")) {
        path = userdir + File.separator + parentDir;
      } else {
        path = userdir + File.separator + parentDir + File.separator + fileName;
      }
      return path;
    }
}

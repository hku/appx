package com.app.assistant.utils;

import android.os.Environment;

import java.io.File;

/**
 * @说明 文件管理方法
 * @作者 LY
 * @文件 FileManagerUtils.java
 * @时间 2015年6月11日 下午9:35:56
 * @版权 Copyright(c) 2014 LY-版权所有
 */
public class FileManagerUtils {
    private static String filePath = "";
    private static FileManagerUtils mInstance;
    private static final String apkFolder = "apk";

    public synchronized static FileManagerUtils getInstance() {
        if (mInstance == null) {
            mInstance = new FileManagerUtils();
        }
        return mInstance;
    }

    /**
     * 设置文件夹名
     *
     * @param folder 文件夹名
     */
    public void setFolderName(String folder) {
        filePath = folder;
    }

    /**
     * 判断是否有存储卡
     */
    public boolean isSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取存储卡路径
     */
    public String getSdcardPath() {
        if (isSdcard()) {
            return Environment.getExternalStorageDirectory().getPath() + "/";
        } else {
            return "";
        }
    }

    /**
     * 获取应用程序本地文件夹路径
     */
    public String getAppPath() {
        String path = getSdcardPath() + filePath;
        File dirFile = new File(path);
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
        return path;
    }

    /**
     * 获取apk的路径名称
     *
     * @return
     */
    public String getApkPath() {
        String appPath = getAppPath();
        String path = appPath + apkFolder;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        if (!dirFile.exists()) {
            LogUtils.d(path + "文件创建失败");
        } else {
            LogUtils.d(path + "success");
        }
        return path;
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public long getFileSize(File file) {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @return 文件大小/M
     */
    public String getFileSize() {
        String str = "0";
        long size = getFileSize(new File(getAppPath()));
        // if (size == 0) {
        // str = "0 K";
        // } else if (size > 0 && size < 1024) {
        // str = size + " K";
        // } else if (size > 1024 && size < 1024 * 1024) {
        // str = size / 1024 + " K";
        // } else if (size >= 1024 * 1024) {
        str = String.valueOf(size / (1024 * 1024));
        // }
        return str;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 路径
     * @return 是否成功
     */
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}

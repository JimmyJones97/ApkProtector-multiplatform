package com.mcal.apkprotector.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * Created by linchaolong on 2015/9/5.
 */
public class FileHelper {

    public static final String TAG = FileHelper.class.getSimpleName();

    public static final int KB = 1024;
    public static final int MB = KB * 1024;
    public static final int GB = MB * 1024;

    public static String getNoSuffixName(File file){
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        if(dotIndex == -1){
            return name;
        }
        return name.substring(0, dotIndex);
    }

    /**
     * 判断文件格式
     *
     * @param file      文件
     * @param suffix    后缀，不带“.”
     * @return  true:格式匹配成功; false:格式匹配不成功。
     */
    public static boolean isSuffix(File file, String suffix){
        if (file == null || !file.exists() || suffix == null) return false;
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        if(dotIndex == -1){
            return false;
        }
        String fileSuffix = name.substring(dotIndex);
        return fileSuffix.equalsIgnoreCase("."+suffix);
    }

    /**
     * 递归文件
     *
     * @param file 根目录或文件
     * @param fileHandler 文件处理器，如果handle方法返回true表示递归子目录与文件，否则不递归
     */
    public static void recusive(File file, FileHandler fileHandler){

        if (file == null || !file.exists() || fileHandler == null) {
            return;
        }

        if (fileHandler.handle(file)) {
            // 递归
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for(File tmp : files){
                    recusive(tmp, fileHandler);
                }
            }
        }
    }

    /**
     * 拷贝一个目录
     *
     * @param dir
     * @param copy
     */
    public static boolean copyDir(File dir, File copy) {
        return copyDir(dir,copy,true);
    }

    /**
     * 拷贝一个目录
     *
     * @param dir
     * @param copy
     * @param includeDir    是否包含dir目录
     * @return
     */
    public static boolean copyDir(File dir, File copy, boolean includeDir) {
        try {
            if(includeDir){
                FileUtils.copyDirectoryToDirectory(dir,copy);
            }else{
                FileUtils.copyDirectory(dir,copy);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return copy.exists();
    }

    /**
     * 拷贝一个文件
     *
     * @param file
     * @param copy
     */
    public static boolean copyFile(File file, File copy){
        try {
            FileUtils.copyFile(file,copy);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return copy.exists();
    }

    /**
     * 拷贝一个文件或目录
     *
     * @param file
     * @param copy
     * @return
     */
    public static boolean copy(File file, File copy){
        if(file.isFile()){
            copyFile(file,copy);
        }else{
            copyDir(file,copy);
        }
        return copy.exists();
    }

    /**
     * 删除一个文件或目录
     *
     * @param file
     * @return
     */
    public static boolean delete(File file){
        if(!file.exists()){
            return false;
        }
        if(file.isFile()){
            return file.delete();
        }
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 清理一个目录
     *
     * @param dir
     * @return
     */
    public static boolean cleanDirectory(File dir){
        try {
            if(!exists(dir) || dir.isFile()){
                return false;
            }
            FileUtils.cleanDirectory(dir);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 文件是否存在
     *
     * @param file
     *
     * @return
     */
    public static boolean exists(File file){
        return file != null && file.exists();
    }

    /**
     * 移动一个文件或目录
     *
     * @param file    文件
     * @param dest    目标路径
     * @return
     */
    public static boolean move(File file, File dest){
        if(!exists(file)){
            return false;
        }
        if(file.isDirectory()){
            try {
                FileUtils.moveDirectory(file,dest);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                FileUtils.moveFile(file,dest);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取所有文件和目录
     *
     * @param file
     * @return
     */
    public static List<File> listAllFiles(File file){
        ArrayList<File> files = new ArrayList<>();
        recusive(file, f -> {
            files.add(f);
            return true;
        });
        return files;
    }

    /**
     * 文件处理器
     *
     * @author linchaolong
     *
     */
    public interface FileHandler {
        /**
         * 处理文件的方法
         *
         * @param file 文件
         */
        boolean handle(File file);
    }
}
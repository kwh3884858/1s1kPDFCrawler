package com.StarPlatinum.s1kPDFCrawler;

import java.io.File;
import java.io.IOException;

public class CreateFileUtil {
	   public static File createFile(String destFileName, String suffix) {
	        File file = new File(destFileName + "." + suffix);
	        if(file.exists()) {
	            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
	            
	            for (int i = 1; file.exists() && i < Integer.MAX_VALUE; i++) {
	            		file = new File(destFileName + "("+ i +")." + suffix);
	            }
	            System.out.println("文件重命名为:" + file.getName());
	            return file;
	        }
	        if (destFileName.endsWith(File.separator)) {
	            System.out.println("创建单个文件" + file.getName() + "失败，目标文件不能为目录！");
	            return null;
	        }
	        //判断目标文件所在的目录是否存在
	        if(!file.getParentFile().exists()) {
	            //如果目标文件所在的目录不存在，则创建父目录
	            System.out.println("目标文件所在目录不存在，准备创建它！");
	            if(!file.getParentFile().mkdirs()) {
	                System.out.println("创建目标文件所在目录失败！");
	                return null;
	            }
	        }
	        //创建目标文件
	        try {
	            if (file.createNewFile()) {
	                System.out.println("创建单个文件" + destFileName + "成功！");
	                return file;
	            } else {
	                System.out.println("创建单个文件" + destFileName + "失败！");
	                return null;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
	            return null;
	        }
	    }
	   
	   
	    public static boolean createDir(String destDirName) {
	        File dir = new File(destDirName);
	        if (dir.exists()) {
	            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
	            return false;
	        }
	        if (!destDirName.endsWith(File.separator)) {
	            destDirName = destDirName + File.separator;
	        }
	        //创建目录
	        if (dir.mkdirs()) {
	            System.out.println("创建目录" + destDirName + "成功！");
	            return true;
	        } else {
	            System.out.println("创建目录" + destDirName + "失败！");
	            return false;
	        }
	    }
	   
	   
	    public static String createTempFile(String prefix, String suffix, String dirName) {
	        File tempFile = null;
	        if (dirName == null) {
	            try{
	                //在默认文件夹下创建临时文件
	                tempFile = File.createTempFile(prefix, suffix);
	                //返回临时文件的路径
	                return tempFile.getCanonicalPath();
	            } catch (IOException e) {
	                e.printStackTrace();
	                System.out.println("创建临时文件失败！" + e.getMessage());
	                return null;
	            }
	        } else {
	            File dir = new File(dirName);
	            //如果临时文件所在目录不存在，首先创建
	            if (!dir.exists()) {
	                if (!CreateFileUtil.createDir(dirName)) {
	                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
	                    return null;
	                }
	            }
	            try {
	                //在指定目录下创建临时文件
	                tempFile = File.createTempFile(prefix, suffix, dir);
	                return tempFile.getCanonicalPath();
	            } catch (IOException e) {
	                e.printStackTrace();
	                System.out.println("创建临时文件失败！" + e.getMessage());
	                return null;
	            }
	        }
	    }

}

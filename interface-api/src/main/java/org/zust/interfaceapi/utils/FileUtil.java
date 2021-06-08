package org.zust.interfaceapi.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.zust.interfaceapi.extra_entity.FileModel;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author lxy
 * @date 2020-09-02-20:29
 * @function
 **/
// 将二进制数写入文件里
public class FileUtil{
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * @Title: byteToFile
     * @Description: 把二进制数据转成指定后缀名的文件，例如PDF，PNG等
     * @param contents 二进制数据
     * @param filePath 文件存放目录，包括文件名及其后缀，如D:\file\bike.jpg
     * @Author: Wiener
     * @Time: 2018-08-26 08:43:36
     */
    public static void byteToFile(byte[] contents, String filePath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(contents);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            if (!path.exists()) {
                logger.info("文件夹不存在，创建。path={}", path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    logger.error("创建文件夹失败，path={}", path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            logger.error("输出文件流时抛异常，filePath={}", filePath, e);
        } finally {
            try {
                output.close();
                fos.close();
                bis.close();
            } catch (IOException e0) {
                logger.error("文件处理失败，filePath={}", filePath, e0);
            }
        }
    }

    // 将输入流写入file
    public static boolean copyInputStreamToFile(InputStream in,File file) {

        Boolean flag = false;

        try{
            FileOutputStream outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;


    }

    // 将文件转成二进制数组
    public static byte[] fileToByte(File file){
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            fis.close();
            return bytes;
        }catch (Exception ex){
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
    }

    // 将文件转成二进制字符串
    public static String fileToByteStr(File file){
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            fis.close();
            return new String(bytes,"ISO-8859-1");
        }catch (Exception ex){
            throw new RuntimeException("transform file into bin String 出错",ex);
        }
    }

    // 通用：创建文件或目录（文件夹）
    public static boolean createFolder(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        if (file.exists()) {
            return false;
        }else {
            String fileName = file.getName();
            // 根据文件名中是否有 . 来判断是文件还是文件夹
            if (fileName.contains(".")) {   //有. 就当做文件
                return createFile(absolutePath);
            } else {    //没有. 就当做文件夹
                return createDirectory(absolutePath);
            }
        }
    }

    /**
     * 创建单个空文件
     * @param absolutePath 待创建文件的绝对路径（包括文件名）
     *                     file.createNewFile()创建文件的前提条件是父目录必须存在
     * @return
     */
    private static boolean createFile(String absolutePath) throws IOException {
        boolean flag = false;
        File file = new File(absolutePath);

        String parentPath = file.getParent();
        File parentFile = new File(parentPath);
        //先判断文件的父目录是否存在
        if (!parentFile.exists()){
            //不存在的话就先创建父目录
            createDirectory(parentPath);
        }

        //存在的话就直接创建文件
        if (file.createNewFile()) {
            flag = true;
        }

        return flag;
    }

    /**
     * 创建目录(文件夹)
     * @param absolutePath  待创建文件夹的绝对路径
     * @return
     */
    private static boolean createDirectory(String absolutePath){
        boolean flag = false;
        File file = new File(absolutePath);
        if(file.mkdirs()){
            flag = true;
        }
        return flag;
    }

    // 通用：删除文件或目录（文件夹）
    public static boolean deleteFolder(String absolutePath){
        boolean flag = false;
        File file = new File(absolutePath);
        if (!file.exists()) {
            return flag;
        }else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(absolutePath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(absolutePath);
            }
        }
    }

    /**
     * 删除单个文件
     * @param absolutePath 被删除文件的绝对路径（包括文件名）
     * @return
     *      文件删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String absolutePath){
        boolean flag = false;
        File file = new File(absolutePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
//            System.gc();   // 回收资源
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param absolutePath 被删除目录（文件夹）的绝对路径
     * @return
     *      目录（文件夹）删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String absolutePath) {
        //absolutePath，自动添加文件分隔符
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath = absolutePath + File.separator;
        }
        File dirFile = new File(absolutePath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }

        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将某一路径下的文件写到指定路径下
     * @param originalPath  原路径
     * @param targetPath    目标路径
     */
    public static void writeFile(String originalPath,String targetPath){

        FileInputStream in = null;
        FileOutputStream out = null;
        BufferedInputStream bufferedIn = null;
        BufferedOutputStream bufferedOut = null;
        try {
            in=new FileInputStream(originalPath);
            out = new FileOutputStream(targetPath);
            bufferedIn=new BufferedInputStream(in);
            bufferedOut=new BufferedOutputStream(out);

            byte[] by=new byte[1];
            while (bufferedIn.read(by)!=-1){
                bufferedOut.write(by);
            }

            bufferedOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedIn!=null) bufferedIn.close();
                if (bufferedOut!=null) bufferedOut.close();
                if (out!=null) out.close();
                if (in!=null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归获取路径下所有文件夹和文件
     * @param folder
     * @return
     */
    public static List<File> getFileList(File folder){
        ArrayList<File> list = new ArrayList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()){ //如果该文件是文件夹
                List<File> fileList = getFileList(file);
                list.addAll(fileList);
            }
        }
        return list;
    }

    // 根据路径获取所有的文件夹和文件,及文件的md5值
    public static Map<String, FileModel> getFolderAndMD5(String path) throws IOException {
        HashMap<String, FileModel> fileModelHashMap = new HashMap<>();
        File folder = new File(path);
        Object[] files = getFileList(folder).toArray();
        Arrays.sort(files);

        for (Object obj : files) {
            File file = (File) obj;
            // 去掉根目录,正则的\\\\,转义为java的\\,再转义为\
            String key = file.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(path, "");
            String md5 = "";// 文件夹不比较md5值
            if (file.isFile()){
                md5 = DigestUtils.md5Hex(new FileInputStream(file));
            }
            FileModel fileModel = new FileModel(file, md5);
            fileModelHashMap.put(key,fileModel);
        }

        return fileModelHashMap;
    }

    // 根据路径获取所有的文件(不包括文件夹)，及文件的md5值
    public static Map<String, FileModel> getFileAndMD5(String path) throws IOException{
        HashMap<String, FileModel> fileModelHashMap = new HashMap<>();

        Map<String, FileModel> fileModel = getFolderAndMD5(path);
        for (String s : fileModel.keySet()) {
            File file = fileModel.get(s).getFile();
            if (file.isFile()){
                fileModelHashMap.put(s,fileModel.get(s));
            }
        }
        return fileModelHashMap;
    }

    /**
     * 比较两个文件集合的不同
     * @param fileMap1 文件1集合
     * @param fileMap2 文件2集合
     * @return
     *      返回文件2中没有，但文件1中有的文件集合
     */
    public static List<FileModel> compareFile(Map<String, FileModel> fileMap1, Map<String, FileModel> fileMap2) {
        List<FileModel> list = new ArrayList<FileModel>();
        for(String key : fileMap1.keySet()){
            FileModel fileModel1 = fileMap1.get(key);
            FileModel fileModel2 = fileMap2.get(key);
            // 将fileMap2中没有的文件夹和文件，添加到结果集中
            if (fileModel2 == null){
                list.add(fileModel1);
                continue;
            }
            // 若双方相同路径下的某文件的md5值不同，则添加到结果集中
            if (!fileModel1.getFile().isDirectory() && !fileModel1.getMd5().equals(fileModel2.getMd5())) {
                list.add(fileModel1);
            }
        }

        return list;
    }

    /**
     * 将path1下的文件和path2下的文件互相备份（不考虑文件内容不同的同名文件）
     * @param path1 路径1
     * @param path2 路径2
     */
    public static boolean LocalFileEachBackups(String path1,String path2) throws IOException {
        // 获取路径下所有文件夹和文件,及文件的md5值
        Map<String, FileModel> fileMap1 = FileUtil.getFolderAndMD5(path1);
        Map<String, FileModel> fileMap2 = FileUtil.getFolderAndMD5(path2);

        // 得到fileMap2中没有的文件夹和文件,及md5值不同的文件
        List<FileModel> fileModels1 = FileUtil.compareFile(fileMap1, fileMap2);
        for (FileModel fileModel : fileModels1) {
            String absolutePath = fileModel.getFile().toString().replace("\\","/");
            String relativePath = absolutePath.replace(path1, "");
            if (fileModel.getFile().isFile()){ // 写入fileMap2中没有的文件
                FileUtil.writeFile(absolutePath,path2+relativePath);
            }else if (fileModel.getFile().isDirectory()){ // 写入fileMap2中没有的文件夹
                FileUtil.createDirectory(path2+relativePath);
            }
        }
        // 重新比较两文件，获取路径1下有但路径2下没有的文件
        fileModels1 = FileUtil.compareFile(FileUtil.getFolderAndMD5(path1), FileUtil.getFolderAndMD5(path2));

        // 得到fileMap1中没有的文件夹和文件,及md5值不同的文件
        List<FileModel> fileModels2 = FileUtil.compareFile(fileMap2, fileMap1);
        for (FileModel fileModel : fileModels2) {
            String absolutePath = fileModel.getFile().toString().replace("\\","/");
            String relativePath = absolutePath.replace(path2, "");
            if (fileModel.getFile().isFile()){ // 写入fileMap1中没有的文件
                FileUtil.writeFile(absolutePath,path1+relativePath);
            }else if (fileModel.getFile().isDirectory()){ // 写入fileMap1中没有的文件夹
                FileUtil.createDirectory(path1+relativePath);
            }
        }
        // 重新比较两文件，获取路径2下有但路径1下没有的文件
        fileModels2 = FileUtil.compareFile(FileUtil.getFolderAndMD5(path2), FileUtil.getFolderAndMD5(path1));

        //用于调用垃圾收集器。
        // 在调用时，垃圾收集器将运行以回收未使用的内存空间。它将尝试释放被丢弃对象占用的内存。
        System.gc();
        if (fileModels1.size()==0 && fileModels2.size()==0){
            return true;
        }else{
            return false;
        }


    }

    /**
     * 将主路径下的文件作为依据，对副路径下的副本进行拷贝
     *      如果出现文件内容不同的同名文件，采用主路径下的文件覆盖副路径下的文件
     * @param mainPath  主路径
     * @param copyPath  副路径
     */
    public static boolean DoubleLocalFileBackups(String mainPath, String copyPath) throws IOException {
        // 获取主路径和副路径下所有文件夹和文件,及文件的md5值
        Map<String, FileModel> fileMap1 = FileUtil.getFolderAndMD5(mainPath);
        Map<String, FileModel> fileMap2 = FileUtil.getFolderAndMD5(copyPath);

        // 得到副本中没有的文件夹和文件,及md5值不同的文件
        List<FileModel> fileModels1 = FileUtil.compareFile(fileMap1, fileMap2);
        // 得到主本中没有的文件夹和文件,及md5值不同的文件
        List<FileModel> fileModels2 = FileUtil.compareFile(fileMap2, fileMap1);

        // 判断主副本文件中是否有文件内容不同的同名文件(主本不变，副本修改)
        for (FileModel file1 : fileModels1) {
            String s1  = file1.getFile().toString().replace("\\", "/").replace(mainPath, "");
            for (FileModel file2 : fileModels2) {
                String s2 = file2.getFile().toString().replace("\\", "/").replace(copyPath, "");
                if (s1.equals(s2) && !file1.getMd5().equals(file2.getMd5())){ //出现同名文件，且文件内容不同
                    FileUtil.writeFile(mainPath+s1,copyPath+s1); // 主的覆盖副的
                }
            }
        }

        // 以主本为依据，将副本中缺少的文件加上，多余的文件删除
        // 添加缺失的副本
        // 重新获取副本
        fileModels1 = FileUtil.compareFile(fileMap1, fileMap2);
        for (FileModel fileModel : fileModels1) {
            String absolutePath = fileModel.getFile().toString().replace("\\","/");
            String relativePath = absolutePath.replace(mainPath, "");
            if (fileModel.getFile().isFile()){ // 写入副本中没有的文件
                FileUtil.writeFile(absolutePath,copyPath+relativePath);
            }else if (fileModel.getFile().isDirectory()){ // 写入副本中没有的文件夹
                FileUtil.createDirectory(copyPath+relativePath);
            }
        }
        fileModels1 = FileUtil.compareFile(FileUtil.getFolderAndMD5(mainPath), FileUtil.getFolderAndMD5(copyPath));

        System.gc();

        // 删除多余的副本
        // 重新获取主本
        fileModels2 = FileUtil.compareFile(fileMap2, fileMap1);
        for (FileModel fileModel : fileModels2) {
            String delPath = fileModel.getFile().toString().replace("\\","/");
            FileUtil.deleteFolder(delPath);// 删除副本中多余的文件或文件夹
        }
        fileModels2 = FileUtil.compareFile(FileUtil.getFolderAndMD5(copyPath), FileUtil.getFolderAndMD5(mainPath));

        if (fileModels1.size()==0 && fileModels2.size()==0){
            return true;
        }else{
            return false;
        }
    }


    public static HashMap ThreadDownloader (String origin_url) throws IOException, InterruptedException{

        Boolean status = false;
        HashMap<String, Object> map = new HashMap<>();
        // 记录开始下载的时间
        long begin_time = new Date().getTime();

        // 创建一个URL链接
        URL url = new URL(origin_url);

        // 获取连接
        URLConnection conn = url.openConnection();

        // 获取文件全路径
        String fileName = url.getFile();

        // 获取文件名
        fileName = fileName.substring(fileName.lastIndexOf("/"));
        String[] fname = fileName.split("\\.");
        System.out.println("fileName ="+fname[0]);
        map.put("fileName",fname[0]);

        System.out.println("开始下载>>>");

        // 获取文件大小
        int fileSize = conn.getContentLength();

        System.out.println("文件总共大小：" + fileSize + "字节");

        // 设置分块大小
        int blockSize = 1024 * 1024;
        // 文件分块的数量
        int blockNum = fileSize / blockSize;

        if ((fileSize % blockSize) != 0) {
            blockNum += 1;
        }

        System.out.println("分块数->线程数：" + blockNum);

        Thread[] threads = new Thread[blockNum];
        for (int i = 0; i < blockNum; i++) {

            // 匿名函数对象需要用到的变量
            final int index = i;
            final int finalBlockNum = blockNum;
            final String finalFileName = fileName;

            // 创建一个线程
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        // 重新获取连接
                        URLConnection conn = url.openConnection();
                        // 重新获取流
                        InputStream in = conn.getInputStream();
                        // 定义起始和结束点
                        int beginPoint = 0, endPoint = 0;

                        System.out.print("第" + (index + 1) + "块文件：");
                        beginPoint = index * blockSize;

                        // 判断结束点
                        if (index < finalBlockNum - 1) {
                            endPoint = beginPoint + blockSize;
                        } else {
                            endPoint = fileSize;
                        }

                        System.out.println("起始字节数：" + beginPoint + ",结束字节数：" + endPoint);

                        // 将下载的文件存储到一个文件夹中
                        //当该文件夹不存在时，则新建
                        File filePath = new File("D:/book/temp_file/");
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }

                        FileOutputStream fos = new FileOutputStream(new File("D:/book/temp_file/", finalFileName + "_" + (index + 1)));

                        // 跳过 beginPoint个字节进行读取
                        in.skip(beginPoint);
                        byte[] buffer = new byte[1024];
                        int count;
                        // 定义当前下载进度
                        int process = beginPoint;
                        // 当前进度必须小于结束字节数
                        while (process < endPoint) {

                            count = in.read(buffer);
                            // 判断是否读到最后一块
                            if (process + count >= endPoint) {
                                count = endPoint - process;
                                process = endPoint;
                            } else {
                                // 计算当前进度
                                process += count;
                            }
                            // 保存文件流
                            fos.write(buffer, 0, count);

                        }
                        fos.close();
                        in.close();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            };
            threads[i].start();

        }

        // 当所有线程都结束时才开始文件的合并
        for (Thread t : threads) {
            t.join();
        }

        // 若该文件夹不存在，则创建一个文件夹
        File filePath = new File("D:/book/download/");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 定义文件输出流
        FileOutputStream fos = new FileOutputStream("D:/book/download/" + fileName);
        for (int i = 0; i < blockNum; i++) {
            FileInputStream fis = new FileInputStream("D:/book/temp_file/" + fileName + "_" + (i + 1));
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fis.close();
        }
        fos.close();
        status = true;

        long end_time = new Date().getTime();
        long seconds = (end_time - begin_time) / 1000;
        long minutes = seconds / 60;
        long second = seconds % 60;

        System.out.println("下载完成>>>");
        System.out.println("用时：\" + minutes + \"分\" + second + \"秒");

        map.put("status",status);
        return map;
    }
}


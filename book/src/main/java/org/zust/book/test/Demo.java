package org.zust.book.test;

/**
 * @author: Linxy
 * @Date: 2021/6/6 16:47
 * @function: Java
 **/
public class Demo {

    public static void main(String[] args) {
        String command = "gpedit.msc";
        cmd(command);
    }

    public static boolean cmd(String command){
        boolean flag = false;
        try{
            Runtime.getRuntime().exec("cmd.exe /C start "+command);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}

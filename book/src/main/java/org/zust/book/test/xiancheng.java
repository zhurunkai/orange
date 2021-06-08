package org.zust.book.test;

/**
 * @author: Linxy
 * @Date: 2021/6/8 16:22
 * @function:
 **/
public class xiancheng {

    public static void main(String[] args) {

        //这样的方式开线程不需要去继承Thread类
        new Thread(() -> {
            // TODO Auto-generated method stub
            for(int i=0;i<10000;i++) {
                System.out.println("aa");
            }
        }).start();

        System.out.println("=================================================");

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<10000;i++) {
                            System.out.println("bb");
                        }
                    }
                }).start();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
    }


}

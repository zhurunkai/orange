package org.zust.account.utils;

import java.util.Random;

public class RandomName {
    public static String nameString() {
        String[] fristName = { "酒醉的", "帅气的","天真的","机智的","美丽的","可爱的","和谐的","聪明的","敬业的","爱国的"};
        String[] secondName = {"阿锴", "小婷", "炫宝", "蝴蝶", "刚哥", "恒仔" ,"小朱","小沈","小陈","小林","程哥"};
        int first = new Random().nextInt(fristName.length);
        int second = new Random().nextInt(secondName.length);
//        System.out.println(fristName[first] + secondName[second]);
        return fristName[first] + secondName[second];
    }
}



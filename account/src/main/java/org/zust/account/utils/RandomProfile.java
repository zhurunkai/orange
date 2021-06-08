package org.zust.account.utils;

import java.util.Random;

public class RandomProfile {
    public static String profileString() {
        String[] profile = {
                "https://stone-1258976754.cos.ap-shanghai.myqcloud.com/zcj.jpg",
                "https://stone-1258976754.cos.ap-shanghai.myqcloud.com/syw.jpg",
                "https://stone-1258976754.cos.ap-shanghai.myqcloud.com/smt.jpg",
                "https://stone-1258976754.cos.ap-shanghai.myqcloud.com/lyq.jpg",
                "https://stone-1258976754.cos.ap-shanghai.myqcloud.com/lxy.jpg",
                "https://bookmanage-1258976754.cos.ap-shanghai.myqcloud.com/k.jpg" };
        int num = new Random().nextInt(profile.length);
        System.out.println(profile[num]);
        return profile[num];
    }
}

package org.zust.common.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

public class Upload {
    private static final String bucketName = "orange-1258976754";
    // secretId
    private static final String secretId = "AKIDZ7a3StiVaXgprjJzFG1Wt2va2Phtb74r";
    // secretKey
    private static final String secretKey = "p9OURGwW0MJ1b4Oiw32s0ynSWUIGwa5T";

    // 1 初始化用户身份信息(secretId, secretKey，可在腾讯云后台中的API密钥管理中查看！

    private static COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

    // 2 设置bucket的区域, COS地域的简称请参照
    // https://cloud.tencent.com/document/product/436/6224，根据自己创建的存储桶选择地区
    private static ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));


//        public fileUplo(filepath) {
//            fdajkjk
//        }
}

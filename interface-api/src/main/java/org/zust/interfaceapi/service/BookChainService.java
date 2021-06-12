package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

/**
 * @author: Linxy
 * @Date: 2021/6/10 19:28
 * @function:
 **/
public interface BookChainService {

    ResType getChainBySidAndCid(HashMap map);
    ResType updateChainLocation(HashMap map);
    ResType updateChain(HashMap map);
    ResType deleteChain(HashMap map);
    ResType addReaderAccord(HashMap map);
    ResType getAllChain(HashMap map);
    ResType CollectChain(HashMap map);

}

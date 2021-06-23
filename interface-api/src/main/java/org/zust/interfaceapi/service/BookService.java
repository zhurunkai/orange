package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:22
 * @function:
 **/
public interface BookService {

    public ResType getBook(HashMap map);

    public ResType addBook(HashMap map);

    public ResType checkConvert(HashMap map);

    public ResType findBookDtobyIds(List<Integer> ids);

    public ResType recommendByUser(Integer userId);

    public ResType recommendByItem(Integer userId);
}

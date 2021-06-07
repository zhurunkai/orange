package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:22
 * @function:
 **/
public interface BookService {

    public ResType getBook(Integer id);

    public ResType addBook(HashMap map);

}

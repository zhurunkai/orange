package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:03
 * @function:
 **/
public interface BookShelfService {

//  添加书架
    public ResType addBookShelf(HashMap map);

//  根据id删除书架
    public ResType deleteBookShelfById(HashMap map);

//  根据id修改书架信息
    public ResType updateBookShelfById(HashMap map);

//  根据id获取书架信息
    public ResType getBookShelfById(String id);

//  根据用户token获取书架列表
    public ResType getBookShelfLists(String Token);

}

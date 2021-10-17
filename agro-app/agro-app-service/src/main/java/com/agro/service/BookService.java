package com.agro.service;

import com.agro.pojo.entity.Book;
import com.github.pagehelper.PageInfo;

public interface BookService {

    public int add(Book book);

    public PageInfo<Book> getlist(int page,int limit);

    Book getOne(String id);

    boolean delete(String id);

    PageInfo<Book> getlistByBame(int page, int limit, String name);
}

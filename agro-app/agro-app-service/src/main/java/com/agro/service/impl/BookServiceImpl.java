package com.agro.service.impl;

import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.entity.Book;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public int add(Book book) {
        Sid sid = new Sid();
        book.setId(sid.nextShort());
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        int insert = bookMapper.insert(book);

        return insert;
    }

    @Override
    public PageInfo<Book> getlist(int page, int limit) {


        List<Book> list = bookMapper.selectAll();
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public Book getOne(String id) {
        Example example = new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("id", id);
        Book books = bookMapper.selectOneByExample(example);
        return books;
    }

    @Override
    public boolean delete(String id) {
        Example example = new Example(Book.class);
        example.createCriteria().andEqualTo("id",id);
        int i = bookMapper.deleteByExample(example);
        return i>0?true:false;
    }

    @Override
    public PageInfo<Book> getlistByBame(int page, int limit, String name) {
        Example example = new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orLike("name","%" +name+"%");
        List<Book> books = bookMapper.selectByExample(example);

        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(books);
        return pageInfo;
    }
}

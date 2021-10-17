package com.agro.controller;


import com.agro.result.ServerResponse;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/book")
@RestController
public class BookController {

   /* @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ServerResponse add(Book book){
        int add = bookService.add(book);
        if (add>0){
            return ServerResponse.successMsg("添加成功");
        }
        return ServerResponse.errorMsg("添加失败");
    }
    @GetMapping("/getlist")
    public ServerResponse getlist(@RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "limit",defaultValue = "20")int limit,String name){

        if (!StringUtils.isEmpty(name)){
            PageInfo<Book> getlist = bookService.getlistByBame(page, limit,name);
            return ServerResponse.success(getlist);
        }

        PageInfo<Book> getlist = bookService.getlist(page, limit);
        return ServerResponse.success(getlist);
    }
    @GetMapping("/get")
    public ServerResponse get(String id){
        Book book = bookService.getOne(id);
        return ServerResponse.success(book);
    }
    @GetMapping("/delete")
    public ServerResponse delete(String id){
        boolean rs = bookService.delete(id);
        return rs?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }*/
}

package com.agro.controller;

import com.agro.pojo.entity.Comments;
import com.agro.pojo.vo.CommentVo;
import com.agro.result.ServerResponse;
import com.agro.service.CommentsService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/save")
    public ServerResponse save(@RequestBody() Comments comments){
        System.out.println("coo "+comments);
        Comments rs = commentsService.insert(comments);
        if (rs!=null){
            return ServerResponse.successMsg("评论成功");
        }
        return ServerResponse.errorMsg("评论失败");
    }

    @GetMapping("/get")
    public ServerResponse get(String productionId,int order){
        List<CommentVo> commentVo = commentsService.selectByPid(productionId,order);
        return ServerResponse.success(commentVo);
    }

    @GetMapping("/getlist")
    public ServerResponse getlist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit,String content){

        if (!StringUtils.isEmpty(content)){
            PageInfo pageInfo = commentsService.selectListByContent(page,limit,content);
            return ServerResponse.success(pageInfo);
        }
        PageInfo pageInfo = commentsService.selectList(page,limit);
        return ServerResponse.success(pageInfo);
    }
    @GetMapping("/delete")
    public ServerResponse delete(String id){
        boolean b = commentsService.deleteById(id);
        return b?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("失败");
    }


}

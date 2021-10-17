package com.agro.service.impl;


import com.agro.mapper.CommentsMapper;
import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.entity.Comments;
import com.agro.pojo.vo.CommentVo;
import com.agro.service.CommentsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (Comments)表服务实现类
 *
 * @author makejava
 * @since 2020-07-25 14:13:08
 */
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {
    @Resource
    private CommentsMapper commentsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Comments queryById(Long id) {
        return this.commentsDao.selectByPrimaryKey(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Comments> queryAllByLimit(int offset, int limit) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param comments 实例对象
     * @return 实例对象
     */
    @Override
    public Comments insert(Comments comments) {
        Sid sid = new Sid();
        String id = sid.nextShort();
        comments.setId(id);
        comments.setCreateTime(new Date());
        comments.setUpdateTime(new Date());
        comments.setLikes(0);
        comments.setDislike(0);
        if (StringUtils.isBlank(comments.getReplyId())) {
            comments.setReplyId("0");
        }
        this.commentsDao.insert(comments);
        return comments;
    }

    /**
     * 修改数据
     *
     * @param comments 实例对象
     * @return 实例对象
     */
    @Override
    public Comments update(Comments comments) {
        this.commentsDao.updateByPrimaryKey(comments);
        return commentsDao.selectByPrimaryKey(comments.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        Example example = new Example(Comments.class);
        example.createCriteria().andEqualTo("id",id);
        return this.commentsDao.deleteByExample(example) > 0;
    }

    @Override
    public List<CommentVo> selectByPid(String productionId, int order) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productionId", productionId);

        if (order > 0) {
            example.setOrderByClause("create_time DESC");
        }
        List<Comments> comments = commentsDao.selectByExample(example);

        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comments comment : comments) {
            if (comment.getReplyId().equals("0")) {
                CommentVo commentVo = getCommentVo(comment);
                commentVoList.add(commentVo);
            }

        }
        for (int i = 0; i < commentVoList.size(); i++) {
            for (Comments comment : comments) {
                if (comment.getReplyId().equals(commentVoList.get(i).getId())) {
                    commentVoList.get(i).setCommentVo(getCommentVo(comment));
                }
            }
        }

        return commentVoList;
    }

    private CommentVo getCommentVo(Comments comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setContent(comment.getContent());
        commentVo.setHeadImg(comment.getHeadImg());
        commentVo.setUserName(comment.getUserName());
        commentVo.setCreateTime(comment.getCreateTime());
        commentVo.setUpdateTime(comment.getUpdateTime());
        commentVo.setLikes(comment.getLikes());
        commentVo.setDislike(comment.getDislike());
        commentVo.setProductionId(comment.getProductionId());
        return commentVo;
    }

    @Override
    public PageInfo selectList(Integer page, Integer limit) {

        Example example = new Example(Comments.class);
        example.setOrderByClause("create_time DESC");
        PageHelper.startPage(page, limit);

        List<Comments> comments = commentsDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(comments);
        return pageInfo;
    }

    @Override
    public PageInfo selectListByContent(Integer page, Integer limit, String content) {
        Example example = new Example(Comments.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orLike("userName","%" +content+"%")
                .orLike("content","%" +content+"%");
        List<Comments> comments = commentsDao.selectByExample(example);
        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(comments);
        return pageInfo;
    }
}
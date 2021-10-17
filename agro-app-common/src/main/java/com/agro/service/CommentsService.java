package com.agro.service;



import com.agro.pojo.entity.Comments;
import com.agro.pojo.vo.CommentVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (Comments)表服务接口
 *
 * @author makejava
 * @since 2020-07-25 14:13:08
 */
public interface CommentsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comments queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Comments> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param comments 实例对象
     * @return 实例对象
     */
    Comments insert(Comments comments);

    /**
     * 修改数据
     *
     * @param comments 实例对象
     * @return 实例对象
     */
    Comments update(Comments comments);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    List<CommentVo> selectByPid(String productionId,int order);

    PageInfo selectList(Integer page, Integer limit);

    PageInfo selectListByContent(Integer page, Integer limit, String content);
}
package com.agro.service;


import com.agro.pojo.dto.ArticleDto;
import com.agro.pojo.entity.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2020-07-25 14:12:47
 */
public interface ArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Article queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Article> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    Article insert(Article article);

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    boolean update(Article article);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    PageInfo getList(Integer page, Integer limit);

    List<ArticleDto> getRecommend();

    ArticleDto selectById(String id);

    PageInfo getNoCheckList(int page, int limit);

    PageInfo getListByName(Integer page, Integer limit, String name);
}
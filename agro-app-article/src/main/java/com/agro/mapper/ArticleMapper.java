package com.agro.mapper;


import com.agro.mymapper.BaseMapper;
import com.agro.pojo.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> selectAlls();
    List<Article> selectAllsByName(@Param("title") String title);

    Article selectByPrimaryKeys(String id);

    @Override
    int insert(Article article);
    int update(Article article);
    /*@Override
    int deleteByPrimaryKey(Object o);

    @Override
    int delete(Article article);



    @Override
    int insertSelective(Article article);

    @Override
    boolean existsWithPrimaryKey(Object o);

    @Override
    List<Article> selectAll();

    @Override
    Article selectByPrimaryKey(Object o);

    @Override
    int selectCount(Article article);

    @Override
    List<Article> select(Article article);

    @Override
    Article selectOne(Article article);

    @Override
    int updateByPrimaryKey(Article article);

    @Override
    int updateByPrimaryKeySelective(Article article);

    @Override
    int deleteByExample(Object o);

    @Override
    List<Article> selectByExample(Object o);

    @Override
    int selectCountByExample(Object o);

    @Override
    Article selectOneByExample(Object o);

    @Override
    int updateByExample(Article article, Object o);

    @Override
    int updateByExampleSelective(Article article, Object o);

    @Override
    List<Article> selectByExampleAndRowBounds(Object o, RowBounds rowBounds);

    @Override
    List<Article> selectByRowBounds(Article article, RowBounds rowBounds);

    @Override
    int insertList(List<? extends Article> list);

    @Override
    int insertUseGeneratedKeys(Article article);*/
}
package com.agro.service.impl;

import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.dto.ArticleDto;
import com.agro.pojo.entity.Article;
import com.agro.pojo.entity.User;
import com.agro.result.Const;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2020-07-25 14:12:47
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleDao;

    @Autowired
    private UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Article queryById(String id) {
        return this.articleDao.selectByPrimaryKey(id);
//        return this.articleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Article> queryAllByLimit(int offset, int limit) {
//        return this.articleDao.queryAllByLimit(offset, limit);
//        List<Article> articleList = articleDao.selectByRowBounds(new Article(), offset, limit);
        return null;
    }

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public Article insert(Article article) {
        Sid sid = new Sid();
        article.setId(sid.nextShort());
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        String labels = article.getLabel().replaceAll(",", " ");
        article.setLabel(labels);

        int rs = articleDao.insert(article);
        if (rs > 0) {
            return article;
        }
        return null;
    }

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Article article) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("id",article.getId());
        article.setUpdateTime(new Date());
        int rs = this.articleDao.update(article);

        return rs > 0 ? true : false;

    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("id",id);
        return this.articleDao.deleteByExample(example) > 0;
    }

    @Override
    public PageInfo getList(Integer page, Integer limit) {
        Example example = new Example(Article.class);
        example.setOrderByClause("create_time DESC");

//        List<Article> articles = articleDao.selectByExample(example);
        List<Article> articles = articleDao.selectAlls();
        PageHelper.startPage(page, limit);

        List<ArticleDto> articleDtoList = new ArrayList<>();

        for (Article article : articles) {
            User user = userService.selectById(article.getUserId());
            ArticleDto articleDto = getArticleDto(article, user);
            articleDtoList.add(articleDto);
        }
        PageInfo pageInfo = new PageInfo(articleDtoList);
        //pageInfo.setList(articles);


        return pageInfo;
    }

    @Override
    public List<ArticleDto> getRecommend() {

        Example example = new Example(Article.class);
        example.setOrderByClause("create_time DESC");

        List<Article> articles = articleDao.selectAlls();
        List<ArticleDto> articleDtoList = new ArrayList<>();

        for (Article article : articles) {
            User user = userService.selectById(article.getUserId());
            ArticleDto articleDto = getArticleDto(article, user);
            articleDtoList.add(articleDto);
        }

        return articleDtoList;
    }

    @Override
    public ArticleDto selectById(String id) {
        Article article = articleDao.selectByPrimaryKeys(id);
        /*Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        Article article = articleDao.selectOneByExample(example);*/

        User user = userService.selectById(article.getUserId());
        ArticleDto articleDto = getArticleDto(article, user);
        return articleDto;
    }

    private ArticleDto getArticleDto(Article article, User user) {

        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setContent(article.getContent());
        articleDto.setCover(article.getCover());
        articleDto.setHeadImg(user.getHeadImg());
        articleDto.setLabel(article.getLabel());
        articleDto.setTitle(article.getTitle());
        articleDto.setUserName(user.getUsername());
        articleDto.setUserId(user.getId());
        articleDto.setDesc(article.getDesc());
        articleDto.setUpdateTime(article.getUpdateTime());
        articleDto.setCreateTime(article.getCreateTime());

        return articleDto;
    }

    @Override
    public PageInfo getNoCheckList(int page, int limit) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", Const.VideStatus.WAITCONFIRM);
        List<Article> list = articleDao.selectByExample(example);
        Page p = PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(p);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public PageInfo getListByName(Integer page, Integer limit, String name) {
        System.out.println(name);

        List<Article> articles = articleDao.selectAllsByName(name);
        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(articles);

        return pageInfo;

    }
}
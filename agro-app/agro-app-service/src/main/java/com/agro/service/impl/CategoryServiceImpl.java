package com.agro.service.impl;


import com.agro.mapper.CategoryMapper;
import com.agro.pojo.entity.Category;
import com.agro.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表服务实现类
 *
 * @author makejava
 * @since 2020-09-22 20:20:29
 */
@Service("CategoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Category queryById(Integer id) {
        return this.categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Category> queryAllByLimit(int offset, int limit) {

        return null;
    }

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public Category insert(Category category) {
        this.categoryMapper.insert(category);
        return category;
    }

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Category category) {
       Example example = new Example(Category.class);
       example.createCriteria().andEqualTo("id",category.getId());
        int i = categoryMapper.updateByExample(category, example);
        return i>0?true:false;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.categoryMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Category add(Category category) {

        int rs = categoryMapper.insert(category);

        return rs > 0 ? category : null;
    }

    @Override
    public List<Category> getList() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> getByName(String name) {
        Example example = new Example(Category.class);
        example.createCriteria()
                .andEqualTo("name", name);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }
}
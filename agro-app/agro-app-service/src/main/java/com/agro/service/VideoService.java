package com.agro.service;


import com.agro.pojo.dto.VideoDto;
import com.agro.pojo.entity.CourseVideo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * (Video)表服务接口
 *
 * @author makejava
 * @since 2020-07-25 14:13:41
 */
public interface VideoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CourseVideo queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CourseVideo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    CourseVideo save(CourseVideo video);

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    CourseVideo update(CourseVideo video);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    PageInfo getNoCheckList(int page, int limit);

    PageInfo getList(Integer page, Integer limit,Integer status);

    VideoDto getById(String id);

    PageInfo getRecommend(Integer page, Integer limit);

    List<CourseVideo> getListById(String userId);
    List<VideoDto> getListByUserId(String userId);

    List<VideoDto> selectByTitleAndCategory(String content);
    List<VideoDto> selectByCategory(String category);

    List<VideoDto> getMembersList(String key, Set members);

    boolean updateStatus(Integer status, String id);

    PageInfo selectByName(Integer page, Integer limit, String name,Integer status);
}
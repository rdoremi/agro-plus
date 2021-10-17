package com.agro.service.impl;

import com.agro.client.UserFeignClient;
import com.agro.mapper.CourseVideoMapper;
import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.dto.VideoDto;
import com.agro.pojo.entity.CourseVideo;
import com.agro.pojo.entity.User;
import com.agro.result.Const;
import com.agro.service.VideoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import netscape.javascript.JSObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * (Video)表服务实现类
 *
 * @author makejava
 * @since 2020-07-25 14:13:41
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Resource
    private CourseVideoMapper videoDao;

    @Autowired
    private UserFeignClient userService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CourseVideo queryById(String id) {
        return this.videoDao.selectByPrimaryKey(id);
    }


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CourseVideo> queryAllByLimit(int offset, int limit) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    public CourseVideo save(CourseVideo video) {
        video.setStatus(Const.VideStatus.WAITCONFIRM);
        Sid sid = new Sid();
        video.setId(sid.nextShort());
        video.setCreateTime(new Date());
        video.setUpdateTime(new Date());
        int rs = this.videoDao.insert(video);
        return rs>0?video:null;
    }

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    public CourseVideo update(CourseVideo video) {
        this.videoDao.updateByPrimaryKey(video);
        return this.queryById(video.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.videoDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public PageInfo getNoCheckList(int page, int limit) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",Const.VideStatus.WAITCONFIRM);
        List<CourseVideo> list = videoDao.selectByExample(example);
        Page p = PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(p);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public PageInfo getList(Integer page, Integer limit,Integer status) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.andEqualTo("status",status);
        Page p = PageHelper.startPage(page, limit);
        List<CourseVideo> list = videoDao.selectByExample(example);
        List<VideoDto> dtoList = getVideoDtoList(list);
        /*for (Video video : list) {
            VideoDto videoDto = new VideoDto();

            BeanUtils.copyProperties(video,videoDto);
            User user = userService.selectById(video.getUserId());
//            videoDto.setVideoUrl(FastDFSClient.getTrackerUrl()+video.getVideoUrl());
            videoDto.setVideoUrl(video.getVideoUrl());
            videoDto.setHeadImg(user.getHeadImg());
            videoDto.setUsername(user.getUsername());
            dtoList.add(videoDto);
        }*/
        PageInfo<VideoDto> pageInfo = new PageInfo(dtoList);
        //pageInfo.setList(dtoList);
        return pageInfo;
    }

    @Override
    public VideoDto getById(String id) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        CourseVideo video = videoDao.selectOneByExample(example);
        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(video,videoDto);
//        User user = userService.selectById(video.getUserId());
        Object json = JSONObject.toJSON(userService.getUserInfo(video.getUserId()).getData());

        User user = JSONObject.toJavaObject((JSON) json, User.class);
        videoDto.setHeadImg(user.getHeadImg());
        videoDto.setUsername(user.getUsername());
//        videoDto.setVideoUrl(FastDFSClient.getTrackerUrl()+video.getVideoUrl());
        videoDto.setVideoUrl(video.getVideoUrl());
        return videoDto;
    }

    @Override
    public PageInfo getRecommend(Integer page, Integer limit) {
        List<CourseVideo> videos = videoDao.selectAll();
        Page pg = PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(videos);
        return pageInfo;
    }

    @Override
    public List<CourseVideo> getListById(String userId) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.andEqualTo("userId",userId);
        List<CourseVideo> videos = videoDao.selectByExample(example);
        return videos;
    }

    @Override
    public List<VideoDto> getListByUserId(String userId) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.andEqualTo("userId",userId);
        List<CourseVideo> videos = videoDao.selectByExample(example);

        List<VideoDto> dtoList = getVideoDtoList(videos);
        return dtoList;
    }

    private List<VideoDto> getVideoDtoList(List<CourseVideo> videos){
        List<VideoDto> dtoList = new ArrayList<>();
        for (CourseVideo video : videos) {
            VideoDto videoDto = new VideoDto();

            BeanUtils.copyProperties(video,videoDto);
//            User user = userService.selectById(video.getUserId());
            Object json = JSONObject.toJSON(userService.getUserInfo(video.getUserId()).getData());

            User user = JSONObject.toJavaObject((JSON) json, User.class);
//            videoDto.setVideoUrl(FastDFSClient.getTrackerUrl()+video.getVideoUrl());
            videoDto.setVideoUrl(video.getVideoUrl());
            videoDto.setHeadImg(user.getHeadImg());
            videoDto.setCollect(false);
            videoDto.setUsername(user.getUsername());
            dtoList.add(videoDto);

        }
        return dtoList;
    }

    @Override
    public List<VideoDto> getMembersList(String key, Set members) {
        List<CourseVideo> videoList = new ArrayList<>();
        for (Object member : members) {
            CourseVideo video = videoDao.selectByPrimaryKey(member);
            videoList.add(video);
        }
        List<VideoDto> videoDtoList = getVideoDtoList(videoList);
        return videoDtoList;
    }

    @Override
    public List<VideoDto> selectByTitleAndCategory(String content) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orLike("label","%" +content+"%")
        .orLike("description","%" +content+"%")
        .orLike("title","%" +content+"%")
        .andEqualTo("status",Const.VideStatus.PASS);

        List<CourseVideo> videos = videoDao.selectByExample(example);
        List<VideoDto> videoDtoList = getVideoDtoList(videos);
        return videoDtoList;
    }

    @Override
    public List<VideoDto> selectByCategory(String category) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orEqualTo("label", category)

                .andEqualTo("status",Const.VideStatus.PASS);

        List<CourseVideo> videos = videoDao.selectByExample(example);
        List<VideoDto> videoDtoList = getVideoDtoList(videos);
        return videoDtoList;
    }

    @Override
    public boolean updateStatus(Integer status, String id) {
        Example example = new Example(CourseVideo.class);
        example.createCriteria().andEqualTo("id",id);
        CourseVideo courseVideo = new CourseVideo();
        courseVideo.setStatus(status);
        courseVideo.setId(id);
        courseVideo.setUpdateTime(new Date());
        int i = videoDao.updateByExampleSelective(courseVideo,example);
        return i>0?true:false;
    }

    @Override
    public PageInfo selectByName(Integer page, Integer limit, String name,Integer status) {
        Example example = new Example(CourseVideo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.andEqualTo("title",name)
//              .andLike("label","%" +name+"%")
                .andEqualTo("status",status);
        List<CourseVideo> courseVideos = videoDao.selectByExample(example);
        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(courseVideos);
        return pageInfo;
    }
}
package com.agro.controller;


import com.agro.pojo.dto.VideoDto;
import com.agro.pojo.entity.CourseVideo;
import com.agro.result.Const;
import com.agro.result.ServerResponse;
import com.agro.service.VideoService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/video")
//@CrossOrigin
public class VideoController {

    private static String VIEWCOUNT = "view_count:";
    private static String COLLECT = "collect:";
    private static String RECENT = "recent:";

    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private VideoService videoService;

//    @Autowired
//    private WebSocket webSocket;
    
    @PostMapping("/add")
    //@CachePut(cacheNames = "video",key = "123")
    @CacheEvict(cacheNames = "video",key = "123")
    public ServerResponse add(@RequestBody CourseVideo video) throws IOException {
        System.out.println(video);

//        webSocket.sendMessage("新视频："+video.getTitle()+" 待审核");
        CourseVideo rs = videoService.save(video);
        return rs==null?ServerResponse.errorMsg("发布失败"):ServerResponse.successMsg("发布成功");
    }



    @GetMapping("/getlist")
    @Cacheable(cacheNames = "video",key = "123")
    public ServerResponse getlist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit){
        System.out.println("获取视频");
        PageInfo list = videoService.getList(page, limit, Const.VideStatus.PASS);
        return ServerResponse.success(list);
    }

    @GetMapping("/get_check_list")
    public ServerResponse getChecklist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam String name){
        if (!StringUtils.isEmpty(name)){
            PageInfo vlist = videoService.selectByName(page,limit,name,Const.VideStatus.WAITCONFIRM);
            return ServerResponse.success(vlist);
        }

        PageInfo list = videoService.getList(page, limit, Const.VideStatus.WAITCONFIRM);
        return ServerResponse.success(list);
    }
    @GetMapping("/get_nopass_list")
    public ServerResponse getNopasslist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam String name){
        if (!StringUtils.isEmpty(name)){
            PageInfo vlist = videoService.selectByName(page,limit,name,Const.VideStatus.NOT_PASS);
            return ServerResponse.success(vlist);
        }
        PageInfo list = videoService.getList(page, limit, Const.VideStatus.NOT_PASS);
        return ServerResponse.success(list);
    }
    @GetMapping("/get_pass_list")
    public ServerResponse getAlllist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,@RequestParam String name){
        if (!StringUtils.isEmpty(name)){
            PageInfo vlist = videoService.selectByName(page,limit,name,Const.VideStatus.PASS);
            return ServerResponse.success(vlist);
        }
        PageInfo list = videoService.getList(page, limit, Const.VideStatus.PASS);
        return ServerResponse.success(list);
    }
    @GetMapping("/get")
    public ServerResponse get( String id,String userId){
        redisTemplate.opsForSet().add(VIEWCOUNT+id,userId);
        String key = COLLECT+userId;
        Boolean member = redisTemplate.opsForSet().isMember(key, id);

        VideoDto videoDto = videoService.getById(id);
        if (member){
            videoDto.setCollect(true);
        }
        Long size = redisTemplate.opsForSet().size(VIEWCOUNT + id);
        videoDto.setViewCount(size);
        return ServerResponse.success(videoDto);
    }

    @GetMapping("/get_one")
    public ServerResponse getOne(String id){

        VideoDto videoDto = videoService.getById(id);
        return ServerResponse.success(videoDto);
    }

    @GetMapping("/get_recommend")
    public ServerResponse getRecommed(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "5") Integer limit){
        PageInfo recommend = videoService.getRecommend(page, limit);
        return ServerResponse.success(recommend);
    }

    @GetMapping("/myvideo")
    public ServerResponse myVideo(String userId){
        List<CourseVideo> videos = videoService.getListById(userId);
        return ServerResponse.success(videos);
    }



    @GetMapping("/search")
    public ServerResponse search(String content){
        List<VideoDto> list = videoService.selectByTitleAndCategory(content);
        return ServerResponse.success(list);
    }
    @GetMapping("/searchcagegroy")
    public ServerResponse searchcagegroy(String content){
        List<VideoDto> list = videoService.selectByCategory(content);
        return ServerResponse.success(list);
    }
    @GetMapping("/collect")
    public ServerResponse collect(String userId,String videoId){
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(videoId)){
            return ServerResponse.error();
        }
        String key = COLLECT+userId;
        Boolean member = redisTemplate.opsForSet().isMember(key,videoId);
        if (member){
            redisTemplate.opsForSet().remove(key,videoId);
            return ServerResponse.success();
        }
        redisTemplate.opsForSet().add(key,videoId);

        return ServerResponse.success();
    }

    @GetMapping("/get_collect")
    public ServerResponse getCollect(String userId){

        String key = COLLECT+userId;
        Set members = redisTemplate.opsForSet().members(key);
        List<VideoDto> list = videoService.getMembersList(key,members);
        for (int i = 0; i < list.size(); i++) {

            list.get(i).setCollect(true);
        }
        return ServerResponse.success(list);
    }

    @GetMapping("/recent")
    public ServerResponse recent(String userId,String videoId){
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(videoId)){
            return ServerResponse.error();
        }
        String key = RECENT+userId;
        redisTemplate.opsForSet().add(key,videoId);

        return ServerResponse.success();
    }

    @GetMapping("/get_recent")
    public ServerResponse getRecent(String userId){

        String key = RECENT+userId;
        Set members = redisTemplate.opsForSet().members(key);
        List<VideoDto> list = videoService.getMembersList(key,members);

        return ServerResponse.success(list);
    }

    @GetMapping("/update_status")
    public ServerResponse updateStatus(Integer status,String id){
      boolean rs =  videoService.updateStatus(status,id);
      return rs?ServerResponse.successMsg("操作成功"):ServerResponse.errorMsg("操作失败");
    }
    @GetMapping("/get_no_check")
    public ServerResponse getNoChecklist(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){
        PageInfo noCheckList = videoService.getNoCheckList(page, limit);
        return ServerResponse.success(noCheckList);
    }




    @GetMapping("/videoInfo")
    ServerResponse getVideoInfo(@RequestParam String productionId){
        CourseVideo video = videoService.queryById(productionId);
        return ServerResponse.success(video);
    }
}

package com.agro.service.impl;

import com.agro.mapper.CollectMapper;
import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.entity.Collect;
import com.agro.pojo.entity.CourseVideo;
import com.agro.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CourseVideoMapper courseVideoMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public boolean add(Collect collect) {

        Example example = new Example(Collect.class);
        example.createCriteria()
                .andEqualTo("userId", collect.getUserId())
                .andEqualTo("productionId", collect.getProductionId());
        Collect ct = collectMapper.selectOneByExample(example);
        if (ct != null) {
            return false;
        }
        Sid sid = new Sid();
        collect.setId(sid.nextShort());
        collect.setCreateTime(new Date());
        int insert = collectMapper.insert(collect);
        return insert > 0 ? true : false;
    }

    @Override
    public List<CourseVideo> selectList(String userId) {

        Example example = new Example(Collect.class);
        example.createCriteria()
                .andEqualTo("userId", userId);
        example.setOrderByClause("create_time DESC");
        List<Collect> collects = collectMapper.selectByExample(example);

        List<CourseVideo> courseVideos = new ArrayList<>();
        for (Collect collect : collects) {
            System.out.println(collect);
            Example e = new Example(CourseVideo.class);
            e.createCriteria()
                    .andEqualTo("id", collect.getProductionId());
//            example.setOrderByClause("create_time DESC");

            CourseVideo video = courseVideoMapper.selectOneByExample(e);
            courseVideos.add(video);
        }

        return courseVideos;
    }

    @Override
    public boolean deleteById(String userId, String productionId) {
        Example example = new Example(Collect.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("productionId", productionId);
        int rs = collectMapper.deleteByExample(example);
        return rs > 0 ? true : false;
    }

    @Override
    public boolean checkCollect(String productionId, String userId) {
        Example example = new Example(Collect.class);
        example.createCriteria().andEqualTo("productionId", productionId)
                .andEqualTo("userId", userId);
        Collect collect = collectMapper.selectOneByExample(example);

        return collect == null ? false : true;
    }
}

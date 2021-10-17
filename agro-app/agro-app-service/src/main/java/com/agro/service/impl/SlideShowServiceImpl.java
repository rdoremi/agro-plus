package com.agro.service.impl;

import com.agro.mapper.SlideshowMapper;
import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.entity.Slideshow;
import com.agro.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class SlideShowServiceImpl implements SlideShowService {

    @Autowired
    private SlideshowMapper slideshowMapper;


    @Override
    public int add(Slideshow slideshow) {
        Sid sid = new Sid();
        slideshow.setId(sid.nextShort());
        slideshow.setCreateTime(new Date());
        int insert = slideshowMapper.insert(slideshow);
        return insert;
    }

    @Override
    public List<Slideshow> selectAll() {
        List<Slideshow> slideshows = slideshowMapper.selectAll();
        return slideshows;
    }

    @Override
    public boolean delete(String id) {
        Example example = new Example(Slideshow.class);
        example.createCriteria().andEqualTo("id",id);
        int i = slideshowMapper.deleteByExample(example);
        return i>0?true:false;
    }
}

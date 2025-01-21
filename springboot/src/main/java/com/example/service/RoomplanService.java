package com.example.service;

import com.example.entity.Roomplan;
import com.example.mapper.RoomplanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoomplanService {

    @Resource
    private RoomplanMapper roomplanMapper;

    public void add(Roomplan roomplan) {
        roomplanMapper.insert(roomplan);
    }

    public void deleteById(Integer id) {
        roomplanMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            roomplanMapper.deleteById(id);
        }
    }

    public void updateById(Roomplan roomplan) {
        roomplanMapper.updateById(roomplan);
    }

    public Roomplan selectById(Integer id) {
        return roomplanMapper.selectById(id);
    }


    public List<Roomplan> selectAll(Roomplan roomplan) {
        return roomplanMapper.selectAll(roomplan);
    }


    public PageInfo<Roomplan> selectPage(Roomplan roomplan, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Roomplan> list = roomplanMapper.selectAll(roomplan);
        return PageInfo.of(list);
    }

}

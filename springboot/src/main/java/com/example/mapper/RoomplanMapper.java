package com.example.mapper;

import com.example.entity.Roomplan;

import java.util.List;

public interface RoomplanMapper {

    int insert(Roomplan roomplan);

    int deleteById(Integer id);

    int updateById(Roomplan roomplan);

    Roomplan selectById(Integer id);

    List<Roomplan> selectAll(Roomplan roomplan);

}

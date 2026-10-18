package com.sky.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.User;

@Mapper 
public interface UserMapper {
    @Select ("""
            select * from user where openid=#{openid}
            """)
    User getByOpenid(String openid);

    
    void insert(User user);

    @Select ("""
            SELECT * FROM user where id = #{id}
            """)
    User getById(Long userId);

    
    Integer countByMap(Map map);
}

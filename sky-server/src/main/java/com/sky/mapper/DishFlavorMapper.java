package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> flavors);

    @Delete("""
            DELETE from dish_flavor where dish_id=#{dishId}
            """)
    void deleteByDishId(Long dishId);

    void deleteBydishIds(List<Long> dishids);

    @Select ("""
            Select * from dish_flavor where dish_id = #{dishId}
            """)
    List<DishFlavor> getByDishId(Long dishId);

}

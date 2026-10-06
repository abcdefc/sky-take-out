package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {

    // 新增菜品和对应的口味
    public void saveWithFlavor(DishDTO dishDTO);

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    public void deleteBath(List<Long> ids);

    public DishVO getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDTO dishDTO);

    public void startOrStop(Integer status, Long id);

    /**
     * 根据分类id查询菜品
     * 
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}

package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.GoodsType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/4 15:05
 */
public interface GoodsTypeMapper {
    // 查询所有的商品分类信息
    List<GoodsType> findGoodsTypeListByPId(Integer pId);

    // 根据 id 查询 商品分类信息
    GoodsType getGoodsTypeById(Integer goodsTypeId);

    // 根据goodsTypeName 查询数据库中是否有数据
    Integer getCount(@Param("goodsTypeName") String goodsTypeName);

    // 添加一条新记录
    Integer addGoodsType(GoodsType goodsType);

    // 根据id删除
    Integer deleteGoodsTypeById(Integer goodsTypeId);

    Integer updateGoodsType(GoodsType type);


    void updateGoodsStatus(GoodsType goodsType);
}

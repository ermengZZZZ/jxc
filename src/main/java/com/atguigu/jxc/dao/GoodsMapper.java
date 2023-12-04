package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/4 13:09
 */
public interface GoodsMapper {
    List<Goods> findGoodsList(@Param("page") Integer page,
                              @Param("rows") Integer rows,
                              @Param("codeOrName") String codeOrName,
                              @Param("goodsTypeId") Integer goodsTypeId);

    Integer getTotal(@Param("codeOrName") String codeOrName,
                     @Param("goodsTypeId") Integer goodsTypeId,
                     @Param("inventoryQuantity") Integer inventoryQuantity); // 0 表示无库存  1表示有库存

    //
    Integer getMaxGoodsCode();

    Goods findGoodsByName(@Param("goodsName") String goodsName);


    Integer addGoods(Goods goods);

    Integer updateGoods(Goods goods);

    Goods findGoodsById(Integer goodsId);

    Integer deleteById(Integer goodsId);

    List<Goods> findInventoryQuantity(@Param("page") Integer page,
                                      @Param("rows")Integer rows,
                                      @Param("codeOrName")String nameOrCode,
                                      @Param("inventoryQuantity")Integer inventoryQuantity); // 1 为有库存 0为无库存

    Integer updateGoodsInventoryQuantity(Goods goods);

    List<Goods> listAlarm();

    // 批量加库存
    void addBatchKuCun(@Param("goodsList") List<Goods> goodsList);

    // 批量减库存
    void subBatchKuCun(@Param("goodsList") List<Goods> goodsList);
}

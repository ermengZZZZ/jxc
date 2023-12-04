package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.OverflowList;
import com.atguigu.jxc.entity.OverflowListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 14:43
 */
public interface OverflowListGoodsMapper {
    // 批量保存报溢单
    Integer saveBatch(@Param("overflowListGoodsList") List<OverflowListGoods> overflowListGoodsList);

    //报溢单商品信息
    List<OverflowListGoods> findListByOverflowListId(Integer overflowListId);
}

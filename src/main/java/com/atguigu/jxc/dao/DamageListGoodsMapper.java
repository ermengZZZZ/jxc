package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.DamageListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 11:55
 */
public interface DamageListGoodsMapper {
    // 批量保存数据
    Integer saveBatch(@Param("damageListGoodsList") List<DamageListGoods> damageListGoodsList);

    // 查询保损数据
    List<DamageListGoods> findListByDamageListId(Integer damageListId);
}

package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 13:07
 */
public interface GoodsService {
    // 查询当前库存
    Map<String,Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId);

    // 保存 | 修改商品
    ServiceVO save(Goods goods);

    // 根据id 删除
    ServiceVO delete(Integer goodsId);

    // 无库存商品列表展示
    Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode);

    //  有库存商品列表展示
    Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode);

    // 添加库存、修改数量或成本价
    ServiceVO saveStock(Goods goods);

    // 库存报警
    Map<String, Object> listAlarm();
    // 批量加库存
    void addBatchKuCun(List<Goods> goodsList);

    // 批量减库存
    void subBatchKuCun(List<Goods> goodsList);
}

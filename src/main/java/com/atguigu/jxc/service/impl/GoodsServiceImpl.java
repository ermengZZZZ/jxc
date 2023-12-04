package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsMapper;
import com.atguigu.jxc.dao.GoodsTypeMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.entity.GoodsType;
import com.atguigu.jxc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 13:07
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    // 查询当前库存
    @Override
    public Map<String, Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId) {
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsMapper.getTotal(codeOrName, goodsTypeId, null);
        page = page == 0 ? 1 : page;
        List<Goods> goodsList = new ArrayList<>();

        if (goodsTypeId != null && goodsTypeId.intValue() != 1) {
            GoodsType goodsType = goodsTypeMapper.getGoodsTypeById(goodsTypeId);
            if (goodsType.getGoodsTypeState() == 0) {
                goodsList = goodsMapper.findGoodsList((page - 1) * rows, rows, codeOrName, goodsTypeId);
            } else {
                List<GoodsType> listByPId = goodsTypeMapper.findGoodsTypeListByPId(goodsTypeId);
                goodsList.addAll(goodsMapper.findGoodsList((page - 1) * rows, rows, codeOrName, goodsTypeId));
                for (GoodsType type : listByPId) {
                    List<Goods> list = goodsMapper.findGoodsList((page - 1) * rows, rows, codeOrName, type.getGoodsTypeId());
                    goodsList.addAll(list);
                }
            }

        } else {
            goodsList = goodsMapper.findGoodsList((page - 1) * rows, rows, codeOrName, null);
        }
        map.put("total", total);
        map.put("rows", goodsList);
        return map;
    }

    // 保存 | 修改商品
    @Override
    public ServiceVO save(Goods goods) {
        if (goods == null) {
            return new ServiceVO(ErrorCode.PARA_TYPE_ERROR_CODE, ErrorCode.PARA_TYPE_ERROR_MESS);
        }
        if (goods.getGoodsId() == null) {
            Goods goodsEx = goodsMapper.findGoodsByName(goods.getGoodsName());
            if (goodsEx != null) {
                return new ServiceVO(ErrorCode.PARA_TYPE_ERROR_CODE, "该商品已存在");
            }

            // 新增
            goods.setGoodsCode(this.getMaxGoodsCode());
            goods.setLastPurchasingPrice(goods.getPurchasingPrice());
            goods.setState(0);
            goods.setInventoryQuantity(0);
            goodsMapper.addGoods(goods);
        } else {
            // 修改
            Goods goodsEx = goodsMapper.findGoodsByName(goods.getGoodsName());
            if (goodsEx != null) {
                return new ServiceVO(ErrorCode.PARA_TYPE_ERROR_CODE, "该商品已存在");
            }
            goodsMapper.updateGoods(goods);
        }
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 根据id删除
    @Override
    public ServiceVO delete(Integer goodsId) {
        // 要判断商品状态,入库、有进货和销售单据的不能删除
        Goods goods = goodsMapper.findGoodsById(goodsId);
        if (goods.getState() == 1) {
            return new ServiceVO(ErrorCode.STORED_ERROR_CODE, ErrorCode.STORED_ERROR_MESS);
        }
        if (goods.getState() == 2) {
            return new ServiceVO(ErrorCode.HAS_FORM_ERROR_CODE, ErrorCode.HAS_FORM_ERROR_MESS);
        }
        // 有报损也不能删除
      /*  if(){

        }*/
        goodsMapper.deleteById(goodsId);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    //无库存商品列表展示
    @Override
    public Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsMapper.getTotal(nameOrCode, null, 2);
        page = page == 0 ? 1 : page;

        List<Goods> list = goodsMapper.findInventoryQuantity((page - 1) * rows, rows, nameOrCode, 2);

        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    // 有库存商品列表展示
    @Override
    public Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsMapper.getTotal(nameOrCode, null, 1);
        page = page == 0 ? 1 : page;

        List<Goods> list = goodsMapper.findInventoryQuantity((page - 1) * rows, rows, nameOrCode, 1);

        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    // 添加库存、修改数量或成本价
    @Override
    public ServiceVO saveStock(Goods goods) {
        if (goods == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        if (goods.getInventoryQuantity() < 0) {
            return new ServiceVO(ErrorCode.PARA_TYPE_ERROR_CODE, ErrorCode.PARA_TYPE_ERROR_MESS);
        }
        if (goods.getInventoryQuantity() == 0) {
            goodsMapper.updateGoodsInventoryQuantity(goods);
        }
        if (goods.getInventoryQuantity() > 0) {
            goods.setState(1);
            goodsMapper.updateGoods(goods);
        }

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 库存报警
    @Override
    public Map<String, Object> listAlarm() {
        List<Goods> list = goodsMapper.listAlarm();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        return map;
    }

    // 批量加库存
    @Override
    public void addBatchKuCun(List<Goods> goodsList) {
        goodsMapper.addBatchKuCun(goodsList);
    }

    // 批量减库存
    @Override
    public void subBatchKuCun(List<Goods> goodsList) {
        goodsMapper.subBatchKuCun(goodsList);
    }

    // 获取 最大 MaxGoods
    private String getMaxGoodsCode() {
        Integer maxGoodsCode = goodsMapper.getMaxGoodsCode();
        if ((maxGoodsCode.intValue() + 1) >= 10 && (maxGoodsCode.intValue() + 1) < 100) {
            return "000" + maxGoodsCode;
        } else if ((maxGoodsCode.intValue() + 1) >= 100 && (maxGoodsCode.intValue() + 1) < 1000) {
            return "0" + maxGoodsCode;
        } else {
            return "" + maxGoodsCode;
        }
    }
}

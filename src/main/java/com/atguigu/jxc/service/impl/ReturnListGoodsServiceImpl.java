package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.ReturnListGoodsMapper;
import com.atguigu.jxc.dao.ReturnListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.service.GoodsService;
import com.atguigu.jxc.service.ReturnListGoodsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 18:58
 */
@Service
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Autowired
    private ReturnListGoodsMapper returnListGoodsMapper;
    @Autowired
    private ReturnListMapper returnListMapper;

    @Autowired
    private GoodsService goodsService;

    // 退货单保存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(ReturnList returnList, String returnListGoodsStr) {
        if (StringUtils.isEmpty(returnList.getReturnNumber()) || StringUtils.isEmpty(returnListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        // 保存退货单
        returnListMapper.addReturnList(returnList);
        Integer returnListId = returnList.getReturnListId();


        // 保存退货商品
        List<Goods> goodsList = new ArrayList<>();
        Gson gson = new Gson();
        ReturnListGoods[] returnListGoodsArr = gson.fromJson(returnListGoodsStr, ReturnListGoods[].class);
        for (ReturnListGoods returnListGoods : returnListGoodsArr) {
            returnListGoods.setReturnListId(returnListId);

            Goods goods = new Goods();
            goods.setState(2);
            goods.setGoodsId(returnListGoods.getGoodsId());
            goods.setInventoryQuantity(returnListGoods.getGoodsNum());
            goodsList.add(goods);
        }
        returnListGoodsMapper.saveList(returnListGoodsArr);

        // 减库存
        goodsService.subBatchKuCun(goodsList);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 退货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    @Override
    public Map<String, Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();
        List<ReturnList> list = returnListMapper.findList(returnNumber, supplierId, state, sTime, eTime);
        map.put("rows", list);
        return map;
    }

    // 退货单商品信息
    @Override
    public Map<String, Object> goodsList(Integer returnListId) {
        Map<String, Object> map = new HashMap<>();
        List<ReturnListGoods> list = returnListGoodsMapper.findList(returnListId);
        map.put("rows", list);
        return map;
    }

    // 删除退货单
    @Override
    public ServiceVO delete(Integer returnListId) {
        // todo 删除没写
        return null;
    }

    @Override
    public ServiceVO updateState(Integer returnListId) {
        if (returnListId == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        returnListMapper.updateState(returnListId);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 退货统计
    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {

        List<Map<String, Object>> list = returnListMapper.count(sTime, eTime, goodsTypeId, codeOrName);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}

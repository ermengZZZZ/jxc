package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.PurchaseListGoodsMapper;
import com.atguigu.jxc.dao.PurchaseListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.service.GoodsService;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zm
 * @Date: 2021/9/5 16:51
 */
@Service
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Autowired
    private PurchaseListGoodsMapper purchaseListGoodsMapper;

    @Autowired
    private PurchaseListMapper purchaseListMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr) {
        if (StringUtils.isEmpty(purchaseList.getPurchaseNumber()) || StringUtils.isEmpty(purchaseListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        // 保存进货单
        purchaseListMapper.addPurchaseList(purchaseList);
        Integer purchaseListId = purchaseList.getPurchaseListId();

        Gson gson = new Gson();
        List<Goods> goodsList = new ArrayList<>();
        PurchaseListGoods[] purchaseListGoodsArr = gson.fromJson(purchaseListGoodsStr, PurchaseListGoods[].class);
        for (PurchaseListGoods purchaseListGoods : purchaseListGoodsArr) {
            purchaseListGoods.setPurchaseListId(purchaseListId);

            Goods goods = new Goods();
            goods.setState(2);
            goods.setGoodsId(purchaseListGoods.getGoodsId());
            goods.setInventoryQuantity(purchaseListGoods.getGoodsNum());
            goodsList.add(goods);

        }
        // 保存进货商品
        purchaseListGoodsMapper.saveButchPurchaseListGoods(purchaseListGoodsArr);

        // 修改库存
        goodsService.addBatchKuCun(goodsList);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 进货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    @Override
    public Map<String, Object> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();
        List<PurchaseList> list = purchaseListMapper.findList(purchaseNumber, supplierId, state, sTime, eTime);
        map.put("rows", list);
        return map;
    }

    // 查询进货单商品信息
    @Override
    public Map<String, Object> goodsList(Integer purchaseListId) {
        Map<String, Object> map = new HashMap<>();
        List<PurchaseListGoods> list = purchaseListGoodsMapper.findList(purchaseListId);
        map.put("rows", list);
        return map;
    }

    // 进货单删除
    @Override
    public ServiceVO delete(Integer purchaseListId) {
        // todo 删除没写
        return null;
    }

    @Override
    public ServiceVO updateState(Integer purchaseListId) {
        if (purchaseListId == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        PurchaseList purchaseList = new PurchaseList();
        purchaseList.setState(1);
        purchaseList.setPurchaseListId(purchaseListId);
        purchaseListMapper.updateState(purchaseList);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 进货状态查询
    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        List<Map<String, Object>> list = purchaseListMapper.count(sTime, eTime, goodsTypeId, codeOrName);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}

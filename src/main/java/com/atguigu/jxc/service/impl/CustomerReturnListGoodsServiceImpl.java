package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerReturnListGoodsMapper;
import com.atguigu.jxc.dao.CustomerReturnListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import com.atguigu.jxc.service.GoodsService;
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
 * @Date: 2021/9/5 22:10
 */
@Service
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

    @Autowired
    private CustomerReturnListGoodsMapper customerReturnListGoodsMapper;

    @Autowired
    private CustomerReturnListMapper customerReturnListMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr) {
        if (StringUtils.isEmpty(customerReturnList.getReturnNumber()) || StringUtils.isEmpty(customerReturnListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }

        customerReturnListMapper.addSaleList(customerReturnList);
        Integer returnListId = customerReturnList.getCustomerReturnListId();

        List<Goods> goodsList = new ArrayList<>();

        Gson gson = new Gson();
        CustomerReturnListGoods[] customerReturnListGoods = gson.fromJson(customerReturnListGoodsStr, CustomerReturnListGoods[].class);
        for (CustomerReturnListGoods returnListGoods : customerReturnListGoods) {
            returnListGoods.setCustomerReturnListId(returnListId);

            Goods goods = new Goods();
            goods.setState(2);
            goods.setGoodsId(returnListGoods.getGoodsId());
            goods.setInventoryQuantity(returnListGoods.getGoodsNum());
            goodsList.add(goods);
        }
        customerReturnListGoodsMapper.saveBatch(customerReturnListGoods);

        goodsService.addBatchKuCun(goodsList);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 客户退货单查询（可查询条件：退货单号、客户、退款状态）
    @Override
    public Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();
        List<CustomerReturnList> list = customerReturnListMapper.fincList(returnNumber, customerId, state, sTime, eTime);
        map.put("rows", list);
        return map;
    }

    // 退货单商品信息
    @Override
    public Map<String, Object> goodsList(Integer customerReturnListId) {
        Map<String, Object> map = new HashMap<>();
        List<CustomerReturnListGoods> list = customerReturnListGoodsMapper.findList(customerReturnListId);
        map.put("rows", list);
        return map;
    }

    // 删除
    @Override
    public ServiceVO delete(Integer customerReturnListId) {
        // todo 删除
        return null;
    }

    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        List<Map<String, Object>> list = customerReturnListMapper.count(sTime, eTime, goodsTypeId, codeOrName);
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}

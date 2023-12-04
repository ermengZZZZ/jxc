package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.OverflowListGoodsMapper;
import com.atguigu.jxc.dao.OverflowListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.DamageList;
import com.atguigu.jxc.entity.OverflowList;
import com.atguigu.jxc.entity.OverflowListGoods;
import com.atguigu.jxc.service.OverflowListGoodsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zm
 * @Date: 2021/9/5 14:42
 */
@Service
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService {

    @Autowired
    private OverflowListGoodsMapper overflowListGoodsMapper;

    @Autowired
    private OverflowListMapper overflowListMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(OverflowList overflowList, String overflowListGoodsStr) {
        if (StringUtils.isEmpty(overflowList.getOverflowNumber()) || StringUtils.isEmpty(overflowListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        overflowListMapper.addOverflowList(overflowList);
        Integer overflowId = overflowList.getOverflowListId();

        Gson gson = new Gson();
        OverflowListGoods[] overflowListGoodsArr = gson.fromJson(overflowListGoodsStr, OverflowListGoods[].class);
        List<OverflowListGoods> overflowListGoodsList = Arrays.stream(overflowListGoodsArr).map((overflowListGoods) -> {
            overflowListGoods.setOverflowListId(overflowId);
            return overflowListGoods;
        }).collect(Collectors.toList());

        overflowListGoodsMapper.saveBatch(overflowListGoodsList);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 报溢单查询
    @Override
    public Map<String, Object> list(String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();
        List<OverflowList> list = overflowListMapper.findList(sTime, eTime);
        map.put("rows", list);
        return map;
    }

    // 报溢单商品信息
    @Override
    public Map<String, Object> goodsList(Integer overflowListId) {
        Map<String, Object> map = new HashMap<>();
        List<OverflowListGoods> list = overflowListGoodsMapper.findListByOverflowListId(overflowListId);
        map.put("rows", list);
        return map;
    }
}

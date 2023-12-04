package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.DamageListGoodsMapper;
import com.atguigu.jxc.dao.DamageListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.DamageList;
import com.atguigu.jxc.entity.DamageListGoods;
import com.atguigu.jxc.service.DamageListGoodsService;
import com.atguigu.jxc.util.DateUtil;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: zm
 * @Date: 2021/9/5 11:54
 */
@Service
public class DamageListGoodsServiceImpl implements DamageListGoodsService {

    @Autowired
    private DamageListGoodsMapper damageListGoodsMapper;

    @Autowired
    private DamageListMapper damageListMapper;

    // 保存报损
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(DamageList damageList, String damageListGoodsStr) {

        if (StringUtils.isEmpty(damageList.getDamageNumber()) || StringUtils.isEmpty(damageListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
         damageListMapper.addDamageList(damageList);
        Integer damageListId = damageList.getDamageListId();

        Gson gson = new Gson();
        DamageListGoods[] damageListGoodsArr = gson.fromJson(damageListGoodsStr, DamageListGoods[].class);
        List<DamageListGoods> damageListGoodsList = Arrays.stream(damageListGoodsArr).map((damageListGoods -> {
            damageListGoods.setDamageListId(damageListId);
            return damageListGoods;
        })).collect(Collectors.toList());

        damageListGoodsMapper.saveBatch(damageListGoodsList);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 查询所有报损单
    @Override
    public Map<String, Object> list(String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();

        List<DamageList> list = damageListMapper.findDamageList(sTime, eTime);

        map.put("rows", list);
        return map;
    }

    // 查询报损单商品信息
    @Override
    public Map<String, Object> goodsList(Integer damageListId) {
        Map<String, Object> map = new HashMap<>();
        List<DamageListGoods> list = damageListGoodsMapper.findListByDamageListId(damageListId);
        map.put("rows", list);
        return map;
    }
}

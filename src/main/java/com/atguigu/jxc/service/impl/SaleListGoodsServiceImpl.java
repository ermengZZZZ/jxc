package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.SaleListGoodsMapper;
import com.atguigu.jxc.dao.SaleListMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.SaleListGoods;
import com.atguigu.jxc.service.GoodsService;
import com.atguigu.jxc.service.SaleListGoodsService;
import com.atguigu.jxc.util.DateUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: zm
 * @Date: 2021/9/5 21:41
 */
@Service
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Autowired
    private SaleListGoodsMapper saleListGoodsMapper;

    @Autowired
    private SaleListMapper saleListMapper;

    @Autowired
    private GoodsService goodsService;

    //销售单保存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceVO save(SaleList saleList, String saleListGoodsStr) {
        if (StringUtils.isEmpty(saleList.getSaleNumber()) || StringUtils.isEmpty(saleListGoodsStr)) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }

        saleListMapper.addSaleList(saleList);
        Integer saleListId = saleList.getSaleListId();

        List<Goods> goodsList = new ArrayList<>();

        Gson gson = new Gson();
        SaleListGoods[] saleListGoodsArr = gson.fromJson(saleListGoodsStr, SaleListGoods[].class);
        for (SaleListGoods saleListGoods : saleListGoodsArr) {
            saleListGoods.setSaleListId(saleListId);

            Goods goods = new Goods();
            goods.setState(2);
            goods.setGoodsId(saleListGoods.getGoodsId());
            goods.setInventoryQuantity(saleListGoods.getGoodsNum());
            goodsList.add(goods);
        }
        saleListGoodsMapper.saveBatch(saleListGoodsArr);

        goodsService.subBatchKuCun(goodsList);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String saleNumber, Integer customerId, Integer state, String sTime, String eTime) {
        Map<String, Object> map = new HashMap<>();
        List<SaleList> list = saleListMapper.findList(saleNumber, customerId, state, sTime, eTime);
        map.put("rows", list);
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer saleListId) {
        Map<String, Object> map = new HashMap<>();
        List<SaleListGoods> list = saleListGoodsMapper.findList(saleListId);
        map.put("rows", list);
        return map;
    }

    @Override
    public ServiceVO delete(Integer saleListId) {
        // todo 删除
        return null;
    }

    @Override
    public ServiceVO updateState(Integer saleListId) {
        if (saleListId == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        saleListMapper.updateState(saleListId);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 销售统计
    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {

        List<Map<String, Object>> list = saleListMapper.count(sTime, eTime, goodsTypeId, codeOrName);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    // 日统计
    @Override
    public String getSaleDataByDay(String sTime, String eTime) {
        List<Map<String, Object>> list = saleListMapper.getSaleDataByDay(sTime, eTime);
        List<Map<String, Object>> arr = this.getMaps(sTime, eTime, list);

        Gson gson = new Gson();
        return gson.toJson(arr);
    }


    // 月统计
    @Override
    public String getSaleDataByMonth(String sTime, String eTime) {
        String start = sTime.substring(0, 6);
        String end = eTime.substring(0, 6);
        List<Map<String, Object>> list = saleListMapper.getSaleDataByMonth(start, end);
        List<Map<String, Object>> maps = this.getMaps(start, eTime, list);
        Gson gson = new Gson();
        return gson.toJson(maps);
    }


//    // 日统计
//    public List<Map<String, Object>> getData(String sTime, String eTime) {
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("saleTotal", 0);
//        map.put("purchasingTotal", 0);
//        map.put("profit", 0);
//        try {
//            List<String> days = DateUtil.getTimeSlotByDay(sTime, eTime);
//            for (String day : days) {
//                // 获取当天的销售数据
//                 saleListMapper.findByDate(date);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    // 月统计
//    public List<Map<String, Object>> getMonth(String sTime, String eTime) {
//
//        return null;
//    }

    private List<Map<String, Object>> getMaps(String sTime, String eTime, List<Map<String, Object>> list) {
        LinkedHashMap<String, Map<String, Object>> hashMap = new LinkedHashMap<>();
        try {
            List<String> days = null;
            if (sTime.length() > 7) {
                days = DateUtil.getTimeSlotByDay(sTime, eTime);
            } else {
                days = DateUtil.getTimeSlotByMonth(sTime, eTime);
            }
            for (String day : days) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", day);
                map.put("saleTotal", 0);
                map.put("purchasingTotal", 0);
                map.put("profit", 0);
                hashMap.put(day,map);
            }
            for (Map<String, Object> m : list) {
                String date = (String) m.get("date");
                hashMap.replace(date,m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(hashMap.values());
    }

//    private List<Map<String, Object>> getMaps(String sTime, String eTime, List<Map<String, Object>> list) {
//        List<Map<String, Object>> arr = new ArrayList<>();
//
//        try {
//            List<String> days = null;
//            if (sTime.length() > 7) {
//                days = DateUtil.getTimeSlotByDay(sTime, eTime);
//            } else {
//                days = DateUtil.getTimeSlotByMonth(sTime, eTime);
//            }
//
//            for (String day : days) {
//                for (Map<String, Object> map : list) {
//                    String date = (String) map.get("date");
//                    if (date.equals(day)) {
//                        arr.add(map);
//                    }
//                }
//                Map<String, Object> map = new HashMap<>();
//                map.put("date", day);
//                map.put("saleTotal", 0);
//                map.put("purchasingTotal", 0);
//                map.put("profit", 0);
//                arr.add(map);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return arr;
//    }
}

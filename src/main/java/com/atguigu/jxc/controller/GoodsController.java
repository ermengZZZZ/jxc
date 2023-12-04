package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 13:03
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    // 查询当前库存
    // http://localhost:8080/goods/listInventory
    @PostMapping("/listInventory")
    public Map<String, Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId) {
        return goodsService.listInventory(page, rows, codeOrName, goodsTypeId);
    }


    // http://localhost:8080/goods/list
    @PostMapping("list") // Integer page, Integer rows, String goodsName, Integer goodsTypeId
    public Map<String, Object> list(Integer page, Integer rows, String goodsName, Integer goodsTypeId) {
        return goodsService.listInventory(page, rows, goodsName, goodsTypeId);
    }

    // 请求URL：http://localhost:8080/goods/save?goodsId=37
    @PostMapping("save")
    public ServiceVO save(Goods goods) {
        return goodsService.save(goods);
    }

    //    http://localhost:8080/goods/delete
    @PostMapping("delete")
    public ServiceVO delete(Integer goodsId) {
        return goodsService.delete(goodsId);
    }

    // 无库存商品列表展示
    //    http://localhost:8080/goods/getNoInventoryQuantity
    @PostMapping("getNoInventoryQuantity")
    public Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        return goodsService.getNoInventoryQuantity(page, rows, nameOrCode);
    }

    //    有库存商品列表展示
    // http://localhost:8080/goods/getHasInventoryQuantity
    @PostMapping("getHasInventoryQuantity")
    public Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        return goodsService.getHasInventoryQuantity(page, rows, nameOrCode);
    }

    // http://localhost:8080/goods/saveStock?goodsId=25
    @PostMapping("saveStock")
    public ServiceVO saveStock(Goods goods) {
        return goodsService.saveStock(goods);
    }

    // http://localhost:8080/goods/deleteStock
    @PostMapping("deleteStock")
    public ServiceVO deleteStock(Integer goodsId) {
        return goodsService.delete(goodsId);
    }

    // 库存报警
//    http://localhost:8080/goods/listAlarm
    @PostMapping("listAlarm")
    public Map<String, Object> listAlarm(){
        return goodsService.listAlarm();
    }
}

package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsTypeMapper;
import com.atguigu.jxc.dao.UnitMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.GoodsType;
import com.atguigu.jxc.entity.Unit;
import com.atguigu.jxc.service.GoodsTypeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zm
 * @Date: 2021/9/4 15:04
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Autowired
    private UnitMapper unitMapper;

    // 查询所有商品类别
    @Override
    public String findAllGoodsTypeList() {

        return this.getAllMenu(-1).toString();

    }

    // 查询所有商品单位
    @Override
    public Map<String, Object> getAll() {
        List<Unit> list = unitMapper.getAll();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        return map;
    }

    // 保存商品类别
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceVO save(GoodsType goodsType) {
        if (goodsType == null) {
            return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "参数错误");
        }

        Integer count = goodsTypeMapper.getCount(goodsType.getGoodsTypeName());
        if (count > 0) {
            return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "已有该分类");
        }
        GoodsType type = goodsTypeMapper.getGoodsTypeById(goodsType.getPId());
        if (type.getGoodsTypeState().intValue() == 0) {
            type.setGoodsTypeState(1);
            goodsTypeMapper.updateGoodsType(type);
        }
        goodsTypeMapper.addGoodsType(goodsType);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 删除商品类别
    @Transactional
    @Override
    public ServiceVO deleteGoodsTypeById(Integer goodsTypeId) {
        // 可以不写  前端进行拦截了
        List<GoodsType> typeList = goodsTypeMapper.findGoodsTypeListByPId(goodsTypeId);
        if (typeList != null && typeList.size() > 0) {
            return new ServiceVO(ErrorCode.GOODS_TYPE_ERROR_CODE, ErrorCode.GOODS_TYPE_ERROR_MESS);
        }

        GoodsType goodsTypeById = goodsTypeMapper.getGoodsTypeById(goodsTypeId);
        Integer pId = goodsTypeById.getPId();
        List<GoodsType> list = goodsTypeMapper.findGoodsTypeListByPId(pId);
        if (list != null && list.size() == 1) {
            GoodsType goodsType = new GoodsType();
            goodsType.setGoodsTypeState(0);
            goodsType.setGoodsTypeId(pId);
            goodsTypeMapper.updateGoodsStatus(goodsType);
        }
        goodsTypeMapper.deleteGoodsTypeById(goodsTypeId);

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }


    /**
     * 递归查询当前角色下的所有菜单
     *
     * @return
     */
    public JsonArray getAllMenu(Integer parentId) {

        JsonArray array = this.getMenuByParentId(parentId);

        for (int i = 0; i < array.size(); i++) {

            JsonObject obj = (JsonObject) array.get(i);

            if (obj.get("state").getAsString().equals("open")) {//如果是叶子节点，不再递归

                continue;
            } else {//如果是根节点，继续递归查询
                obj.add("children", this.getAllMenu(obj.get("id").getAsInt()));
            }
        }
        return array;
    }

    private JsonArray getMenuByParentId(int i) {
        List<GoodsType> goodsList = goodsTypeMapper.findGoodsTypeListByPId(i);
        JsonArray jsonArray = new JsonArray();

        for (GoodsType goodsType : goodsList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", goodsType.getGoodsTypeId());
            jsonObject.addProperty("text", goodsType.getGoodsTypeName());
            jsonObject.addProperty("iconCls", "goods-type");

            if (0 == goodsType.getGoodsTypeState().intValue()) {
                // 叶子节点
                jsonObject.addProperty("state", "open");// 叶子节点
            } else {
                // 非叶子节点
                jsonObject.addProperty("state", "closed"); // 根节点
            }

            JsonObject attributes = new JsonObject(); //扩展属性
            attributes.addProperty("state", goodsType.getGoodsTypeState());
            jsonObject.add("attributes", attributes);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


}

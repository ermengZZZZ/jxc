package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.DamageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 12:04
 */
public interface DamageListMapper {
    // 保存报损单
    Integer addDamageList(DamageList damageList);

    // 查询报损单
    List<DamageList> findDamageList(@Param("sTime") String sTime, @Param("eTime")String eTime);
}

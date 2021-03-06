package com.tcredit.creditHunan.dao;

import com.tcredit.creditHunan.model.AdministrativePunishment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author joey123
 * @since 2018-07-03
 */
public interface AdministrativePunishmentDao extends BaseMapper<AdministrativePunishment> {

    @Select("select * from administrative_punishment order by publish_time desc limit 1")
    AdministrativePunishment selectLatestPublishOne();
}

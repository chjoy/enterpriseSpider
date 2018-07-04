package com.tcredit.creditHunan.service;

import com.tcredit.creditHunan.model.AdministrativePunishment;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author joey123
 * @since 2018-07-03
 */
public interface AdministrativePunishmentService extends IService<AdministrativePunishment> {

    Date selectLatestPublishDate();
}

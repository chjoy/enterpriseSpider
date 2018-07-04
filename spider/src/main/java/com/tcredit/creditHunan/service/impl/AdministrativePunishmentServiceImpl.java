package com.tcredit.creditHunan.service.impl;

import com.tcredit.creditHunan.model.AdministrativePunishment;
import com.tcredit.creditHunan.dao.AdministrativePunishmentDao;
import com.tcredit.creditHunan.service.AdministrativePunishmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author joey123
 * @since 2018-07-03
 */
@Service
public class AdministrativePunishmentServiceImpl extends ServiceImpl<AdministrativePunishmentDao, AdministrativePunishment> implements AdministrativePunishmentService {
    @Resource
    private AdministrativePunishmentDao administrativePunishmentDao;

    public Date selectLatestPublishDate() {
        AdministrativePunishment administrativePunishment = administrativePunishmentDao.selectLatestPublishOne();
        if (administrativePunishment==null) return null;
        return administrativePunishment.getPublishTime();
    }
}

package com.tcredit.creditHunan.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.Date;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author joey123
 * @since 2018-07-04
 */
@TableName("administrative_punishment")
public class AdministrativePunishment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 案件名称
     */
    private String title;
    /**
     * 发布日期（字符串）
     */
    @TableField("publish_time_str")
    private String publishTimeStr;
    /**
     * 发布日期
     */
    @TableField("publish_time")
    private Date publishTime;
    /**
     * 行政处罚决定书文号
     */
    @TableField("punish_no")
    private String punishNo;
    /**
     * 行政相对人名称
     */
    private String subject;
    /**
     * 法定代表人
     */
    @TableField("legal_representative")
    private String legalRepresentative;
    /**
     * 执法部门
     */
    @TableField("legal_operation_department")
    private String legalOperationDepartment;
    /**
     * 作出行政处罚的日期（字符串）
     */
    @TableField("punish_time_str")
    private String punishTimeStr;
    /**
     * 作出行政处罚的日期
     */
    @TableField("punish_time")
    private Date punishTime;
    /**
     * 数据创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 数据是否有效（删除）0 无效（已删除） 1有效（未删除）
     */
    @TableField("data_state")
    private Integer dataState;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTimeStr() {
        return publishTimeStr;
    }

    public void setPublishTimeStr(String publishTimeStr) {
        this.publishTimeStr = publishTimeStr;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPunishNo() {
        return punishNo;
    }

    public void setPunishNo(String punishNo) {
        this.punishNo = punishNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalOperationDepartment() {
        return legalOperationDepartment;
    }

    public void setLegalOperationDepartment(String legalOperationDepartment) {
        this.legalOperationDepartment = legalOperationDepartment;
    }

    public String getPunishTimeStr() {
        return punishTimeStr;
    }

    public void setPunishTimeStr(String punishTimeStr) {
        this.punishTimeStr = punishTimeStr;
    }

    public Date getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(Date punishTime) {
        this.punishTime = punishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDataState() {
        return dataState;
    }

    public void setDataState(Integer dataState) {
        this.dataState = dataState;
    }

    @Override
    public String toString() {
        return "AdministrativePunishment{" +
                "id=" + id +
                ", title=" + title +
                ", publishTimeStr=" + publishTimeStr +
                ", publishTime=" + publishTime +
                ", punishNo=" + punishNo +
                ", subject=" + subject +
                ", legalRepresentative=" + legalRepresentative +
                ", legalOperationDepartment=" + legalOperationDepartment +
                ", punishTimeStr=" + punishTimeStr +
                ", punishTime=" + punishTime +
                ", createTime=" + createTime +
                ", dataState=" + dataState +
                "}";
    }
}

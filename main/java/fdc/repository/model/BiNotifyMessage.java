package fdc.repository.model;

import java.util.Date;

public class BiNotifyMessage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.PK_ID
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String pkId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.OPCODE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String opcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.NOTIFYDATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Date notifydate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEB
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Date getTimeb;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Date getTimee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String getFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Integer getNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_MSG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String getMsg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.REMARK
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_APP
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String originApp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Long lastUpdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Date lastUpdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.MODIFICATION_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private Integer modificationNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.DELETED_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String deletedFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    private String originFlag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.PK_ID
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.PK_ID
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getPkId() {
        return pkId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.PK_ID
     *
     * @param pkId the value for BRZFDC.BI_NOTIFY_MESSAGE.PK_ID
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setPkId(String pkId) {
        this.pkId = pkId == null ? null : pkId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.OPCODE
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.OPCODE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.OPCODE
     *
     * @param opcode the value for BRZFDC.BI_NOTIFY_MESSAGE.OPCODE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode == null ? null : opcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.NOTIFYDATE
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.NOTIFYDATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Date getNotifydate() {
        return notifydate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.NOTIFYDATE
     *
     * @param notifydate the value for BRZFDC.BI_NOTIFY_MESSAGE.NOTIFYDATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setNotifydate(Date notifydate) {
        this.notifydate = notifydate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEB
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEB
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Date getGetTimeb() {
        return getTimeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEB
     *
     * @param getTimeb the value for BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEB
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setGetTimeb(Date getTimeb) {
        this.getTimeb = getTimeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEE
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Date getGetTimee() {
        return getTimee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEE
     *
     * @param getTimee the value for BRZFDC.BI_NOTIFY_MESSAGE.GET_TIMEE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setGetTimee(Date getTimee) {
        this.getTimee = getTimee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_FLAG
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.GET_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getGetFlag() {
        return getFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_FLAG
     *
     * @param getFlag the value for BRZFDC.BI_NOTIFY_MESSAGE.GET_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setGetFlag(String getFlag) {
        this.getFlag = getFlag == null ? null : getFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_NUM
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.GET_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Integer getGetNum() {
        return getNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_NUM
     *
     * @param getNum the value for BRZFDC.BI_NOTIFY_MESSAGE.GET_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setGetNum(Integer getNum) {
        this.getNum = getNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_MSG
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.GET_MSG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getGetMsg() {
        return getMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.GET_MSG
     *
     * @param getMsg the value for BRZFDC.BI_NOTIFY_MESSAGE.GET_MSG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setGetMsg(String getMsg) {
        this.getMsg = getMsg == null ? null : getMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.REMARK
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.REMARK
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.REMARK
     *
     * @param remark the value for BRZFDC.BI_NOTIFY_MESSAGE.REMARK
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_APP
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_APP
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getOriginApp() {
        return originApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_APP
     *
     * @param originApp the value for BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_APP
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setOriginApp(String originApp) {
        this.originApp = originApp == null ? null : originApp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_BY
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.CREATED_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_BY
     *
     * @param createdBy the value for BRZFDC.BI_NOTIFY_MESSAGE.CREATED_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_DATE
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.CREATED_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.CREATED_DATE
     *
     * @param createdDate the value for BRZFDC.BI_NOTIFY_MESSAGE.CREATED_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_BY
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Long getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_BY
     *
     * @param lastUpdBy the value for BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_BY
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setLastUpdBy(Long lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_DATE
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Date getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_DATE
     *
     * @param lastUpdDate the value for BRZFDC.BI_NOTIFY_MESSAGE.LAST_UPD_DATE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setLastUpdDate(Date lastUpdDate) {
        this.lastUpdDate = lastUpdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.MODIFICATION_NUM
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.MODIFICATION_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public Integer getModificationNum() {
        return modificationNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.MODIFICATION_NUM
     *
     * @param modificationNum the value for BRZFDC.BI_NOTIFY_MESSAGE.MODIFICATION_NUM
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setModificationNum(Integer modificationNum) {
        this.modificationNum = modificationNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.DELETED_FLAG
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.DELETED_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getDeletedFlag() {
        return deletedFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.DELETED_FLAG
     *
     * @param deletedFlag the value for BRZFDC.BI_NOTIFY_MESSAGE.DELETED_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_FLAG
     *
     * @return the value of BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public String getOriginFlag() {
        return originFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_FLAG
     *
     * @param originFlag the value for BRZFDC.BI_NOTIFY_MESSAGE.ORIGIN_FLAG
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    public void setOriginFlag(String originFlag) {
        this.originFlag = originFlag == null ? null : originFlag.trim();
    }
}
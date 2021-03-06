package fdc.repository.dao;

import fdc.repository.model.BiNotifyConf;
import fdc.repository.model.BiNotifyConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BiNotifyConfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int countByExample(BiNotifyConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByExample(BiNotifyConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insert(BiNotifyConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insertSelective(BiNotifyConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    List<BiNotifyConf> selectByExample(BiNotifyConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    BiNotifyConf selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyConf record, @Param("example") BiNotifyConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyConf record, @Param("example") BiNotifyConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_CONF
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKey(BiNotifyConf record);
}
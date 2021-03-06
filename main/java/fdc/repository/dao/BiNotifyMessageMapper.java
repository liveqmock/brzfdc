package fdc.repository.dao;

import fdc.repository.model.BiNotifyMessage;
import fdc.repository.model.BiNotifyMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BiNotifyMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int countByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insert(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insertSelective(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    List<BiNotifyMessage> selectByExample(BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    BiNotifyMessage selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiNotifyMessage record, @Param("example") BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExample(@Param("record") BiNotifyMessage record, @Param("example") BiNotifyMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKeySelective(BiNotifyMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_NOTIFY_MESSAGE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKey(BiNotifyMessage record);
}
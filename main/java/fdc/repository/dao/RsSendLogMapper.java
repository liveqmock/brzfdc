package fdc.repository.dao;

import fdc.repository.model.RsSendLog;
import fdc.repository.model.RsSendLogExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsSendLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int countByExample(RsSendLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int deleteByExample(RsSendLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int insert(RsSendLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int insertSelective(RsSendLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    List<RsSendLog> selectByExample(RsSendLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    RsSendLog selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int updateByExampleSelective(@Param("record") RsSendLog record, @Param("example") RsSendLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int updateByExample(@Param("record") RsSendLog record, @Param("example") RsSendLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int updateByPrimaryKeySelective(RsSendLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_SEND_LOG
     *
     * @mbggenerated Mon Apr 30 15:52:14 CST 2012
     */
    int updateByPrimaryKey(RsSendLog record);
}
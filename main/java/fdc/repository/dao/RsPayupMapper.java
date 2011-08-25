package fdc.repository.dao;

import fdc.repository.model.RsPayup;
import fdc.repository.model.RsPayupExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RsPayupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int countByExample(RsPayupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int deleteByExample(RsPayupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int insert(RsPayup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int insertSelective(RsPayup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    List<RsPayup> selectByExample(RsPayupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    RsPayup selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsPayup record, @Param("example") RsPayupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByExample(@Param("record") RsPayup record, @Param("example") RsPayupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByPrimaryKeySelective(RsPayup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYUP
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByPrimaryKey(RsPayup record);
}
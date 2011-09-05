package fdc.repository.dao;

import fdc.repository.model.RsLockedaccDetail;
import fdc.repository.model.RsLockedaccDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RsLockedaccDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int countByExample(RsLockedaccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int deleteByExample(RsLockedaccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int insert(RsLockedaccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int insertSelective(RsLockedaccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    List<RsLockedaccDetail> selectByExample(RsLockedaccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    RsLockedaccDetail selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsLockedaccDetail record, @Param("example") RsLockedaccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int updateByExample(@Param("record") RsLockedaccDetail record, @Param("example") RsLockedaccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int updateByPrimaryKeySelective(RsLockedaccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_LOCKEDACC_DETAIL
     *
     * @mbggenerated Mon Sep 05 15:31:05 CST 2011
     */
    int updateByPrimaryKey(RsLockedaccDetail record);
}
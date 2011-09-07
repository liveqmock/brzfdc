package fdc.repository.dao;

import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPayoutExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RsPayoutMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int countByExample(RsPayoutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int deleteByExample(RsPayoutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int insert(RsPayout record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int insertSelective(RsPayout record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    List<RsPayout> selectByExample(RsPayoutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    RsPayout selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsPayout record, @Param("example") RsPayoutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByExample(@Param("record") RsPayout record, @Param("example") RsPayoutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByPrimaryKeySelective(RsPayout record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_PAYOUT
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByPrimaryKey(RsPayout record);
}
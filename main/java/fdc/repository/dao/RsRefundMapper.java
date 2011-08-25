package fdc.repository.dao;

import fdc.repository.model.RsRefund;
import fdc.repository.model.RsRefundExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RsRefundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int countByExample(RsRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int deleteByExample(RsRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int insert(RsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int insertSelective(RsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    List<RsRefund> selectByExample(RsRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    RsRefund selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsRefund record, @Param("example") RsRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByExample(@Param("record") RsRefund record, @Param("example") RsRefundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByPrimaryKeySelective(RsRefund record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_REFUND
     *
     * @mbggenerated Thu Aug 25 21:20:36 CST 2011
     */
    int updateByPrimaryKey(RsRefund record);
}
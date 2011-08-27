package fdc.repository.dao;

import fdc.repository.model.BiTrade;
import fdc.repository.model.BiTradeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiTradeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int countByExample(BiTradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int deleteByExample(BiTradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int insert(BiTrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int insertSelective(BiTrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    List<BiTrade> selectByExample(BiTradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    BiTrade selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiTrade record, @Param("example") BiTradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int updateByExample(@Param("record") BiTrade record, @Param("example") BiTradeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int updateByPrimaryKeySelective(BiTrade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_TRADE
     *
     * @mbggenerated Sat Aug 27 14:15:07 CST 2011
     */
    int updateByPrimaryKey(BiTrade record);
}
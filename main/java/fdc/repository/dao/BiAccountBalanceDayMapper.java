package fdc.repository.dao;

import fdc.repository.model.BiAccountBalanceDay;
import fdc.repository.model.BiAccountBalanceDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BiAccountBalanceDayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int countByExample(BiAccountBalanceDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByExample(BiAccountBalanceDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insert(BiAccountBalanceDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insertSelective(BiAccountBalanceDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    List<BiAccountBalanceDay> selectByExample(BiAccountBalanceDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    BiAccountBalanceDay selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiAccountBalanceDay record, @Param("example") BiAccountBalanceDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExample(@Param("record") BiAccountBalanceDay record, @Param("example") BiAccountBalanceDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKeySelective(BiAccountBalanceDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE_DAY
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKey(BiAccountBalanceDay record);
}
package fdc.repository.dao;

import fdc.repository.model.BiAccountBalance;
import fdc.repository.model.BiAccountBalanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BiAccountBalanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int countByExample(BiAccountBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByExample(BiAccountBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insert(BiAccountBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insertSelective(BiAccountBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    List<BiAccountBalance> selectByExample(BiAccountBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    BiAccountBalance selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiAccountBalance record, @Param("example") BiAccountBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExample(@Param("record") BiAccountBalance record, @Param("example") BiAccountBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKeySelective(BiAccountBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_ACCOUNT_BALANCE
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKey(BiAccountBalance record);
}
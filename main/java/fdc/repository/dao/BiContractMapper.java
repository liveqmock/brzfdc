package fdc.repository.dao;

import fdc.repository.model.BiContract;
import fdc.repository.model.BiContractExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BiContractMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int countByExample(BiContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByExample(BiContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insert(BiContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int insertSelective(BiContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    List<BiContract> selectByExample(BiContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    BiContract selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiContract record, @Param("example") BiContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByExample(@Param("record") BiContract record, @Param("example") BiContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKeySelective(BiContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT
     *
     * @mbggenerated Wed Sep 07 18:28:59 CST 2011
     */
    int updateByPrimaryKey(BiContract record);
}
package fdc.repository.dao;

import fdc.repository.model.RsContract;
import fdc.repository.model.RsContractExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RsContractMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int countByExample(RsContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int deleteByExample(RsContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int insert(RsContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int insertSelective(RsContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    List<RsContract> selectByExample(RsContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    RsContract selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsContract record, @Param("example") RsContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int updateByExample(@Param("record") RsContract record, @Param("example") RsContractExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int updateByPrimaryKeySelective(RsContract record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_CONTRACT
     *
     * @mbggenerated Thu Aug 25 21:29:56 CST 2011
     */
    int updateByPrimaryKey(RsContract record);
}
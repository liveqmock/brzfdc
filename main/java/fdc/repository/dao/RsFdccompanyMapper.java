package fdc.repository.dao;

import fdc.repository.model.RsFdccompany;
import fdc.repository.model.RsFdccompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RsFdccompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int countByExample(RsFdccompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int deleteByExample(RsFdccompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int insert(RsFdccompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int insertSelective(RsFdccompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    List<RsFdccompany> selectByExample(RsFdccompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    RsFdccompany selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsFdccompany record, @Param("example") RsFdccompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByExample(@Param("record") RsFdccompany record, @Param("example") RsFdccompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByPrimaryKeySelective(RsFdccompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.RS_FDCCOMPANY
     *
     * @mbggenerated Wed Sep 07 18:29:53 CST 2011
     */
    int updateByPrimaryKey(RsFdccompany record);
}
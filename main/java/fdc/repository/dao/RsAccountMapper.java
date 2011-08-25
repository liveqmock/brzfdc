package fdc.repository.dao;

import fdc.repository.model.RsAccount;
import fdc.repository.model.RsAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface RsAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int countByExample(RsAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int deleteByExample(RsAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int insert(RsAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int insertSelective(RsAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    List<RsAccount> selectByExample(RsAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    RsAccount selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsAccount record, @Param("example") RsAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByExample(@Param("record") RsAccount record, @Param("example") RsAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByPrimaryKeySelective(RsAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACCOUNT
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByPrimaryKey(RsAccount record);
}
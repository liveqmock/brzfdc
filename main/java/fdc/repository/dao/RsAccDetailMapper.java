package fdc.repository.dao;

import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface RsAccDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int countByExample(RsAccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int deleteByExample(RsAccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int insert(RsAccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int insertSelective(RsAccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    List<RsAccDetail> selectByExample(RsAccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    RsAccDetail selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByExampleSelective(@Param("record") RsAccDetail record, @Param("example") RsAccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByExample(@Param("record") RsAccDetail record, @Param("example") RsAccDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByPrimaryKeySelective(RsAccDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RS_ACC_DETAIL
     *
     * @mbggenerated Wed Aug 24 09:49:51 CST 2011
     */
    int updateByPrimaryKey(RsAccDetail record);
}
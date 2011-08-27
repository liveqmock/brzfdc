package fdc.repository.dao;

import fdc.repository.model.BiPlanDetail;
import fdc.repository.model.BiPlanDetailExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiPlanDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int countByExample(BiPlanDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int deleteByExample(BiPlanDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int insert(BiPlanDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int insertSelective(BiPlanDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    List<BiPlanDetail> selectByExample(BiPlanDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    BiPlanDetail selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiPlanDetail record, @Param("example") BiPlanDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int updateByExample(@Param("record") BiPlanDetail record, @Param("example") BiPlanDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int updateByPrimaryKeySelective(BiPlanDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN_DETAIL
     *
     * @mbggenerated Sat Aug 27 13:53:17 CST 2011
     */
    int updateByPrimaryKey(BiPlanDetail record);
}
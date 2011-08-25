package fdc.repository.dao;

import fdc.repository.model.BiPlan;
import fdc.repository.model.BiPlanExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BiPlanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int countByExample(BiPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int deleteByExample(BiPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int insert(BiPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int insertSelective(BiPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    List<BiPlan> selectByExample(BiPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    BiPlan selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiPlan record, @Param("example") BiPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int updateByExample(@Param("record") BiPlan record, @Param("example") BiPlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int updateByPrimaryKeySelective(BiPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_PLAN
     *
     * @mbggenerated Thu Aug 25 21:15:56 CST 2011
     */
    int updateByPrimaryKey(BiPlan record);
}
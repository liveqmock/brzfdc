package platform.repository.dao;

import platform.repository.model.Pterror;
import platform.repository.model.PterrorExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface PterrorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int countByExample(PterrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int deleteByExample(PterrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int insert(Pterror record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int insertSelective(Pterror record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    List<Pterror> selectByExample(PterrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int updateByExampleSelective(@Param("record") Pterror record, @Param("example") PterrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTERROR
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    int updateByExample(@Param("record") Pterror record, @Param("example") PterrorExample example);
}
package fdc.repository.dao;

import fdc.repository.model.BiContractClose;
import fdc.repository.model.BiContractCloseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface BiContractCloseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int countByExample(BiContractCloseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int deleteByExample(BiContractCloseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int deleteByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int insert(BiContractClose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int insertSelective(BiContractClose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    List<BiContractClose> selectByExample(BiContractCloseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    BiContractClose selectByPrimaryKey(String pkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int updateByExampleSelective(@Param("record") BiContractClose record, @Param("example") BiContractCloseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int updateByExample(@Param("record") BiContractClose record, @Param("example") BiContractCloseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int updateByPrimaryKeySelective(BiContractClose record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BRZFDC.BI_CONTRACT_CLOSE
     *
     * @mbggenerated Fri Aug 26 14:17:47 CST 2011
     */
    int updateByPrimaryKey(BiContractClose record);
}
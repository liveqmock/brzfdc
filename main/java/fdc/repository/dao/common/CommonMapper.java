package fdc.repository.dao.common;

import fdc.repository.model.RsContract;
import fdc.repository.model.RsPayout;
import fdc.view.payout.ParamPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-27
 * Time: ÏÂÎç2:12
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface CommonMapper {

    @Select("select nvl(max(company_id)+1,'100001') from rs_fdccompany")
    String selectNewCompanyId();

    List<RsPayout> selectRsPayoutsByParamPlan(ParamPlan paramPlan);

    @Select("select nvl(max(SERIAL)+1,'100001') from rs_payout")
    String selectMaxPayoutSerial();

    @Select("select nvl(max(SERIAL)+1,'100001') from rs_receive")
    String selectMaxRecvSerial();

     @Select("select nvl(max(LOCAL_SERIAL)+1,'100001') from rs_acc_detail")
    String selectMaxAccDetailSerial();

    @Select("select nvl(max(SERIAL)+1,'100001') from rs_refund")
    String selectMaxRefundSerial();

    @Select("select sum(t.pl_amount) sumplamount from rs_refund t where t.deleted_flag = '0' group by t.business_no")
    BigDecimal selectSumPlamount();

    @Select("select sum(t.pl_amount) sumplamount from rs_refund t where t.deleted_flag = '0' and pk_id <> #{pkid} group by t.business_no")
    BigDecimal selectSumPlamountExceptPkid(@Param("pkid")String pkid);
}

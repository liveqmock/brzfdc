package fdc.repository.dao.common;

import fdc.repository.model.RsPayout;
import fdc.view.payout.ParamPlan;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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
}

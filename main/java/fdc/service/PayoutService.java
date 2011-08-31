package fdc.service;

import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.dao.RsPayoutMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPayoutExample;
import fdc.view.payout.ParamPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PayoutService {
    @Autowired
    private RsPayoutMapper rsPayoutMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Transactional
    public int insertRsPayout(RsPayout rsPayout) {
        OperatorManager om = SystemService.getOperatorManager();
        rsPayout.setApplyUserId(om.getOperatorId());
        rsPayout.setApplyUserName(om.getOperatorName());
        rsPayout.setApplyDate(new Date());
        rsPayout.setCreatedBy(om.getOperatorId());
        rsPayout.setCreatedDate(new Date());
        return rsPayoutMapper.insertSelective(rsPayout);
    }

    @Transactional
    public int updateRsPayoutsToWorkResult(RsPayout[] rsPayoutList, String workResult) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        Date operDate = new Date();
        int rtnFlag = 1;
        for (RsPayout rsPayout : rsPayoutList) {
            rsPayout.setAuditDate(operDate);
            rsPayout.setAuditUserId(operId);
            rsPayout.setAuditUserName(operName);
            rsPayout.setLastUpdBy(operId);
            rsPayout.setLastUpdDate(operDate);
            rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
            rsPayout.setWorkResult(workResult);
            if (rsPayoutMapper.updateByPrimaryKey(rsPayout) != 1) {
                rtnFlag = -1;
                throw new RuntimeException("【记录更新失败】付款监管账号：" + rsPayout.getPayAccount());
            }
        }
        return rtnFlag;
    }

    // TODO 入账
    @Transactional
    public int updateRsPayoutToExecStatus(RsPayout rsPayout) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        Date operDate = new Date();

        rsPayout.setLastUpdBy(operId);
        rsPayout.setLastUpdDate(operDate);
        rsPayout.setExecUserId(operId);
        rsPayout.setExecUserName(operName);
        rsPayout.setExecDate(operDate);
        rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
        rsPayout.setStatusFlag(RefundStatus.ACCOUNT_SUCCESS.getCode());
        rsPayout.setWorkResult(WorkResult.COMMIT.getCode());
        rsPayout.setSerial(SystemService.getSdfdate8() + commonMapper.selectMaxPayoutSerial());
        // TODO 银行流水 接口2004用
        return rsPayoutMapper.updateByPrimaryKey(rsPayout);
        // 更新statusFlag之后
    }

    public List<RsPayout> selectRecordsByWorkResult(String workResultCode) {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andWorkResultEqualTo(workResultCode);
        return rsPayoutMapper.selectByExample(example);
    }

    public List<RsPayout> selectRsPayoutsByParamPlan(ParamPlan paramPlan) {
        return commonMapper.selectRsPayoutsByParamPlan(paramPlan);
    }

    @Transactional
    public int updateRsPayoutSent(RsPayout rsPayout) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        Date operDate = new Date();

        rsPayout.setLastUpdBy(operId);
        rsPayout.setLastUpdDate(operDate);
         rsPayout.setWorkResult(WorkResult.SENT.getCode());
        rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
         return rsPayoutMapper.updateByPrimaryKey(rsPayout);
    }
}

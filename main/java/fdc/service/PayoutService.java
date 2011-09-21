package fdc.service;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;
import fdc.common.constant.RefundStatus;
import fdc.common.constant.WorkResult;
import fdc.repository.dao.RsPayoutMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPayoutExample;
import fdc.repository.model.RsPlanCtrl;
import fdc.service.expensesplan.ExpensesPlanService;
import fdc.view.payout.ParamPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-30
 * Time: ����3:46
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PayoutService {
    @Autowired
    private RsPayoutMapper rsPayoutMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private ExpensesPlanService expensesPlanService;
    @Autowired
    private TradeService tradeService;
    private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    public RsPayout selectPayoutByPkid(String pkid) {
        return rsPayoutMapper.selectByPrimaryKey(pkid);
    }

    public boolean isHasUnSend() {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andWorkResultEqualTo(WorkResult.COMMIT.getCode());
        if (rsPayoutMapper.countByExample(example) > 0) {
            return true;
        }
        return false;
    }

    public int updateRsPayout(RsPayout rsPayout) {
        RsPayout originRecord = rsPayoutMapper.selectByPrimaryKey(rsPayout.getPkId());
        if (!originRecord.getModificationNum().equals(rsPayout.getModificationNum())) {
            throw new RuntimeException("��¼�������³�ͻ�������ԣ�");
        } else {
            OperatorManager om = SystemService.getOperatorManager();
            String operId = om.getOperatorId();
            rsPayout.setLastUpdBy(operId);
            rsPayout.setLastUpdDate(new Date());
            rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
            return rsPayoutMapper.updateByPrimaryKey(rsPayout);
        }
    }

    /**
     * @param rsPayout
     * @return
     */
    public int insertRsPayout(RsPayout rsPayout) {
        OperatorManager om = SystemService.getOperatorManager();
        rsPayout.setApplyUserId(om.getOperatorId());
        rsPayout.setApplyUserName(om.getOperatorName());
        rsPayout.setApplyDate(sdf10.format(new Date()));
        rsPayout.setCreatedBy(om.getOperatorId());
        rsPayout.setCreatedDate(new Date());
        return rsPayoutMapper.insertSelective(rsPayout);
    }

    @Transactional
    public int updateRsPayoutsToWorkResult(RsPayout[] rsPayoutList, String workResult) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        String operDate = sdf10.format(new Date());
        int rtnFlag = 1;
        for (RsPayout rsPayout : rsPayoutList) {
            rsPayout.setAuditDate(operDate);
            rsPayout.setAuditUserId(operId);
            rsPayout.setAuditUserName(operName);
            rsPayout.setWorkResult(workResult);
            if (updateRsPayout(rsPayout) != 1) {
                rtnFlag = -1;
                throw new RuntimeException("����¼����ʧ�ܡ��������˺ţ�" + rsPayout.getPayAccount());
            }
        }
        return rtnFlag;
    }

    @Transactional
    public int updateRsPayoutToExec(RsPayout rsPayout) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        String operDate = sdf10.format(new Date());
        rsPayout.setExecUserId(operId);
        rsPayout.setExecUserName(operName);
        rsPayout.setExecDate(operDate);
        rsPayout.setStatusFlag(RefundStatus.ACCOUNT_SUCCESS.getCode());
        rsPayout.setWorkResult(WorkResult.COMMIT.getCode());
        rsPayout.setSerial(SystemService.getSdfdate8() + commonMapper.selectMaxPayoutSerial());
        rsPayout.setBankSerial(rsPayout.getSerial());
        return tradeService.handlePayoutTrade(rsPayout) + updateRsPayout(rsPayout);
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
        rsPayout.setWorkResult(WorkResult.SENT.getCode());
        return updateRsPayout(rsPayout);
    }

    public RsPayout selectRecordByAccDetail(RsAccDetail record) {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andPayAccountEqualTo(record.getAccountCode()).andRecAccountEqualTo(record.getToAccountCode())
                .andApAmountEqualTo(record.getTradeAmt()).andTradeDateEqualTo(record.getTradeDate());
        List<RsPayout> payoutList = rsPayoutMapper.selectByExample(example);
        if (payoutList.size() > 0) {
            return payoutList.get(0);
        } else {
            throw new RuntimeException("û�в�ѯ���ñʼƻ������¼��");
        }

    }

    public List<RsPayout> selectEditRecords() {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0")
                .andWorkResultEqualTo(WorkResult.CREATE.getCode());
        example.or(example.createCriteria().andDeletedFlagEqualTo("0")
                .andWorkResultEqualTo(WorkResult.NOTPASS.getCode()));
        return rsPayoutMapper.selectByExample(example);
    }
}

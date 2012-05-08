package fdc.service;

import fdc.common.constant.*;
import fdc.gateway.cbus.domain.txn.QDJG03Res;
import fdc.gateway.cbus.domain.txn.QDJG04Res;
import fdc.gateway.service.CbusTxnService;
import fdc.repository.dao.RsPayoutMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPayoutExample;
import fdc.repository.model.RsPlanCtrl;
import fdc.service.expensesplan.ExpensesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class CbusPayoutService {
    @Autowired
    private RsPayoutMapper rsPayoutMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private ExpensesPlanService expensesPlanService;
    @Autowired
    private RsAccDetailService rsAccDetailService;
    @Autowired
    private CbusTxnService cbusTxnService;

    private SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    public int updateRsPayout(RsPayout rsPayout) {
        RsPayout originRecord = rsPayoutMapper.selectByPrimaryKey(rsPayout.getPkId());
        if (!originRecord.getModificationNum().equals(rsPayout.getModificationNum())) {
            throw new RuntimeException("记录并发更新冲突，请重试！");
        } else {
            OperatorManager om = SystemService.getOperatorManager();
            String operId = om.getOperatorId();
            rsPayout.setLastUpdBy(operId);
            rsPayout.setLastUpdDate(new Date());
            rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
            return rsPayoutMapper.updateByPrimaryKey(rsPayout);
        }
    }

    @Transactional
    public int updateRsPayoutToExec(RsPayout rsPayout) throws Exception {

        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        String operName = om.getOperatorName();
        String operDate = sdf10.format(new Date());
        rsPayout.setExecUserId(operId);
        rsPayout.setExecUserName(operName);
        rsPayout.setExecDate(operDate);
        rsPayout.setTradeDate(operDate);
        rsPayout.setStatusFlag(RefundStatus.ACCOUNT_SUCCESS.getCode());
        rsPayout.setWorkResult(WorkResult.COMMIT.getCode());
        rsPayout.setSerial(commonMapper.selectMaxPayoutSerial());
        rsPayout.setBankSerial(rsPayout.getSerial());
        // 相应计划扣款
        RsPlanCtrl planCtrl = expensesPlanService.selectPlanCtrlByPlanNo(rsPayout.getBusinessNo());
        planCtrl.setAvAmount(planCtrl.getAvAmount().subtract(rsPayout.getApAmount()));

        //======== 开始付款 ===========
        //--------------------------------------------------
        // 新增交易明细
        RsAccDetail accDetail = new RsAccDetail();
        accDetail.setAccountCode(rsPayout.getPayAccount());
        accDetail.setAccountName(rsPayout.getPayCompanyName());
        accDetail.setToAccountCode(rsPayout.getRecAccount());
        accDetail.setToAccountName(rsPayout.getRecCompanyName());
        accDetail.setToHsBankName(rsPayout.getRecBankName());
        accDetail.setInoutFlag(InOutFlag.OUT.getCode());// 支出
        accDetail.setTradeDate(sdf10.format(new Date()));
        accDetail.setTradeAmt(rsPayout.getApAmount());
        accDetail.setBalance(new BigDecimal("0.00"));
        accDetail.setLocalSerial(rsPayout.getSerial());
        accDetail.setBankSerial(rsPayout.getBankSerial());
        accDetail.setTradeType(TradeType.PLAN_PAYOUT.getCode());
        accDetail.setPlanCtrlNo(rsPayout.getBusinessNo());
        accDetail.setRemark(rsPayout.getRemark());
        accDetail.setStatusFlag(TradeStatus.SUCCESS.getCode());
        //-----------------------------------------------------
        // 设置账户余额 和可用余额
        int rtnCnt = expensesPlanService.updatePlanCtrl(planCtrl)
                + rsAccDetailService.insertAccDetail(accDetail)
                + updateRsPayout(rsPayout);
        // TODO 核心记账
        // TODO 行内转账
        // 是否核心记账
        if ("cbus".equals(PropertyManager.getProperty("bank.act.flag"))) {
            QDJG03Res res03 = cbusTxnService.qdjg03payAmtInBank(rsPayout.getRecAccount(), rsPayout.getPayAccount(),
                    String.format("%.2f", rsPayout.getPlAmount()));

            if ("00".equals(res03.getHeader().getRtnCode())) {
                return 1;
            }
            // TODO 跨行转账
            /*
           String sndToBkNo, String rmtrNameFl, String rmtrAcctNo,
           String payeeNameFl, String payeeFlAcctNo, String rmtAmt, String rmtPurp
            */
            QDJG04Res res04 = cbusTxnService.qdjg04payAmtBtwnBank(rsPayout.getRecBankCode(),
                    rsPayout.getPayCompanyName(), rsPayout.getPayAccount(),
                    rsPayout.getRecCompanyName(), rsPayout.getRecAccount(),
                    String.format("%.2f", rsPayout.getPlAmount()), rsPayout.getPurpose());
            if ("00".equals(res04.getHeader().getRtnCode())) {
                return 1;
            }
        }
        return rtnCnt;
    }

    public List<RsPayout> selectRecordsByWorkResult(String workResultCode) {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andWorkResultEqualTo(workResultCode);
        return rsPayoutMapper.selectByExample(example);
    }
}

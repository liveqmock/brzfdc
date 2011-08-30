package fdc.service;

import fdc.common.constant.WorkResult;
import fdc.repository.dao.RsPayoutMapper;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsPayoutExample;
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
        for(RsPayout rsPayout : rsPayoutList) {
            rsPayout.setAuditDate(operDate);
            rsPayout.setAuditUserId(operId);
            rsPayout.setAuditUserName(operName);
            rsPayout.setLastUpdBy(operId);
            rsPayout.setLastUpdDate(operDate);
            rsPayout.setModificationNum(rsPayout.getModificationNum() + 1);
            rsPayout.setWorkResult(workResult);
            if(rsPayoutMapper.updateByPrimaryKey(rsPayout) != 1) {
              rtnFlag = -1;
              throw  new RuntimeException("【记录更新失败】付款监管账号："+rsPayout.getPayAccount());
            }
        }
        return rtnFlag;
    }

    public List<RsPayout> selectRecordsByWorkResult(String workResultCode) {
        RsPayoutExample example = new RsPayoutExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andWorkResultEqualTo(workResultCode);
        return rsPayoutMapper.selectByExample(example);
    }
}

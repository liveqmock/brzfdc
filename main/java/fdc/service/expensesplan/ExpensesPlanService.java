package fdc.service.expensesplan;

import fdc.repository.dao.RsPlanCtrlMapper;
import fdc.repository.model.RsPlanCtrl;
import fdc.repository.model.RsPlanCtrlExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-10
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExpensesPlanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsPlanCtrlMapper rsPlanCtrlMapper;

    public int updatePlanCtrl(RsPlanCtrl planCtrl) {
        RsPlanCtrl originRecord = selectPlanCtrlByPkid(planCtrl.getPkId());
        if (originRecord.getModificationNum().equals(planCtrl.getModificationNum())) {
            throw new RuntimeException("记录并发更新冲突，请重试！");
        } else {
            OperatorManager om = SystemService.getOperatorManager();
            String operId = om.getOperatorId();
            planCtrl.setLastUpdBy(operId);
            planCtrl.setLastUpdDate(new Date());
            planCtrl.setModificationNum(planCtrl.getModificationNum() + 1);
            return rsPlanCtrlMapper.updateByPrimaryKey(planCtrl);
        }
    }

    public RsPlanCtrl selectPlanCtrlByPlanNo(String planNo) {

        RsPlanCtrlExample example = new RsPlanCtrlExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andPlanCtrlNoEqualTo(planNo);
        List<RsPlanCtrl> planCtrlList = rsPlanCtrlMapper.selectByExample(example);
        if (planCtrlList.isEmpty()) {
            throw new RuntimeException("没有查询到计划明细！");
        }
        return planCtrlList.get(0);
    }

    public RsPlanCtrl selectPlanCtrlByPkid(String pkid) {
        return rsPlanCtrlMapper.selectByPrimaryKey(pkid);
    }

    public List<RsPlanCtrl> selectPlanList() {
        RsPlanCtrlExample example = new RsPlanCtrlExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return rsPlanCtrlMapper.selectByExample(example);
    }

    public List<RsPlanCtrl> selectPlanListByFields(String companyName, String accountCode, String payContractNo) {
        RsPlanCtrlExample example = new RsPlanCtrlExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andCompanyNameLike("%" + companyName + "%")
                .andAccountCodeLike("%" + accountCode + "%").andPayContractNoLike("%" + payContractNo + "%");
        return rsPlanCtrlMapper.selectByExample(example);
    }

}

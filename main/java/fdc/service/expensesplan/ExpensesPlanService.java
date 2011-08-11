package fdc.service.expensesplan;

import fdc.repository.dao.RsPlanCtrlMapper;
import fdc.repository.model.RsPlanCtrl;
import fdc.repository.model.RsPlanCtrlExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-10
 * Time: ÏÂÎç4:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExpensesPlanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsPlanCtrlMapper  rsPlanCtrlMapper;

    public List<RsPlanCtrl> selectPlanList(){
        RsPlanCtrlExample example = new RsPlanCtrlExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return rsPlanCtrlMapper.selectByExample(example);
    }

}

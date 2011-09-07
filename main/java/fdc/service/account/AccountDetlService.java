package fdc.service.account;

import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-6
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountDetlService {
    @Autowired
    private RsAccDetailMapper rsAccDetailMapper;

    public List<RsAccDetail> selectedRecordsByTradeDate(Date beginDate, Date endDate) {
        RsAccDetailExample example = new RsAccDetailExample();
        if (beginDate != null && endDate != null) {
            example.createCriteria().andTradeDateBetween(beginDate, endDate).andDeletedFlagEqualTo("0");
        }
        return rsAccDetailMapper.selectByExample(example);
    }

    public void insertSelectedRecord(RsAccDetail rsAccDetail) {
        rsAccDetailMapper.insertSelective(rsAccDetail);
    }

    public List<RsAccDetail> selectedRecordsForChk(String tradeType, String statusflag) {
        RsAccDetailExample example = new RsAccDetailExample();
        if (tradeType != null && !StringUtils.isEmpty(tradeType.trim())) {
            example.createCriteria().andDeletedFlagEqualTo("0").andTradeTypeEqualTo(tradeType).andStatusFlagEqualTo(statusflag);
        }
        return rsAccDetailMapper.selectByExample(example);
    }

    public boolean isChecked(RsAccDetail rsAccDetail) {
        String orgn_statusflag = rsAccDetailMapper.selectByPrimaryKey(rsAccDetail.getPkId()).getStatusFlag();
        if (orgn_statusflag.equalsIgnoreCase(rsAccDetail.getStatusFlag())) {
            return true;
        } else {
            return false;
        }
    }

    public int updateSelectedRecord(RsAccDetail rsAccDetail) {
//        if (isChecked(rsAccDetail)) {
            return rsAccDetailMapper.updateByPrimaryKeySelective(rsAccDetail);
//        } else {
//            throw new RuntimeException("记录并发更新冲突！");
//        }
    }

    public RsAccDetailMapper getRsAccDetailMapper() {
        return rsAccDetailMapper;
    }

    public void setRsAccDetailMapper(RsAccDetailMapper rsAccDetailMapper) {
        this.rsAccDetailMapper = rsAccDetailMapper;
    }
}

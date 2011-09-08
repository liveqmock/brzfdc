package fdc.service.account;

import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
import org.apache.commons.lang.StringUtils;
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
 * User: haiyuhuang
 * Date: 11-9-6
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountDetlService {
    @Autowired
    private RsAccDetailMapper rsAccDetailMapper;

    public List<RsAccDetail> selectedRecordsByTradeDate(String beginDate, String endDate) {
        RsAccDetailExample example = new RsAccDetailExample();
        if (beginDate != null && endDate != null) {
            example.createCriteria().andTradeDateBetween(beginDate, endDate).andDeletedFlagEqualTo("0");
        }
        return rsAccDetailMapper.selectByExample(example);
    }

    /**
     * 插入*/
    public void insertSelectedRecord(RsAccDetail rsAccDetail) {
        OperatorManager om = SystemService.getOperatorManager();
        rsAccDetail.setCreatedBy(om.getOperatorId());
        rsAccDetail.setCreatedDate(new Date());
        rsAccDetail.setLastUpdBy(om.getOperatorId());
        rsAccDetail.setLastUpdDate(new Date());
        rsAccDetailMapper.insertSelective(rsAccDetail);
    }

    /**
     * 未发送前数据(包括退回)*/
    public List<RsAccDetail> selectedRecordsForChk(String tradeType, List<String> statusflags) {
        RsAccDetailExample example = new RsAccDetailExample();
        example.clear();
        RsAccDetailExample.Criteria criteria = example.createCriteria();
        if (tradeType != null && !StringUtils.isEmpty(tradeType.trim())) {
            criteria.andTradeTypeEqualTo(tradeType);
        }
        if (statusflags != null && statusflags.size()>0) {
            criteria.andStatusFlagIn(statusflags);
        }
        example.setOrderByClause("trade_Date desc");
        return rsAccDetailMapper.selectByExample(example);
    }

    public List<RsAccDetail> selectedRecordsForSend(String tradeType,String statusflag,String sendflag) {
        RsAccDetailExample example = new RsAccDetailExample();
        example.clear();
        RsAccDetailExample.Criteria criteria = example.createCriteria();
        if (tradeType != null && !StringUtils.isEmpty(tradeType.trim())) {
            criteria.andTradeTypeEqualTo(tradeType);
        }
        if (statusflag != null && !StringUtils.isEmpty(tradeType.trim())) {
            criteria.andStatusFlagEqualTo(statusflag);
        }
        if (sendflag != null && !StringUtils.isEmpty(sendflag.trim())) {
            criteria.andSendFlagEqualTo(sendflag);
        }
        example.setOrderByClause("trade_Date desc");
        return rsAccDetailMapper.selectByExample(example);
    }

    public RsAccDetail selectedByPK(String pkid) {
        return rsAccDetailMapper.selectByPrimaryKey(pkid);
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
        OperatorManager om = SystemService.getOperatorManager();
        rsAccDetail.setLastUpdBy(om.getOperatorId());
        rsAccDetail.setLastUpdDate(new Date());
        return rsAccDetailMapper.updateByPrimaryKeySelective(rsAccDetail);
    }

    public RsAccDetailMapper getRsAccDetailMapper() {
        return rsAccDetailMapper;
    }

    public void setRsAccDetailMapper(RsAccDetailMapper rsAccDetailMapper) {
        this.rsAccDetailMapper = rsAccDetailMapper;
    }
}

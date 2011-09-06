package fdc.service.account;

import fdc.repository.dao.RsAccDetailMapper;
import fdc.repository.model.RsAccDetail;
import fdc.repository.model.RsAccDetailExample;
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
 * Time: ÉÏÎç10:15
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountDetlService {
    @Autowired
    private RsAccDetailMapper rsAccDetailMapper;

    public List<RsAccDetail> selectedRecordsByTradeDate(Date beginDate,Date endDate) {
        RsAccDetailExample example = new RsAccDetailExample();
        if (beginDate != null && endDate != null) {
            example.createCriteria().andTradeDateBetween(beginDate,endDate);
        }
        return rsAccDetailMapper.selectByExample(example);
    }

    public void insertSelectedRecord(RsAccDetail rsAccDetail) {
        rsAccDetailMapper.insertSelective(rsAccDetail);
    }

    public RsAccDetailMapper getRsAccDetailMapper() {
        return rsAccDetailMapper;
    }

    public void setRsAccDetailMapper(RsAccDetailMapper rsAccDetailMapper) {
        this.rsAccDetailMapper = rsAccDetailMapper;
    }
}

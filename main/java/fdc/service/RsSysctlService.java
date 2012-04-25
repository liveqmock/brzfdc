package fdc.service;

import fdc.repository.dao.RsSysCtlMapper;
import fdc.repository.dao.common.CommonMapper;
import fdc.repository.model.RsSysCtl;
import fdc.repository.model.RsSysCtlExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-4-26
 * Time: ����1:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RsSysctlService {

    @Autowired
    private RsSysCtlMapper rsSysCtlMapper;
    @Autowired
    private CommonMapper commonMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int generateTxnSeq(String elmtId) {
        RsSysCtlExample example = new RsSysCtlExample();
        example.createCriteria().andSysElmtIdEqualTo(elmtId);
        RsSysCtl rsSysCtl = rsSysCtlMapper.selectByExample(example).get(0);

        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        int txnSeq = commonMapper.selectSysSeq();
        if (date.equals(rsSysCtl.getSysDate())) {
            commonMapper.updateSysSeq(txnSeq + 1);
        } else {
            rsSysCtl.setSysDate(date);
            commonMapper.updateSeqAndSysDate(100000, date);
        }
        return txnSeq;
    }

}

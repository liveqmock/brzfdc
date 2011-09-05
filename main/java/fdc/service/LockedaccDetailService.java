package fdc.service;

import fdc.repository.dao.RsLockedaccDetailMapper;
import fdc.repository.model.RsLockedaccDetail;
import fdc.repository.model.RsLockedaccDetailExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-5
 * Time: ����5:11
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LockedaccDetailService {
    @Autowired
    private RsLockedaccDetailMapper lockedaccDetailMapper;

    @Transactional
    public int insertRecord(RsLockedaccDetail record) {
        OperatorManager om = SystemService.getOperatorManager();
        String operId = om.getOperatorId();
        record.setCreatedBy(operId);
        record.setCreatedDate(new Date());
        return lockedaccDetailMapper.insertSelective(record);
    }

    public List<RsLockedaccDetail> selectRecordsBySendflag(String sendflag) {
        RsLockedaccDetailExample example = new RsLockedaccDetailExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andSendFlagEqualTo(sendflag);
        return lockedaccDetailMapper.selectByExample(example);
    }

    @Transactional
    public int updateRecordToSendflag(RsLockedaccDetail record, String sendflag) {
        record.setSendFlag(sendflag);
        return updateRecord(record);
    }

    public int updateRecord(RsLockedaccDetail record) {
        RsLockedaccDetail originRecord = lockedaccDetailMapper.selectByPrimaryKey(record.getPkId());
        if(!record.getModificationNum().equals(originRecord.getModificationNum())) {
            throw new RuntimeException("���������쳣���������˺� ��"+record.getAccountCode());
        }
        record.setModificationNum(record.getModificationNum() + 1);
        return lockedaccDetailMapper.updateByPrimaryKeySelective(record);
    }
}

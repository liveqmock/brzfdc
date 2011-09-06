package fdc.service;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import fdc.common.constant.ContractRecvStatus;
import fdc.common.constant.SendFlag;
import fdc.common.constant.WorkResult;
import fdc.repository.dao.RsContractMapper;
import fdc.repository.dao.RsReceiveMapper;
import fdc.repository.model.RsPayout;
import fdc.repository.model.RsReceive;
import fdc.repository.model.RsReceiveExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * ��Ҫ��Ӧ��ͬ�տ�.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ����2:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContractRecvService {

    @Autowired
    private RsContractMapper contractMapper;
    @Autowired
    private RsReceiveMapper receiveMapper;

    public int insertRecord(RsReceive record) throws Exception {
        OperatorManager om = SystemService.getOperatorManager();
        record.setCreatedBy(om.getOperatorId());
        record.setApplyUserId(om.getOperatorId());
        record.setApplyUserName(om.getOperatorName());
        record.setCreatedDate(new Date());
        return receiveMapper.insertSelective(record);
    }

    public int updateRsReceiveToWorkResult(RsReceive rsReceive, WorkResult workResult) throws Exception {
        RsReceive originRecord = receiveMapper.selectByPrimaryKey(rsReceive.getPkId());
        if (rsReceive.getWorkResult().equalsIgnoreCase(originRecord.getWorkResult())) {
            OperatorManager om = SystemService.getOperatorManager();
            String operId = om.getOperatorId();
            String operName = om.getOperatorName();
            Date operDate = new Date();
            rsReceive.setLastUpdBy(operId);
            rsReceive.setLastUpdDate(operDate);
            rsReceive.setWorkResult(workResult.getCode());
            return updateRecord(rsReceive);
        } else {
            throw new RuntimeException("��¼�������³�ͻ��");
        }

    }

    public int updateRecord(RsReceive record) {
        RsReceive originRecord = receiveMapper.selectByPrimaryKey(record.getPkId());
        if (!originRecord.getModificationNum().equals(originRecord.getModificationNum())) {
            throw new RuntimeException("��¼�������³�ͻ��");
        }
        record.setModificationNum(record.getModificationNum() + 1);
        return receiveMapper.updateByPrimaryKeySelective(record);
    }

    public List<RsReceive> selectContractRecvList() {
        RsReceiveExample example = new RsReceiveExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return receiveMapper.selectByExample(example);
    }

    public List<RsReceive> selectContractList(WorkResult workResult) {
        RsReceiveExample example = new RsReceiveExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andWorkResultEqualTo(workResult.getCode());
        return receiveMapper.selectByExample(example);
    }

    public RsReceive selectContractRecv(String pkId) {
        return receiveMapper.selectByPrimaryKey(pkId);
    }
}

package fdc.service.contract;

import fdc.common.constant.ContractRecvStatus;
import fdc.common.constant.ContractStatus;
import fdc.repository.dao.BiContractCloseMapper;
import fdc.repository.dao.RsContractMapper;
import fdc.repository.model.BiContractClose;
import fdc.repository.model.BiContractCloseExample;
import fdc.repository.model.RsContract;
import fdc.repository.model.RsContractExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContractService {

    @Autowired
    private RsContractMapper contractMapper;
    @Autowired
    private BiContractCloseMapper contractCloseMapper;

    public List<RsContract> selectContractList(){
        RsContractExample example = new RsContractExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return  contractMapper.selectByExample(example);
    }

    public RsContract selectContractByNo(String contractNo) {
        RsContractExample example = new RsContractExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andContractNoEqualTo(contractNo);
        List<RsContract> list = contractMapper.selectByExample(example);
        if(list.isEmpty()) {
            return null;
        }else return list.get(0);
    }
    public BiContractClose selectCloseContractByNo(String contractNo) {
        BiContractCloseExample example = new BiContractCloseExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andContractNoEqualTo(contractNo);
        List<BiContractClose> list = contractCloseMapper.selectByExample(example);
        if(list.isEmpty()) {
            return null;
        }else return list.get(0);
    }

    public List<RsContract> selectContractList(ContractStatus contractStatus){
        RsContractExample example = new RsContractExample();
        example.createCriteria().andDeletedFlagEqualTo("0").andStatusFlagEqualTo(contractStatus.getCode());
        return  contractMapper.selectByExample(example);
    }
    public RsContract selectRecordContract(String pkid) {
        return contractMapper.selectByPrimaryKey(pkid);
    }

    @Transactional
    public int updateRecord(RsContract contract) {
        OperatorManager om = SystemService.getOperatorManager();
        contract.setLastUpdBy(om.getOperatorId());
        contract.setLastUpdDate(new Date());
        RsContract originRecord = contractMapper.selectByPrimaryKey(contract.getPkId());
        if(!originRecord.getModificationNum().equals(contract.getModificationNum())) {
            throw new RuntimeException("并发更新冲突!");
        }
        return contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Transactional
    public int insertContract(RsContract contract) {
         return contractMapper.insertSelective(contract);
    }

}

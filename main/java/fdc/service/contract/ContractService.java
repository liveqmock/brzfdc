package fdc.service.contract;

import fdc.repository.dao.RsContractMapper;
import fdc.repository.model.RsContract;
import fdc.repository.model.RsContractExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-8-25
 * Time: ÏÂÎç2:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContractService {

    @Autowired
    private RsContractMapper contractMapper;

    public List<RsContract> selectContractList(){
        RsContractExample example = new RsContractExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return  null;
    }
}

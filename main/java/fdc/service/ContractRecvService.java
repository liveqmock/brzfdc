package fdc.service;

import fdc.repository.dao.RsContractMapper;
import fdc.repository.dao.RsReceiveMapper;
import fdc.repository.model.RsReceive;
import fdc.repository.model.RsReceiveExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<RsReceive> selectContractRecvList(){
        RsReceiveExample example = new RsReceiveExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return  receiveMapper.selectByExample(example);
    }
}

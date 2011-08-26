package fdc.gateway.service;

import fdc.repository.dao.BiContractCloseMapper;
import fdc.repository.dao.BiContractMapper;
import fdc.repository.dao.RsAccountMapper;
import fdc.repository.model.BiContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-26
 * Time: ÏÂÎç2:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BiDbService {

    @Autowired
    private RsAccountMapper accountMapper;
    @Autowired
    private BiContractMapper biContractMapper;
    @Autowired
    private BiContractCloseMapper biContractCloseMapper;

}

package fdc.service.company;

import fdc.repository.dao.RsFdccompanyMapper;
import fdc.repository.model.RsFdccompany;
import fdc.repository.model.RsFdccompanyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.SystemService;
import pub.platform.security.OperatorManager;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-24
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
@Service("fdccompanyService")
public class FdccompanyService {
    @Autowired
    private RsFdccompanyMapper fdccompanyMapper;

    /**
     * 判断房产商ID是否存在
     *
     * @param fdccompany
     * @return
     */
    public boolean isExistInDb(RsFdccompany fdccompany) {
        RsFdccompanyExample example = new RsFdccompanyExample();
        example.createCriteria().andCompanyIdEqualTo(fdccompany.getCompanyId());
        int cnt = fdccompanyMapper.countByExample(example);
        return (cnt >= 1);
    }

    /**
     * 是否并发更新冲突
     *
     * @param fdccompany
     * @return
     */
    public boolean isModifiable(RsFdccompany fdccompany) {
        RsFdccompany originRecord = fdccompanyMapper.selectByPrimaryKey(fdccompany.getPkId());
        if (fdccompany.getModificationNum() != originRecord.getModificationNum()) {
            return false;
        }
        return true;
    }

    /**
     * 新增房产商信息
     *
     * @param fdccompany
     */
    @Transactional
    public void insertRsFdccompany(RsFdccompany fdccompany) {
        String newId = fdccompanyMapper.selectNewCompanyId();
        fdccompany.setCompanyId(newId);
        OperatorManager om = SystemService.getOperatorManager();
        fdccompany.setCreatedBy(om.getOperatorId());
        fdccompany.setCreatedDate(new Date());
        fdccompany.setLastUpdBy(om.getOperatorId());
        fdccompany.setLastUpdDate(new Date());
        fdccompanyMapper.insertSelective(fdccompany);
    }

    /**
     * 并发更新
     *
     * @param fdccompany
     */
    @Transactional
    public void updateRsFdccompany(RsFdccompany fdccompany) {
        if (isModifiable(fdccompany)) {
            fdccompany.setModificationNum(fdccompany.getModificationNum() + 1);
            OperatorManager om = SystemService.getOperatorManager();
            fdccompany.setLastUpdBy(om.getOperatorId());
            fdccompany.setLastUpdDate(new Date());
            fdccompanyMapper.updateByPrimaryKey(fdccompany);
        } else {
            throw new RuntimeException("并发更新冲突！UUID=" + fdccompany.getPkId());
        }
    }

    /**
     * 查询所有未删除记录
     *
     * @return
     */
    public List<RsFdccompany> qryAllRecords() {
        RsFdccompanyExample example = new RsFdccompanyExample();
        example.createCriteria().andDeleteFlagEqualTo("0");
        return fdccompanyMapper.selectByExample(example);
    }

    /**
     * 按名称模糊查询房产公司
     *
     * @param companyName
     * @return
     */
    public List<RsFdccompany> qryRsFdccompanyByName(String companyName) {
        RsFdccompanyExample example = new RsFdccompanyExample();
        example.createCriteria().andCompanyNameLike("%" + companyName + "%");
        return fdccompanyMapper.selectByExample(example);
    }
}

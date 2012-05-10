package fdc.service;

import fdc.repository.dao.CbsBankInfoMapper;
import fdc.repository.model.CbsBankInfo;
import fdc.repository.model.CbsBankInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-5-9
 * Time: ����11:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BankInfoService {

    @Autowired
    private CbsBankInfoMapper cbsBankInfoMapper;

    public List<SelectItem> qryBankInfoListByNoAndName(String code, String name) {
        CbsBankInfoExample example = new CbsBankInfoExample();
        List<SelectItem> items = new ArrayList<SelectItem>();
        if (!name.contains(" ")) {
            example.createCriteria().andCodeLike("%" + code + "%").andFullNameLike("%" + name + "%");
        } else {
            for (String s : name.split(" ")) {
                example.createCriteria().andCodeLike("%" + code + "%").andFullNameLike("%" + s + "%");
            }
        }
        int cnt = cbsBankInfoMapper.countByExample(example);
        if (cnt > 30) {
            throw new RuntimeException("��ѯ�����м�¼��̫�࣬��ϸ����ѯ������");
        }
        List<CbsBankInfo> cbsBankInfos = cbsBankInfoMapper.selectByExample(example);
        for (CbsBankInfo record : cbsBankInfos) {
            SelectItem item = new SelectItem(record.getCode(), record.getFullName());
            items.add(item);
        }
        return items;
    }
}

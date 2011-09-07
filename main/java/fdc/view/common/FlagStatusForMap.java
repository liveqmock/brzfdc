package fdc.view.common;

import fdc.repository.model.RsFdccompany;
import fdc.service.company.CompanyService;
import org.springframework.context.support.StaticApplicationContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-6
 * Time: ����2:23
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class FlagStatusForMap {
    @ManagedProperty(value = "#{companyService}")
    private CompanyService companyService;
    //��������
    private Map<String,String> tradeTypeMap;
    //����˻�������ϸ״̬��־
    private Map<String,String> actDetlStatusFlagMap;
    //���ն��˱�־
    private Map<String,String> actDetlDcheckFlagMap;
    //�����־
    private Map<String,String> actDetlChangeFlagMap;
    //������˾ Map<id,name>
    private Map<String,String> companyMap;

    public Map<String, String> getCompanyMap() {
        List<RsFdccompany> rsFdccompanyList = companyService.qryRsFdccompanyByName("");
        companyMap = new HashMap();
        for (RsFdccompany rf:rsFdccompanyList) {
            companyMap.put(rf.getCompanyId(),rf.getCompanyName());
        }
        return companyMap;
    }

    public void setCompanyMap(Map<String, String> companyMap) {
        this.companyMap = companyMap;
    }

    public Map<String, String> getActDetlChangeFlagMap() {
        actDetlChangeFlagMap = new HashMap();
        actDetlChangeFlagMap.put("N","����");
        actDetlChangeFlagMap.put("R","����");
        actDetlChangeFlagMap.put("D","ɾ��");
        return actDetlChangeFlagMap;
    }

    public void setActDetlChangeFlagMap(Map<String, String> actDetlChangeFlagMap) {
        this.actDetlChangeFlagMap = actDetlChangeFlagMap;
    }

    public Map<String, String> getActDetlDcheckFlagMap() {
        actDetlDcheckFlagMap = new HashMap();
        actDetlDcheckFlagMap.put("0","δ����");
        actDetlDcheckFlagMap.put("1","�Ѷ���");
        return actDetlDcheckFlagMap;
    }

    public void setActDetlDcheckFlagMap(Map<String, String> actDetlDcheckFlagMap) {
        this.actDetlDcheckFlagMap = actDetlDcheckFlagMap;
    }

    public Map<String, String> getTradeTypeMap() {
        tradeTypeMap = new HashMap();
        tradeTypeMap.put("01","��������");
        tradeTypeMap.put("02","�ƻ�����");
        tradeTypeMap.put("03","�˿�");
        tradeTypeMap.put("04","��Ϣ");
        tradeTypeMap.put("05","���ת");
        tradeTypeMap.put("09","����");
        return tradeTypeMap;
    }

    public void setTradeTypeMap(Map<String, String> tradeTypeMap) {
        this.tradeTypeMap = tradeTypeMap;
    }

    public Map<String, String> getActDetlStatusFlagMap() {
        actDetlStatusFlagMap = new HashMap();
        actDetlStatusFlagMap.put("0","�ѳ���");
        actDetlStatusFlagMap.put("1","���׳ɹ�");
        actDetlStatusFlagMap.put("2","����ͨ��");
        return actDetlStatusFlagMap;
    }

    public void setActDetlStatusFlagMap(Map<String, String> actDetlStatusFlagMap) {
        this.actDetlStatusFlagMap = actDetlStatusFlagMap;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
}

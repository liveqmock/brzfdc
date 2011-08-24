package fdc.view.account;

import fdc.repository.model.RsAccount;
import fdc.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platform.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-24
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccountAction {
    private static final Logger logger = LoggerFactory.getLogger(AccountAction.class);
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    private RsAccount account;
    private List<RsAccount> accountList;

    @PostConstruct
    public void init() {
        this.account = new RsAccount();
        accountList = new ArrayList<RsAccount>();
    }

    public String insertRecord(RsAccount account) {
        try {
            accountService.insertRecord(account);
            accountList.add(account);
        } catch (Exception e) {
            logger.error("新增数据失败，", e);
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("新增数据完成。");
        return null;
    }
}

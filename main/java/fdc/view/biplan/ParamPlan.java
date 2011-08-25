package fdc.view.biplan;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-8-25
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class ParamPlan {
    // -------------查询条件用字段-----------
    private String companyName;
    private String accountCode;
    private String toAccountName;
    private String toAccountCode;
    private String tradeStatusFlag;
    private String docNo;
    private String applyStartDate;
    private String applyEndDate;
    private String applyStartAmt;
    private String applyEndAmt;
    //----------Datatable List用字段------
    private String applyAmt;
    private String applyDate;
    private String applyOperName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(String toAccountCode) {
        this.toAccountCode = toAccountCode;
    }

    public String getTradeStatusFlag() {
        return tradeStatusFlag;
    }

    public void setTradeStatusFlag(String tradeStatusFlag) {
        this.tradeStatusFlag = tradeStatusFlag;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getApplyStartDate() {
        return applyStartDate;
    }

    public void setApplyStartDate(String applyStartDate) {
        this.applyStartDate = applyStartDate;
    }

    public String getApplyEndDate() {
        return applyEndDate;
    }

    public void setApplyEndDate(String applyEndDate) {
        this.applyEndDate = applyEndDate;
    }

    public String getApplyStartAmt() {
        return applyStartAmt;
    }

    public void setApplyStartAmt(String applyStartAmt) {
        this.applyStartAmt = applyStartAmt;
    }

    public String getApplyEndAmt() {
        return applyEndAmt;
    }

    public void setApplyEndAmt(String applyEndAmt) {
        this.applyEndAmt = applyEndAmt;
    }

    public String getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(String applyAmt) {
        this.applyAmt = applyAmt;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyOperName() {
        return applyOperName;
    }

    public void setApplyOperName(String applyOperName) {
        this.applyOperName = applyOperName;
    }
}

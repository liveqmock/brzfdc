package platform.repository.model;

public class PtenudetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.ENUITEMVALUE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    private String enuitemvalue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.ENUTYPE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    private String enutype;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.ENUITEMVALUE
     *
     * @return the value of PTENUDETAIL.ENUITEMVALUE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    public String getEnuitemvalue() {
        return enuitemvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.ENUITEMVALUE
     *
     * @param enuitemvalue the value for PTENUDETAIL.ENUITEMVALUE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    public void setEnuitemvalue(String enuitemvalue) {
        this.enuitemvalue = enuitemvalue == null ? null : enuitemvalue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.ENUTYPE
     *
     * @return the value of PTENUDETAIL.ENUTYPE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    public String getEnutype() {
        return enutype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.ENUTYPE
     *
     * @param enutype the value for PTENUDETAIL.ENUTYPE
     *
     * @mbggenerated Fri Jul 22 13:16:43 CST 2011
     */
    public void setEnutype(String enutype) {
        this.enutype = enutype == null ? null : enutype.trim();
    }
}
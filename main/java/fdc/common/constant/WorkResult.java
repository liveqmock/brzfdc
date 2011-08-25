package fdc.common.constant;

import java.util.Hashtable;

/**
 * 处理结果。[0]-不通过;[1]-通过;[2]-提交;[3]-新建
 * User: zhanrui
 * Date: 11-7-23
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public enum WorkResult implements EnumApp {
    NOTPASS("0", "不通过"),
    PASS("1", "通过"),
    COMMIT("2", "提交"),
    CREATE("3", "新建");

    private String code = null;
    private String title = null;
    private static Hashtable<String, WorkResult> aliasEnums;

    WorkResult(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public static WorkResult valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

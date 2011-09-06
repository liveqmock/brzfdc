package fdc.common.constant;

import java.util.Hashtable;

public enum LockAccStatus implements EnumApp {
    UN_LOCK("0", "�ⶳ"),
    PART_LOCK("1", "���ֶ���"),
    FULL_LOCK("2","ȫ������");

    private String code = null;
    private String title = null;
    private static Hashtable<String, LockAccStatus> aliasEnums;

    LockAccStatus(String code, String title) {
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

    public static LockAccStatus valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
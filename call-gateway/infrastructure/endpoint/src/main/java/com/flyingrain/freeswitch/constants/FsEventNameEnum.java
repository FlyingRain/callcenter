package com.flyingrain.freeswitch.constants;

public enum FsEventNameEnum {

    CHANNEL_CREATE("CHANNEL_CREATE", "通道创建"),
    CHANNEL_ANSWER("CHANNEL_ANSWER", "通道应答"),
    CHANNEL_HANGUP_COMPLETE("CHANNEL_HANGUP_COMPLETE", "通道挂断"),


    ;
    private String code;

    private String desc;

    FsEventNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FsEventNameEnum getByCode(String code) {
        for (FsEventNameEnum e : FsEventNameEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

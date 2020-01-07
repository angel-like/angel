package com.xmsy.server.zxyy.manager.utils;

public enum GameGradeEnum {

    DZPKXSF(3600L,"新手房"),
    DZPKCJF(3601L,"初级房"),
    DZPKZJF(3602L,"中级房"),
    DZPKGJF(3603L,"高级房"),
    DZPKCDQCF(3700L,"财大气粗房"),
    DZPKYCWGF(3701L,"腰缠万贯房"),
    DZPKHJRTF(3702L,"挥金如土房"),
    DZPKFGBRF(3703L,"富贵逼人房"),

    SLWHTYF(9201L,"体验房"),
    SLWHCJF(9202L,"初级房"),
    SLWHZJF(9203L,"中级房"),
    SLWHGJF(9204L,"高级房"),

    ERMJTYF(7401L,"体验房"),
    ERMJCJF(7402L,"初级房"),
    ERMJZJF(7403L,"中级房"),
    ERMJGJF(7404L,"高级房"),

    XLCHTYF(6501L,"体验房"),
    XLCHCJF(6502L,"初级房"),
    XLCHZJF(6503L,"中级房"),
    XLCHGJF(6504L,"高级房"),

    EBGMJTYF(7201L,"体验房"),
    EBGMJCJF(7202L,"初级房"),
    EBGMJZJF(7203L,"中级房"),
    EBGMJGJF(7204L,"高级房"),
    EBGMJZZF(7205L,"至尊房"),
    EBGMJWZF(7206L,"王者房"),

    QZNNTYF(8301L,"体验房"),
    QZNNCJF(8302L,"初级房"),
    QZNNZJF(8303L,"中级房"),
    QZNNGJF(8304L,"高级房"),
    QZNNZZF(8305L,"至尊房"),
    QZNNWZF(8306L,"王者房"),

    DDZTYF(6101L,"体验房"),
    DDZCJF(6102L,"初级房"),
    DDZZJF(6103L,"中级房"),
    DDZGJF(6104L,"高级房"),

    SGTYF(8601L,"体验房"),
    SGCJF(8602L,"初级房"),
    SGZJF(8603L,"中级房"),
    SGGJF(8604L,"高级房"),
    SGZZF(8605L,"至尊房"),
    SGWZF(8606L,"王者房"),
    
    ZJHTYF(2201L,"体验房"),
    ZJHCJF(2202L,"初级房"),
    ZJHZJF(2203L,"中级房"),
    ZJHGJF(2204L,"高级房"),

    ESYDTYF(600L,"体验房"),
    ESYDCJF(600L,"初级房"),
    ESYDZJF(600L,"中级房"),
    ESYDGJF(600L,"高级房"),

    JSYSTYF(1940L,"体验房"),
    JSYSCJF(1940L,"初级房"),
    JSYSZJF(1940L,"中级房"),
    JSYSGJF(1940L,"高级房"),
    ;

    private final Long roomId;

    private final String roomName;

    private GameGradeEnum(final Long roomId, String roomName){
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public static String getGradeName(Long value) {
        GameGradeEnum[] roomEnums = values();
        for (GameGradeEnum roomEnum : roomEnums) {
            if (roomEnum.roomId().equals(value)) {
                return roomEnum.roomName();
            }
        }
        return null;
    }

    public Long  roomId(){
        return this.roomId;
    }

    public String roomName(){
        return this.roomName;
    }

}
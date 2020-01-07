package com.xmsy.server.zxyy.schedule.utils;

public enum GameRoomEnum {

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
    ;

    private final Long roomId;

    private final String roomName;

    private GameRoomEnum(final Long roomId, String roomName){
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public static String getRoomName(Long value) {
        GameRoomEnum[] roomEnums = values();
        for (GameRoomEnum roomEnum : roomEnums) {
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
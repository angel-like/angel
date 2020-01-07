package com.xmsy.server.zxyy.manager.utils;

public enum GameEnum {

    MJ(740L,"开元二人麻将"),

    SLWH(920L,"开元森林舞会"),

    DZPK(620L,"开元德州扑克"),

    XLCH(650L,"开元血流成河"),
    
    HBBY(510L,"开元红包捕鱼"),

    EBG(720L,"开元二八杠"),

    QZNN(830L,"开元抢庄牛牛"),

    SG(860L,"开元三公"),
    
    ZJH(220L,"开元炸金花"),

    GSSH(1370L,"开元港式梭哈"),

    DDZ(610L,"开元斗地主"),

    ESYD(600L,"开元21点"),

    QZPJ(730L,"开元抢庄牌九"),

    QTNN(1810L,"开元单挑牛牛"),

    TBNN(870L,"开元通比牛牛"),
    ;

    private final Long gameId;

    private final String gameName;

    private GameEnum(final Long gameId,String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public static String getGameName(Long value) {
        GameEnum[] gameEnums = values();
        for (GameEnum  gameEnum : gameEnums) {
            if (gameEnum.gameId().equals(value)) {
                return gameEnum.gameName();
            }
        }
        return null;
    }

    public Long  gameId(){
        return this.gameId;
    }

    public String gameName(){
        return this.gameName;
    }

}
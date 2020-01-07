package com.xmsy.server.zxyy.schedule.utils;

public enum GameEnum {

    MJ(740L,"开元二人麻将"),

    SLWH(920L,"开元森林舞会"),

    DZPK(620L,"开元德州扑克"),

    EBG(720L,"开元二八杠"),

    QZNN(830L,"开元抢庄牛牛"),
    TBNN(870L,"开元通比牛牛"),

    GSSH(1370L,"开元港式梭哈"),

    DDZ(610L,"开元斗地主"),
    SG(860L,"开元三公"),
    ESYD(600L,"开元21点"),
    QZPJ(730L,"开元抢庄牌九"),
    XZTB(1690L,"开元血战骰宝"),
	XYDB(1610L,"开元幸运夺宝"),
	DCPK(1860L,"开元赌场扑克"),
	SSS(630L,"开元十三水"),
	BCBM(1960L,"开元奔驰宝马"),
	WXHH(1970L,"开元五星宏辉"),
	JSZJH(230L,"开元极速炸金花"),
	BJL(910L,"开元百家乐"),

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
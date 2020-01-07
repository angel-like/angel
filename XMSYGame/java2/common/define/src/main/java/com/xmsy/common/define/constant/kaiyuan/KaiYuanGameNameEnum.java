package com.xmsy.common.define.constant.kaiyuan;

public enum KaiYuanGameNameEnum {

    KYSLWH(920L,"开元森林舞会"),
    KYERMJ(740L,"开元二人麻将"),
    KYDZPK(620L,"开元德州扑克"),
    KYXLCH(650L,"开元血流成河"),
    KYGSSH(1370L,"开元港式梭哈"),
    KYXZTB(1690L,"开元血战骰宝"),
	KYXYDB(1610L,"开元幸运夺宝"),
	KYDCPK(1860L,"开元赌场扑克"),
	KYSSS(630L,"开元十三水"),
	KYBCBM(1960L,"开元奔驰宝马"),
	KYWXHH(1970L,"开元五星宏辉"),
	KYJSYS(1940L,"开元金鲨银鲨"),
    KYESYD(600L,"开元21点"),
    KYQZPJ(730L,"开元抢庄牌九"),
    KYQTNN(1810L,"开元单挑牛牛"),
    KYTBNN(870L,"开元通比牛牛"),
    KYJSZJH(230L,"开元极速炸金花"),
    KYBJL(910L,"开元百家乐"),
	KYWRZJH(1950L,"开元万人炸金花"),
    KYBRTB(1980L,"开元百人骰宝"),
    KYYBQZNN(1850L,"开元押宝抢庄牛牛"),
    KYZJN(1990L,"开元炸金牛"),
    KYYZLH(900L,"开元押庄龙虎"),
    KYBRNN(930L,"开元百人牛牛"),
    ;

    private final Long gameId;

    private final String gameName;

    private KaiYuanGameNameEnum(final Long gameId, String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public static String getGameName(Long value) {
        KaiYuanGameNameEnum[] roomEnums = values();
        for (KaiYuanGameNameEnum roomEnum : roomEnums) {
            if (roomEnum.gameId().equals(value)) {
                return roomEnum.gameName();
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
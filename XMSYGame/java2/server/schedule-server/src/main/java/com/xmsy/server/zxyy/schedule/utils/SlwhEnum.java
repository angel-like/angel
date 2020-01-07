package com.xmsy.server.zxyy.schedule.utils;

public class SlwhEnum {

    //事件
    public enum EventEnum {
        WSJ(1,"无事件"),
        DSY(2,"大三元"),
        DSX(3,"大四喜"),
        PLSD(4,"霹雳闪电"),
        SD(5,"送灯"),
        ;

        private final int eventId;

        private final String eventName;

        private EventEnum(final int eventId,String eventName){
            this.eventId = eventId;
            this.eventName = eventName;
        }

        public static String getEventName(int eventId) {
            EventEnum[] eventEnums = values();
            for (EventEnum eventEnum : eventEnums) {
                if (eventEnum.eventId() == eventId) {
                    return eventEnum.eventName();
                }
            }
            return null;
        }

        public int  eventId(){
            return this.eventId;
        }

        public String eventName(){
            return this.eventName;
        }

    }

    //颜色
    //颜色
    public enum ColorEnum {
        HS("R","红色"),
        LS("G","绿色"),
        HSY("Y","黄色");

        private final String colorId;

        private final String colorName;

        private ColorEnum(final String colorId,String colorName){
            this.colorId = colorId;
            this.colorName = colorName;
        }

        public static String getColorName(String colorId) {
            ColorEnum[] colorEnums = values();
            for (ColorEnum colorEnum : colorEnums) {
                if (colorEnum.colorId().equals(colorId)) {
                    return colorEnum.colorName();
                }
            }
            return null;
        }

        public String  colorId(){
            return this.colorId;
        }

        public String colorName(){
            return this.colorName;
        }
    }

    //类型
    public enum TypeEnum {
        SZ("A","狮子"),
        XM("B","熊猫"),
        HZ("C","猴子"),
        TZ("D","兔子");

        private final String typeId;

        private final String typeName;

        private TypeEnum(final String typeId,String typeName){
            this.typeId = typeId;
            this.typeName = typeName;
        }

        public static String getTypeName(String typeId) {
            TypeEnum[] typeEnums = values();
            for (TypeEnum typeEnum : typeEnums) {
                if (typeEnum.typeId().equals(typeId)) {
                    return typeEnum.typeName();
                }
            }
            return null;
        }

        public String  typeId(){
            return this.typeId;
        }

        public String typeName(){
            return this.typeName;
        }
    }
}

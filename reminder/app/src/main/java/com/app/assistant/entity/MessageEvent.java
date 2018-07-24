package com.app.assistant.entity;

/**
 * author: zhanghe
 * created on: 2018/7/11 10:39
 * description:
 */

public class MessageEvent {

    private int id;
    private Object object;

    public MessageEvent(int id, Object object) {
        this.id = id;
        this.object = object;
    }

    public MessageEvent() {
    }

    public int getId() {
        return id;
    }

    public Object getObject() {
        return object;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObject(Object object) {
        this.object = object;
    }


    public static class IdPool {
        public static int ALARM_ID = 1;
        public static int HOME_TASK_UPDATE_ID = 2;
        public static int HOME_CLOCK_UPDATE_ID = 3;

        public static int HOME_MEMO_SHOW = 5;
        public static int HOME_CLOCK_SHOW = 6;
        public static int HOME_TASK_SHOW = 7;
        public static int HOME_MEMO_FLIP_LEFT = 8;
        public static int HOME_MEMO_FLIP_RIGHT = 9;
        public static int HOME_SETTING = 10;
    }
}

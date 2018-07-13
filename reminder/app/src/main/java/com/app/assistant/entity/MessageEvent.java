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
    }
}

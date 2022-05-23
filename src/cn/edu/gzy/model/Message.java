package cn.edu.gzy.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 微博消息类，封装微博数据
 */
public class Message {
    private String phone;
    private Long millis;
    private String msg;
    public Message(){}

    public Message(String phone, Long millis, String msg) {
        this.phone = phone;
        this.millis = millis;
        this.msg = msg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取微博发表的时间
     * @return
     */
    public String getLocalDateTime(){
        LocalDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String msgTime = dateTime.format(formatter);
        return msgTime;
    }
}

package com.caring.service.im;

import com.caring.service.CaringServiceException;

/**
 *
 * @author james
 */
public class IMExchangeMessage {

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String VOICE = "voice";
    public static final String VIDEO = "video";
    public static final String FILE = "file";

    private String username;
    private String password;
    private String friendname;
    private String type;
    private String localpath;
    private String message;
    private String openid;
    private Long duration;

    public static IMExchangeMessage create(String message) {
        IMExchangeMessage sendMessage = new IMExchangeMessage();
        sendMessage.setType(TEXT);
        sendMessage.setMessage(message);
        return sendMessage;
    }

    public static IMExchangeMessage create(String type, String value) {
        type = String.valueOf(type);
        IMExchangeMessage sendMessage = new IMExchangeMessage();
        switch (type) {
            case TEXT:
                sendMessage.setType(TEXT);
                sendMessage.setMessage(value);
                break;
            case IMAGE:
            case VOICE:
            case VIDEO:
            case FILE:
                sendMessage.setType(type);
                sendMessage.setLocalpath(value);
                break;
            default:
                throw new CaringServiceException("不支持的类型 '" + type + "'");
        }
        return sendMessage;
    }

    public IMExchangeMessage withType(String type) {
        this.setType(type);
        return this;
    }

    public IMExchangeMessage withDuration(long duration) {
        this.setDuration(duration);
        return this;
    }

    public boolean isText() {
        return TEXT.equals(type);
    }

    public boolean isVoice() {
        return VOICE.equals(type);
    }

    public boolean isImage() {
        return IMAGE.equals(type);
    }

    public boolean isVideo() {
        return VIDEO.equals(type);
    }

    public boolean isFile() {
        return FILE.equals(type);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}

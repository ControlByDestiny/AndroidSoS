package com.sos.www.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EmergencyContact {
    @Id( autoincrement = true)
    private long id = 0;
    @Property
    private String name;
    @Property
    private String telNumber;
    @Property
    private String msgContent;
    @Generated(hash = 1740566414)
    public EmergencyContact(long id, String name, String telNumber,
            String msgContent) {
        this.id = id;
        this.name = name;
        this.telNumber = telNumber;
        this.msgContent = msgContent;
    }
    @Generated(hash = 1556367735)
    public EmergencyContact() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelNumber() {
        return this.telNumber;
    }
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
    public String getMsgContent() {
        return this.msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}

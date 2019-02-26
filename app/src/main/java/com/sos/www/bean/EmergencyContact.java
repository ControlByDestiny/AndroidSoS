package com.sos.www.bean;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EmergencyContact {
    @Id( autoincrement = true)
    private Long id;
    @Property
    private String name;
    @Property
    private String telNumber;
    @Property
    private String msgContent;
    @Generated(hash = 2132213621)
    public EmergencyContact(Long id, String name, String telNumber,
            String msgContent) {
        this.id = id;
        this.name = name;
        this.telNumber = telNumber;
        this.msgContent = msgContent;
    }
    @Generated(hash = 1556367735)
    public EmergencyContact() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
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

    @NonNull
    @Override
    public String toString() {
        return "ID:"+this.getId()+"-NAME:"+this.getName()+"-TEL:"+this.getTelNumber()+"-MSG:"+this.getMsgContent();
    }
}

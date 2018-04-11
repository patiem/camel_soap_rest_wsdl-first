package com.benczykuadama.testdrive.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement(name = "time")
@XmlAccessorType(XmlAccessType.FIELD)
public class Time {

    @XmlAttribute
    private Integer id;

    @XmlAttribute
    private LocalDateTime now;

    @XmlAttribute
    private String message;

    @XmlAttribute
    private String lol;

    public Time() {
    }

    public Time(Integer id, String message, LocalDateTime now) {
        this.id = id;
        this.message = message;
        this.now = now;
        lol = "SROL";

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLol() {
        return lol;
    }

    public void setLol(String lol) {
        this.lol = lol;
    }
}

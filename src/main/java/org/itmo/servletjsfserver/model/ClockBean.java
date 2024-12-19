package org.itmo.servletjsfserver.model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named("clockBean")
@RequestScoped
public class ClockBean implements Serializable {
    private String currentTime;

    public ClockBean() {
        updateTime();
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        setCurrentTime(dateFormat.format(new Date()));
    }
}

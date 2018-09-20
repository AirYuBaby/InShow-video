package com.InShowVideo.pojo;

import java.io.Serializable;
import javax.persistence.*;
 
@Table(name = "`users_fans`")
public class UsersFans implements Serializable {
	@Id
    private String id;

    private String userId;

    private String fanId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId;
    }
}
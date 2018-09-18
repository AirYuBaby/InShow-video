package com.InShowVideo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`videos_topic`")
public class VideosTopic implements Serializable {
    private String id;

    private String userId;

    private String topicId;

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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
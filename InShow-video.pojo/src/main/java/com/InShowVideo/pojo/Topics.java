package com.InShowVideo.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
 
@Table(name = "`topics`")
public class Topics implements Serializable {
    private String id;

    private String userId;

    private String topicName;

    private String topicDesc;

    private String coverPath;

    private Long participationCounts;

    private Integer status;

    private Date createTime;

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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Long getParticipationCounts() {
        return participationCounts;
    }

    public void setParticipationCounts(Long participationCounts) {
        this.participationCounts = participationCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.InShowVideo.pojo;
 
import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="用户对象",description="这是用户对象")
@Table(name = "`users`")
public class Users implements Serializable {
	@Id
	@ApiModelProperty(hidden=true)
    private String id;
	
    private String openid;
    @ApiModelProperty(value="用户名",name="username",example="Eriyeur",required=true)
    private String username;
    @ApiModelProperty(hidden=true)
    private Integer gender;
    @ApiModelProperty(hidden=true)
    private String avatarurl;
    @ApiModelProperty(hidden=true)
    private String country;
    @ApiModelProperty(hidden=true)
    private String province;
    @ApiModelProperty(hidden=true)
    private String city;
    
    private String nickname;
    @ApiModelProperty(hidden=true)
    private Integer fansCounts;
    @ApiModelProperty(hidden=true)
    private Integer followCounts;
    @ApiModelProperty(hidden=true)
    private Integer receiveLikeCounts;
    @ApiModelProperty(hidden=true)
    private Integer reportCounts;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

    public Integer getReportCounts() {
        return reportCounts;
    }

    public void setReportCounts(Integer reportCounts) {
        this.reportCounts = reportCounts;
    }
}

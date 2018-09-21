package com.InShowVideo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`bgm`")
public class Bgm implements Serializable {
	@Id
    private String id;

    private String author;

    private String name;

    private String path;

    private Integer chooseCount;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getChooseCount() {
        return chooseCount;
    }

    public void setChooseCount(Integer chooseCount) {
        this.chooseCount = chooseCount;
    }
}
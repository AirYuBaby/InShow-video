package com.InShowVideo.pojo;

import java.io.Serializable;
import javax.persistence.*;
 
@Table(name = "`bgm_copy`")
public class BgmCopy implements Serializable {
    private String id;

    private String author;

    private String name;

    private String path;

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
}
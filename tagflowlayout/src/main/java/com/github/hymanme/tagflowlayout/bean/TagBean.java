package com.github.hymanme.tagflowlayout.bean;

/**
 * /**
 * Author   :hyman
 * Email    :hymanme@163.com
 * Create at 2016/9/4
 * Description:
 */
public class TagBean {
    private int id;
    private String name;
    private Object tag;

    public TagBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}

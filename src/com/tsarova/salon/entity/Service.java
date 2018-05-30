package com.tsarova.salon.entity;

import com.tsarova.salon.entity.Entity;

import java.sql.Date;

public class Service implements Entity {
    private Long id;
    private String name;
    private String content;
    private String picture;
    private double price;
    private boolean status;


    public Service(Long id, String picture) {
        this.id = id;
        this.picture = picture;
    }

    public Service(Long id, String name, String content, double price) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
    }

    public Service(Long id, String name, String content, double price, String picture) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.picture = picture;
    }


    public Service(String name, String content, double price) {
        this.name = name;
        this.content = content;
        this.price = price;
    }


    public Service(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}

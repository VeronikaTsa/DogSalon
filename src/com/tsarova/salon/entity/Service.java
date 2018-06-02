package com.tsarova.salon.entity;

/**
 * @author Veronika Tsarova
 */
public class Service implements Entity {
    private Long id;
    private String name;
    private String content;
    private String picture;
    private double price;

    public Service(){}

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

    public Service(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (Double.compare(service.getPrice(), getPrice()) != 0) return false;
        if (getId() != null ? !getId().equals(service.getId()) : service.getId() != null) return false;
        if (getName() != null ? !getName().equals(service.getName()) : service.getName() != null) return false;
        if (getContent() != null ? !getContent().equals(service.getContent()) : service.getContent() != null)
            return false;
        return getPicture() != null ? getPicture().equals(service.getPicture()) : service.getPicture() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                '}';
    }
}
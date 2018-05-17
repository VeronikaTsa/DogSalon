package com.tsarova.salon.entity;

public class Register implements Entity {
    private Long registerId;
    private Long userId;
    private Long serviceId;
    private String registerDate;
    private String registerTime;
    private String createTime;

    public Register(Long registerId, Long userId, Long serviceId, String registerDate, String registerTime,
                    String createTime) {
        this.registerId = registerId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Register)) return false;

        Register register = (Register) o;

        if (!getRegisterId().equals(register.getRegisterId()) || !getUserId().equals(register.getUserId()) ||
                !getServiceId().equals(register.getServiceId()) ||
                !getRegisterDate().equals(register.getRegisterDate()) ||
                !getRegisterTime().equals(register.getRegisterTime())) return false;
        return getCreateTime() != null ? getCreateTime().equals(register.getCreateTime()) :
                register.getCreateTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getRegisterId().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + getServiceId().hashCode();
        result = 31 * result + getRegisterDate().hashCode();
        result = 31 * result + getRegisterTime().hashCode();
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Register{" +
                "registerId=" + registerId +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", registerDate='" + registerDate + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}

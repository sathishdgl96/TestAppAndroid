package com.example.urlconnection;

public class ProfileModel
{
    String name;
    String organization;
    String phone;

    public ProfileModel(String name, String organization, String phone) {
        this.name = name;
        this.organization = organization;
        this.phone = phone;
    }

    public ProfileModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

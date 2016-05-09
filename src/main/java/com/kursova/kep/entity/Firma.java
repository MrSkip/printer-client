package com.kursova.kep.entity;

/**
 * by Mr Skip on 31.03.2016.
 */
public class Firma extends BaseWithName {

    private String country;
    private String address;
    private String phoneNumber;
    private String pibManager;
    private String email;
    private String webSite;

    public Firma() {
    }

    public Firma(Long id,
                 String name,
                 String country,
                 String address,
                 String phoneNumber,
                 String pibManager,
                 String email,
                 String webSite
    ) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.pibManager = pibManager;
        this.email = email;
        this.webSite = webSite;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPibManager() {
        return pibManager;
    }

    public void setPibManager(String pibManager) {
        this.pibManager = pibManager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}

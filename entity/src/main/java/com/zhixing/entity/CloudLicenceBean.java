package com.zhixing.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class CloudLicenceBean {
    @NotNull
    @Unique
    private String licence;

    @Generated(hash = 964887611)
    public CloudLicenceBean(@NotNull String licence) {
        this.licence = licence;
    }

    @Generated(hash = 252519146)
    public CloudLicenceBean() {
    }

    public String getLicence() {
        return this.licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}

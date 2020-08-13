package com.zhixing.entity.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 授权
 */
public class ConfigurationBean implements Serializable {
    public Date endDate;
    public int type;
    public boolean isForever;
}

package com.companyname.framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MicroService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 获取数据源的Key
     * @return
     */
    public String getDbKey(){
        return "";
    }
}

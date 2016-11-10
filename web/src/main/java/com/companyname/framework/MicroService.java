package com.companyname.framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MicroService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
}

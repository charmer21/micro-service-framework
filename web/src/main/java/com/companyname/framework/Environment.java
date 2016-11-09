package com.companyname.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 环境变量
 */
public class Environment {

    private static final Logger logger = LoggerFactory.getLogger(Environment.class);

    /**
     * 本机IP地址
     */
    public static String LOCAL_IP_ADDR = "";

    static {
        try {
            InetAddress address = InetAddress.getLocalHost();
            LOCAL_IP_ADDR = address.getHostAddress();
        } catch (UnknownHostException ex) {
            logger.error("获取本机IP失败 -> " + ex.getMessage());
        }
    }

}

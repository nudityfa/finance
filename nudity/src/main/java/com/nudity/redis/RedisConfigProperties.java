package com.nudity.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Copyright: Copyright (C) 2022 XXX, Inc. All rights reserved. <p>
 * Company: 霉运有限公司<p>
 *
 * @author luojinhua
 * @since 2022/5/3 14:50
 */
@Component
@ConfigurationProperties(prefix = "spring-cluster")
public class RedisConfigProperties {

    private String password;

    private int database;

    private String masterAddress;

    private Set<String> slaveAddress;

    public RedisConfigProperties() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public Set<String> getSlaveAddress() {
        return slaveAddress;
    }

    public void setSlaveAddress(Set<String> slaveAddress) {
        this.slaveAddress = slaveAddress;
    }
}

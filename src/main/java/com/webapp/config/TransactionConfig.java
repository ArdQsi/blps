package com.webapp.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.SystemException;

@Configuration
public class TransactionConfig {

    @Bean
    public bitronix.tm.Configuration transactionManagerServices() {
        bitronix.tm.Configuration configuration = TransactionManagerServices.getConfiguration();
        configuration.setServerId("1");
        return configuration;
    }


    @Bean(name = "bitronixTransactionManager")
    public BitronixTransactionManager transactionManager(bitronix.tm.Configuration _c){
        BitronixTransactionManager tm = TransactionManagerServices.getTransactionManager();
        try {
            tm.setTransactionTimeout(60);
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
        return tm;

    }
}

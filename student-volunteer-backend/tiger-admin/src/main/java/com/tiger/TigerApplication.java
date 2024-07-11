package com.tiger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author tiger
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },scanBasePackages = "com.tiger")
public class TigerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TigerApplication.class, args);
    }
}

package com.phyl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Key;
import java.util.Properties;

/**
 * Created by xh on 2017/4/7.
 */
@RestController
public class TestController {
    /**
     * 第一种方式 value("${name}")
     */
    @Value("${authname}")
    private String authname;

    @RequestMapping("getname")
    public String getAuthname() {
        return "my name is " + authname;
    }

    /**
     * 第二种方式直接注入Environment，通过environment.getProperty("name")来获取属性
     */
    @Autowired
    Environment environment;

    @RequestMapping("get")
    public String getname() {
        return environment.getProperty("net.ebh");
    }

    /**
     * 第三种直接获取配置文件
     */
    public String getProperties(String key) {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("getfile")
    public String getFile() {
        String authname = getProperties("authname");
        String ebh = getProperties("net.ebh");
        return authname + "--" + ebh;
    }

    /**
     * 获取自定义文件中的方法
     */
    @Autowired
    User user;

    @RequestMapping("getproperties")
    private String getMyproperties() {
        return user.getName() + "--" + user.getSex() + "--" + user.getAge();
    }
}

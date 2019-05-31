package com.zhaoming.test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 代码自动生成
 *
 * @author hhx
 */
public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        mpg.setGlobalConfig(new GlobalConfig()
                .setAuthor("system")
                .setOutputDir(System.getProperty("user.dir") + "/service-parent/test-service/src/main/java")
                // 是否覆盖同名文件，默认是false
                .setFileOverride(false)
                // 不需要ActiveRecord特性的请改为false
                .setActiveRecord(true)
                // XML 二级缓存
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columnList
                .setBaseColumnList(false));

        // 数据源配置
        mpg.setDataSource(new DataSourceConfig()
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("jbt1234")
                .setUrl("jdbc:mysql://47.104.192.107:3306/base_data?useSSL=false&characterEncoding=utf-8"));

        // 策略配置
        mpg.setStrategy(new StrategyConfig()
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel));

        // 父包配置
        mpg.setPackageInfo(new PackageConfig()
                .setXml("/")
                .setParent("com.zhaoming.service.test"));

        mpg.execute();
    }
}

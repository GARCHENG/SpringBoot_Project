package com.gec.hawaste.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@MapperScan("com.gec.hawaste.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                //lombok生成的 日志对象  可以查看编译后代码得到
               // log.info("start insert fill ....");
                //注意日期格式
                this.setFieldValByName("createDate", LocalDateTime.now(), metaObject);//可以设置数据库默认值替代该配置
                this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);//可以设置数据库默认值替代该配置
                this.setFieldValByName("delFlag", "0", metaObject);//可以设置数据库默认值替代该配置
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                //log.info("start update fill ....");
                this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
            }
        };
    }

}

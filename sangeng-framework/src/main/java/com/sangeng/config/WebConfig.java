package com.sangeng.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    // 配置 fastjson 转换
    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverters(){
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new
                FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonconfig =  new FastJsonConfig();
        fastJsonconfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonconfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        SerializeConfig.globalInstance.put(Long.class,
                ToStringSerializer.instance);
        fastJsonconfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonconfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}

package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import com.sky.json.JacksonObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 通过knife4j生成接口文档
     * 
     * @return
     */
    @Bean
    public OpenAPI docket() {
        log.info("准备生成接口文档……");
        return new OpenAPI().info(new Info()
                .title("苍穹外卖项目接口文档")
                .version("2.0")
                .description("苍穹外卖项目接口文档"));
    }

    /**
     * 设置静态资源映射
     * 
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射……");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // 扩展spring mvc框架消息转化器

    /**
     * 扩展消息转换器，补齐完整 converter 链
     * 注意：本类 extends WebMvcConfigurationSupport，会绕过 Boot 默认 WebMvc 自动装配，
     * 所以默认的 HttpMessageConverter 全部丢失，需要在这里手动补齐。
     * 关键点：必须包含 ByteArrayHttpMessageConverter，否则 /v3/api-docs 会以 Base64 编码响应
     * 参考：https://doc.xiaominfo.com/docs/faq/v4/knife4j-base64-response
     * 参考：https://github.com/springdoc/springdoc-openapi/issues/2143
     */
    @Override
    @SuppressWarnings("removal")
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器……");

        // 1) byte[] —— 解决 knife4j + springdoc OpenAPI 接口 Base64 响应问题
        converters.add(new ByteArrayHttpMessageConverter());

        // 2) 静态资源（Resource、InputStream）
        converters.add(new ResourceHttpMessageConverter());

        // 3) String / text/html / JSON 字符串
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(List.of(
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_HTML,
                MediaType.APPLICATION_JSON,
                MediaType.ALL
        ));
        converters.add(stringConverter);

        // 4) application/x-www-form-urlencoded 与 multipart/form-data
        converters.add(new AllEncompassingFormHttpMessageConverter());

        // 5) 自定义 Jackson2 converter（仅承接 application/json 与 application/*+json）
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(new JacksonObjectMapper());
        jacksonConverter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                new MediaType("application", "*+json")
        ));
        converters.add(0,jacksonConverter);
    }
}

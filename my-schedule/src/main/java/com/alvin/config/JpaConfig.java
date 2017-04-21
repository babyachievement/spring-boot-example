package com.alvin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Administrator on 2017/4/20.
 */
@Configuration
@EnableJpaAuditing // 不能把这个注解用在MyScheduleApplication上，否则使用了@WebMvcTest的HomeControllerTest测试无法通过
/**
 * 出现At least one JPA metamodel must be present错误
 * 参考https://github.com/spring-projects/spring-boot/issues/6844
 * http://stackoverflow.com/questions/41250177/getting-at-least-one-jpa-metamodel-must-be-present-with-webmvctest
 */
public class JpaConfig {
}

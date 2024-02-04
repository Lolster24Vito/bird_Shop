package hr.algebra.bird_shop.configuration;

import hr.algebra.bird_shop.filter.RequestResponseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    // uncomment this and comment the @Component in the filter class definition to register only for a url pattern
    @Bean
    public FilterRegistrationBean<RequestResponseFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestResponseFilter());

        //registrationBean.addUrlPatterns("/login/*");
        registrationBean.setOrder(2);

        return registrationBean;

    }
}

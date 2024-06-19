package com.example.demo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer{
   @Override
    public void addFormatters( @SuppressWarnings("null") FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
    @Override
    public void configureArgumentResolvers(@SuppressWarnings("null") ArgumentResolverConfigurer configurer) {
        var pageableResolver = new ReactivePageableHandlerMethodArgumentResolver();

        pageableResolver.setMaxPageSize(Integer.MAX_VALUE);
        pageableResolver.setFallbackPageable(PageRequest.of(0, 2));

        configurer.addCustomResolver(pageableResolver);
    }
}


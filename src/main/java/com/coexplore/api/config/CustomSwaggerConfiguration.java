package com.coexplore.api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class CustomSwaggerConfiguration {

    public CustomSwaggerConfiguration(@Qualifier("swaggerSpringfoxApiDocket") Docket docket) {
        docket.apiInfo(apiInfo()).globalOperationParameters(getOperationParameters());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Co-Explore API", 
                "API page for Co-Explore", 
                "Version 1.0.0", 
                "Terms of service", 
                new Contact("Nguyen Hai Duy", "", "nguyenhaiduy2894@gmail.com"), 
                "License of API", "API license URL", Collections.emptyList());    }

    private List<Parameter> getOperationParameters() {
        List<Parameter> operationParameters = new ArrayList<>();

        operationParameters.add(new ParameterBuilder().name("Authorization").description("Bearer your_token ").modelRef(new ModelRef("string")).parameterType("header").build());

        return operationParameters;
    }
}

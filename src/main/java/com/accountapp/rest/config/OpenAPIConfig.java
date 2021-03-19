package com.accountapp.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenApi() {
        SecurityScheme securitySchemeItem = new SecurityScheme();
        securitySchemeItem.setType(SecurityScheme.Type.APIKEY);
        securitySchemeItem.setScheme("bearer");
        securitySchemeItem.setBearerFormat("JWT");
        securitySchemeItem.setIn(SecurityScheme.In.HEADER);
        securitySchemeItem.setName("Authorization");
        Info infoVersion = new Info()
                .title("Accounting App API Application")
                .version("V1")
                .description("API end points for Accounting App");
        SecurityRequirement securityItem = new SecurityRequirement()
                .addList("bearer-jwt", Arrays.asList("read", "write"));

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", securitySchemeItem))
                .info(infoVersion)
                .addSecurityItem(securityItem);

    }

    @Bean
    public OpenApiCustomiser sortTagsAlphabetically() {
        return openApi -> openApi.setTags(openApi.getTags()
                .stream()
                .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
                .collect(Collectors.toList()));
    }
}

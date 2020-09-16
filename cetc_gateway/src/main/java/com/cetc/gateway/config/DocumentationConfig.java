package com.cetc.gateway.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("cetc-admin", "/cetc-admin/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-auth", "/cetc-auth/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-dic", "/cetc-dic/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-log", "/cetc-log/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-transfer", "/cetc-transfer/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-activiti", "/cetc-activiti/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-notice", "/cetc-notice/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-filecenter", "/cetc-filecenter/v2/api-docs", "1.0.0"));
        resources.add(swaggerResource("cetc-gateway", "/v2/api-docs", "1.0.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}

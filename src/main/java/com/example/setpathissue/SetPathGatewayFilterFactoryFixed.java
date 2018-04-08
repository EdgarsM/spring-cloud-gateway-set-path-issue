package com.example.setpathissue;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.pattern.PathPattern;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Component
public class SetPathGatewayFilterFactoryFixed extends AbstractGatewayFilterFactory<SetPathGatewayFilterFactoryFixed.Config> {

    public static final String TEMPLATE_KEY = "template";

    public SetPathGatewayFilterFactoryFixed() {
        super(SetPathGatewayFilterFactoryFixed.Config.class);
    }

    @Override
    public String name() {
        return "SetPathFixed";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(TEMPLATE_KEY);
    }

    @Override
    public GatewayFilter apply(SetPathGatewayFilterFactoryFixed.Config config) {
        UriTemplate uriTemplate = new UriTemplate(config.template);

        return (exchange, chain) -> {
            PathPattern.PathMatchInfo variables = exchange.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            ServerHttpRequest req = exchange.getRequest();
            addOriginalRequestUrl(exchange, req.getURI());
            Map<String, String> uriVariables;

            if (variables != null) {
                uriVariables = variables.getUriVariables();
            } else {
                uriVariables = Collections.emptyMap();
            }

            URI uri = uriTemplate.expand(uriVariables);
            // Use encoded path
            String newPath = uri.getRawPath();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);

            ServerHttpRequest request = req.mutate()
                    .path(newPath)
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private String template;

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }
    }
}

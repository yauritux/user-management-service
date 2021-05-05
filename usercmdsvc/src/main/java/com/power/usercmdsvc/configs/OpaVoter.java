package com.power.usercmdsvc.configs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class OpaVoter implements AccessDecisionVoter<FilterInvocation> {

    private static final String URI = "http://localhost:8181/v1/data/authz/allow";
    private static final Logger LOG = LoggerFactory.getLogger(OpaVoter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> collection) {
        String name = authentication.getName();
        LOG.info("OpaVoter::name=" + name);
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableList());
        String method = filterInvocation.getRequest().getMethod();
        String[] path = filterInvocation.getRequest().getRequestURI().replaceAll("^/|/$", "").split("/");

        Map<String, String> headers = new HashMap<>();
        for (Enumeration<String> headerNames = filterInvocation.getRequest().getHeaderNames(); headerNames.hasMoreElements();) {
            String header = headerNames.nextElement();
            headers.put(header, filterInvocation.getRequest().getHeader(header));
        }

        Map<String, Object> input = Map.of(
                "auth", authentication,
                "roles", authentication.getAuthorities(),
                "name", name,
                "authorities", authorities,
                "method", method,
                "path", path,
                "headers", headers
        );

        ObjectNode requestNode = objectMapper.createObjectNode();
        requestNode.set("input", objectMapper.valueToTree(input));
        LOG.info("Authorization request:\n" + requestNode.toPrettyString());

        JsonNode responseNode = Objects.requireNonNull(restTemplate.postForObject(URI, requestNode, JsonNode.class));
        LOG.info("Authorization response:\n" + responseNode.toPrettyString());

        if(responseNode.has("result") && responseNode.get("result").asBoolean()) {
            return ACCESS_GRANTED;
        } else {
            return ACCESS_DENIED;
        }
    }
}

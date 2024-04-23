package com.example.eventapigateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class AddressUtils {

    private final DiscoveryClient discoveryClient;

    public AddressUtils(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public URI getInstances(String serviceId) {
        return discoveryClient.getInstances(serviceId).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("service not found"))
                .getUri();
    }
}

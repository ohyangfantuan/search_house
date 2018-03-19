package com.oywy.core.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by oywy on 2018/3/18.
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public TransportClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();

        InetSocketTransportAddress address = new InetSocketTransportAddress(InetAddress.getByName("192.168.65.13"), 9300);

        return new TransportClient.Builder().settings(settings).build().addTransportAddress(address);
    }
}

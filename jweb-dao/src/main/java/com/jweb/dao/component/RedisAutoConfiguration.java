package com.jweb.dao.component;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
public class RedisAutoConfiguration {
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonSingle() {

        RedissonClient redissonClient = null;

        try {
            Config config = new Config();

            config.setCodec(new JsonJacksonCodec());

            if (redisProperties.getIsCluster() == 1) {
            	logger.info("redisson cluster start");
                String[] nodes = redisProperties.getAddress().split(",");
                ClusterServersConfig serverConfig = config.useClusterServers();
                serverConfig.addNodeAddress(nodes);
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redisProperties.getPingConnectionInterval());
                serverConfig.setPingTimeout(redisProperties.getPingTimeout());
                serverConfig.setTimeout(redisProperties.getTimeout());
                serverConfig.setConnectTimeout(redisProperties.getConnectTimeout());
                if (redisProperties.getPassword() != null && !"".equals(redisProperties.getPassword())) {
                    serverConfig.setPassword(redisProperties.getPassword());
                }
            } else {
            	logger.info("redisson single start");
                SingleServerConfig serverConfig = config.useSingleServer()
                        .setAddress(redisProperties.getAddress())
                        .setDatabase(redisProperties.getDatabase());
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redisProperties.getPingConnectionInterval());
                serverConfig.setPingTimeout(redisProperties.getPingTimeout());
                serverConfig.setTimeout(redisProperties.getTimeout());
                serverConfig.setConnectTimeout(redisProperties.getConnectTimeout());
                serverConfig.setConnectionMinimumIdleSize(redisProperties.getConnectionMinimumIdleSize());

                serverConfig.setConnectionPoolSize(redisProperties.getConnectionPoolSize());

                if (redisProperties.getPassword() != null && !"".equals(redisProperties.getPassword())) {
                    serverConfig.setPassword(redisProperties.getPassword());
                }
            }


            redissonClient = Redisson.create(config);

            logger.info("redisson start success");
        } catch (Exception e) {
        	logger.info("redisson start fail");
        	logger.error("", e);
        }

        return redissonClient;
    }

}

package com.kvs.memcachedb.Config;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MemcacheConfig {

    @Bean
    public MemCachedClient mcc(){
        String[] servers = {"localhost:11211"};
        SockIOPool pool = SockIOPool.getInstance("memcached");
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        return new MemCachedClient("memcached");
    }

    @Bean
    public List<String> allSights() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/sight_names.txt")))){
            String line = null;
            while ((line = reader.readLine()) != null){
                result.add(line);
            }
        }

        return result;
    }
}

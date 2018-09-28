package com.kvs.memcachedb.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.HashMap;

public class MemcachedExample {
    public static String startExample() {
        String[] servers = {"localhost:11211"};
        SockIOPool pool = SockIOPool.getInstance("memcached");
        pool.setServers( servers );
        pool.setFailover( true );
        pool.setInitConn( 10 );
        pool.setMinConn( 5 );
        pool.setMaxConn( 250 );
        pool.setMaintSleep( 30 );
        pool.setNagle( false );
        pool.setSocketTO( 3000 );
        pool.setAliveCheck( true );
        pool.initialize();
        MemCachedClient mcc = new MemCachedClient("memcached");
        System.out.println("add status:"+mcc.add("1", "Original"));
        System.out.println("Get from Cache:"+mcc.get("1"));

        System.out.println("add status:"+mcc.add("1", "Modified"));
        System.out.println("Get from Cache:"+mcc.get("1"));

        System.out.println("set status:"+mcc.set("1","Modified"));
        System.out.println("Get from Cache after set:"+mcc.get("1"));

        System.out.println("remove status:"+mcc.delete("1"));
        System.out.println("Get from Cache after delete:"+mcc.get("1"));

        mcc.set("2", "2");
        mcc.set("3", "3");
        mcc.set("4", "4");
        mcc.set("5", "5");
        String [] keys = {"1", "2","3","INVALID","5"};
        HashMap<String,Object> hm = (HashMap<String, Object>) mcc.getMulti(keys);

        String result = "";
        for(String key : hm.keySet()){
            result += "KEY:"+key+" VALUE:"+hm.get(key) + "\n";
        }

        return result;
    }
}

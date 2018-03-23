package com.example.weather.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EhCacheConfig implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(EhCacheConfig.class);

    private static CacheManager cacheManager;
    
    public static void setCacheManager(CacheManager cacheManager){
        EhCacheConfig.cacheManager = cacheManager;
    }

    public void cache(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new NullPointerException("Failed to obtain cache: " + cacheName);
        }
        Element element = new Element(key,value);
        cache.put(element);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("\n\n Entered Command line runner");
        if(args.length > 0){
            if( args[0].equalsIgnoreCase("clear"))
            cacheManager.getCache("windInfo").setDisabled(true);
      }
    }
}

package controller;

import Entity.Client;
import Repository.Download;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Stateless
public class LRUCacheImp {
    @EJB
    private Download data;


    Deque<Cache> q = new LinkedList<>();
    Map<Integer, Cache> map = new HashMap<Integer, Cache>();
    int CACHE_SIZE = 3;


    public Client getFromCache(int key) throws SQLException {
        if (map.containsKey(key)) {
            Cache cache = map.get(key);
            q.remove(cache);
            q.addFirst(cache);
            System.out.println("---- GET FROM CACHE ----");
            return cache.client;
        } else {
            Client client = this.data.GetClientByID(key);
            PutIntoCache(key, client);
            System.out.println("---- GET FROM DATABASE ----");
            return client;
        }
    }

    public void PutIntoCache(int key, Client client) {
        if (map.containsKey(key)) {
            Cache cache = map.get(key);
            q.remove(cache);
        } else {
            if (q.size() == CACHE_SIZE) {
                Cache cacheToBeRemoved = q.removeLast();
                map.remove(cacheToBeRemoved.key);
            }
        }
        Cache newCache = new Cache(key, client);
        q.addFirst(newCache);
        map.put(key, newCache);
    }
}
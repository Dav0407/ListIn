package com.igriss.ListIn.security.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingService {

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();
    private final Map<String, Long> timeWindow = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 40;
    private static final long TIME_WINDOW = 60000;

    public boolean isRateLimitExceeded(String clientIp){
        //getting requested time
        long currentTime = System.currentTimeMillis();

        //and then we will put that client ip addr into concurrent map with the value requested time
        requestTimestamps.putIfAbsent(clientIp, currentTime);

        //setting default time window for each user as 1 min
        timeWindow.putIfAbsent(clientIp, TIME_WINDOW);

        //on each request we will calculate the diff between when user requested and the current time
        long timeElapsed = currentTime - requestTimestamps.get(clientIp);

        //if user tries to request after 1min we will set the counts to 0,
        // and also we will set the new currentTime for requestTimestamps
        if (timeElapsed > timeWindow.get(clientIp)) {
            requestCounts.put(clientIp, 0);
            requestTimestamps.put(clientIp, currentTime);
        }

        //we will put the counts into requestCounts with the key clientIp and value counts
        requestCounts.put(clientIp,requestCounts.getOrDefault(clientIp,0) + 1);

        int count = requestCounts.get(clientIp);

        //if user tries to request even after it is blocked
        //then the unblocking that user will grow dynamically
        if (count == 3*MAX_REQUESTS)
            timeWindow.put(clientIp, 5*TIME_WINDOW);
        else if (count == 5*MAX_REQUESTS)
            timeWindow.put(clientIp, 60*TIME_WINDOW);
        else if (count == 60*MAX_REQUESTS)
            timeWindow.put(clientIp, 1440*TIME_WINDOW);

        //if the number of requests exceeded we will return true
        return count > MAX_REQUESTS;
    }

}

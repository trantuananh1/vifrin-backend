package com.vifrin.feed.service;

import com.vifrin.common.util.RedisUtil;
import org.springframework.stereotype.Service;

/**
 * @author: trantuananh1
 * @since: Fri, 10/12/2021
 **/

@Service
public class AuthService {
    public String getAccessToken(String username){
        return RedisUtil.getInstance().getValue(username);
    }
}

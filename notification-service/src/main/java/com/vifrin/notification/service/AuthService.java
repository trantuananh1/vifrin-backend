package com.vifrin.notification.service;

import com.vifrin.common.util.RedisUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Service
public class AuthService {
    @Transactional(readOnly = true)
    public String getCurrentUser() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getAccessToken(){
        return RedisUtil.getInstance().getValue(getCurrentUser());
    }
}

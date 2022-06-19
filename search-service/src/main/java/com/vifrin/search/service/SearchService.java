package com.vifrin.search.service;

import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.feign.client.DestinationFeignClient;
import com.vifrin.feign.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tranmanhhung
 * @since: Mon, 20/12/2021
 **/

@Service
@Slf4j
public class SearchService {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    DestinationFeignClient destinationFeignClient;

    public Map<String, Object> search(String key, String username){
        Map<String, Object> result = new HashMap<>();
        String token = RedisUtil.getInstance().getValue(username);
        //search user
        List<UserSummary> userSummaries = userFeignClient.search(key, token).getBody();
        if (userSummaries!= null && !userSummaries.isEmpty()){
            result.put(BaseConstant.USERS, userSummaries);
        }
        //search destination
        List<DestinationDto> destinationDtos = destinationFeignClient.search(key).getBody();
        if (destinationDtos!= null && !destinationDtos.isEmpty()){
            result.put(BaseConstant.DESTINATIONS, destinationDtos);
        }
        return result;
    }
}

package com.service.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.service.UserDTO;
import com.service.UserService;
import org.dromara.soul.client.common.annotation.SoulClient;

@Service
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {
    @Override
//    @SoulClient(path = "/user/create", desc = "创建用户")
    public int createUser(UserDTO userDTO) {
        return 1;
    }

    @Override
//    @SoulClient(path = "/user/get", desc = "获得用户详细")
    public String getUser(String name) {
        return "hello";
    }
}

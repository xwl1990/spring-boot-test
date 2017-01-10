package com.ck.svc.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.dao.mapper.sys.UserMapper;
import com.ck.svc.UserSvc;

@Service
public class UserSvcImpl implements UserSvc {

    private static final Logger LOG = LoggerFactory.getLogger(UserSvcImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, Object> queryUserList() {
        LOG.info("queryUserList");
        return userMapper.queryUserList();
    }

}

	
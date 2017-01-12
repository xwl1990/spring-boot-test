package com.ck.dubbo.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ck.common.GenericResponseDto;
import com.ck.dubbo.api.UserInfoApi;
import com.ck.svc.UserSvc;
import com.ck.util.RespdUtils;

@Service("userInfoApi")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8 })
@Path("/")
public class UserInfoApiImpl implements UserInfoApi{

    @Autowired
    private UserSvc userSvc;
    @Override
    @POST
    @Path("getUser")
    public GenericResponseDto getUser(String userId) {
        GenericResponseDto resp = RespdUtils.success();
        resp.putData("user", userSvc.queryUserList());
        return resp;
    }

}

	
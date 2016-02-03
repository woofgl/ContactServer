package com.britesnow.contactsdemo.web;

import com.britesnow.contactsdemo.entity.User;
import com.britesnow.contactsdemo.perf.annotation.ToMonitor;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
@ToMonitor
public class UserWebHandler {

//    @Inject
//    private UserDao userDao;
//
//	@Inject
//	private IDao<User,Long> udao;
//
//	@Inject
//	private IDao<Ticket,Long> ticketDao;
//
    @WebGet("/test")
    public WebResponse listUser(@WebUser User user){
        Map map = new HashMap<>();
	    map.put("key", "hello REST");
        return WebResponse.success(map);
    }

}

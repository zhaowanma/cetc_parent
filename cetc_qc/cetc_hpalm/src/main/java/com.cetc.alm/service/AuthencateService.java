package com.cetc.alm.service;

import com.cetc.model.hpalm.AlmConfig;
import com.jacob.com.Dispatch;


import java.util.List;
import java.util.Map;

public interface AuthencateService {

    public List<String> getCookie(AlmConfig almConfig);

    public List<String> getSession(AlmConfig almConfig,List<String> cookieList);

    public Boolean closeSession(AlmConfig almConfig,List<String> cookieList);

    public List<String> login(AlmConfig almConfig);

    public void logout(AlmConfig almConfig,List<String> cookieList);

    public Map<String,String> findAlmUsers(Dispatch disp);

    public void addUserProject(Dispatch disp, String domainName, String projectName, String userNames);

    public void addUserGroup(Dispatch disp, String domainName, String projectName, String userNames);
}

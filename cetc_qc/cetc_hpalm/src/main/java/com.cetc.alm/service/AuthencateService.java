package com.cetc.alm.service;

import com.cetc.model.hpalm.AlmConfig;

import java.util.List;

public interface AuthencateService {

    public List<String> getCookie(AlmConfig almConfig);

    public List<String> getSession(AlmConfig almConfig,List<String> cookieList);

    public List<String> login(AlmConfig almConfig);

    public void logout(AlmConfig almConfig,List<String> cookieList);
}

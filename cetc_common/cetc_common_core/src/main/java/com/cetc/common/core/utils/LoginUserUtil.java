package com.cetc.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

public class LoginUserUtil {

    public static LoginSysUser getLoginSysUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginSysUser) {
                    return (LoginSysUser) principal;
                }
                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");
                return JSONObject.parseObject(JSONObject.toJSONString(map), LoginSysUser.class);
            }
            else{
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginSysUser) {
                    return (LoginSysUser) principal;
                }
            }
        }
        return null;
    }
}

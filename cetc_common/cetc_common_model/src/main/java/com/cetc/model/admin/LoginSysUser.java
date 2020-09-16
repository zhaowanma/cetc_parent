package com.cetc.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@ApiModel(description = "登录用户实体类")
public class LoginSysUser extends SysUser implements UserDetails {


    private static final long serialVersionUID = 7508883035172040482L;

    @ApiModelProperty(value = "用户角色集合")
    private Set<SysRole> sysRoles;

    @ApiModelProperty(value = "用户权限集合")
    private Set<String> permissions;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if(!CollectionUtils.isEmpty(sysRoles)){
            sysRoles.forEach(role->{
                if(role.getCode().startsWith("ROLE_")){
                    collection.add(new SimpleGrantedAuthority(role.getCode()));
                }else {
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
                }
            });
        }
        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(per -> {
                if(per!=null&&!"".equals(per)){
                    collection.add(new SimpleGrantedAuthority(per));
                }

            });
        }

        return collection;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

    public Set<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(Set<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}

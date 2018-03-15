package com.oywy.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oywy.entity.Role;
import com.oywy.entity.User;
import com.oywy.mapper.RoleMapper;
import com.oywy.mapper.UserMapper;
import com.oywy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oywy on 2018/3/14.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findByName(String userName) {
        //查找user
        User user = new User();
        user.setName(userName);
        user = userMapper.selectOne(user);
        //判断user非空
        if (ObjectUtil.isNull(user))
            return null;
        //查询roles
        List<Role> roles = roleMapper.selectList(new EntityWrapper<Role>().eq("user_id", user.getId()));
        //判断roles非空
        if (CollUtil.isEmpty(roles))
            throw new DisabledException("权限非法");
        //创建authority集合
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
        //添加authorities到user
        user.setAuthorities(authorities);
        return user;
    }
}

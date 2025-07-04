package org.example.collectionandrecommend.demos.web.service.impl;

import org.example.collectionandrecommend.demos.web.model.dto.UserDto;
import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.entity.UserFavor;
import org.example.collectionandrecommend.demos.web.model.vo.UserVo;
import org.example.collectionandrecommend.demos.web.service.UserService;
import org.example.collectionandrecommend.demos.web.model.entity.User;
import org.example.collectionandrecommend.demos.web.mapper.UserMapper;

import org.example.collectionandrecommend.demos.web.utils.Util.JwtUtil;
import org.example.collectionandrecommend.demos.web.utils.Util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册方法
     *
     * @param userDto 用户注册数据传输对象
     * @throws RuntimeException 当用户名或邮箱已被注册时抛出
     */
    @Override
    @Transactional  // 确保数据一致性和事务原子性
    public void register(UserDto userDto) {
        // 检查用户名或邮箱是否已被注册
        User existingUser = userMapper.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("用户名或邮箱已被注册");
        }

        // 创建新用户实体
        User user = new User();

        // 使用MD5加密密码 - 注意：生产环境应使用更安全的加密方式
        String encryptedPassword = MD5Util.md5Encrypt(userDto.getPassword());

        // 设置用户信息
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRealName(userDto.getRealName());
        user.setAvatarUrl(userDto.getAvatarUrl());

        // 设置创建和更新时间
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // 插入用户数据
        userMapper.insert(user);
    }

    /**
     * 用户登录方法
     *
     * @param userDto 用户登录数据传输对象
     * @return UserVo 登录成功的用户视图对象，包含JWT token
     * @throws RuntimeException 当用户不存在或密码错误时抛出
     */
    @Override
    public UserVo login(UserDto userDto) {
        // 根据用户名查找用户
        User user = userMapper.findByUsername(userDto.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 使用MD5加密验证密码 - 注意：生产环境应使用更安全的加密方式
        String inputEncryptedPassword = MD5Util.md5Encrypt(userDto.getPassword());

        // 验证密码
        if (!inputEncryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 创建用户视图对象
        UserVo userVo = new UserVo();

        // 复制用户信息到视图对象
        BeanUtils.copyProperties(user, userVo);

        // 生成JWT Token
        String token = JwtUtil.generateToken(user.getId());
        userVo.setToken(token);

        return userVo;
    }

    @Override
    public void addFavor(UserFavorDto userFavorDto) {
        UserFavor userFavor = new UserFavor();
        BeanUtils.copyProperties(userFavorDto,userFavor);
        userFavor.setCreatedAt(LocalDateTime.now());
        userMapper.insertFavor(userFavor);
    }

    @Override
    public List<Integer> listFavor(Integer userId) {
        List<Integer> list = userMapper.listFavor(userId);
        return list;
    }
}

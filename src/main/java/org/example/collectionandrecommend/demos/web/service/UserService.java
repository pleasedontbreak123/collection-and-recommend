package org.example.collectionandrecommend.demos.web.service;

import org.example.collectionandrecommend.demos.web.model.dto.UserDto;
import org.example.collectionandrecommend.demos.web.model.vo.UserVo;

public interface UserService {
    void register(UserDto userDto);
    UserVo login(UserDto userDto);
}

package org.example.collectionandrecommend.demos.web.service;

import org.example.collectionandrecommend.demos.web.model.dto.UserDto;
import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.vo.UserVo;

import java.util.List;

public interface UserService {
    void register(UserDto userDto);
    UserVo login(UserDto userDto);

    void addFavor(UserFavorDto userFavorDto);

    List<Integer> listFavor(Integer userId);
}

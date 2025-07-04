package org.example.collectionandrecommend.demos.web.service;

import org.example.collectionandrecommend.demos.web.mapper.UserMapper;
import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.entity.UserFavor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendService {
    private final Recommender recommender = new Recommender();
    @Autowired
    private UserMapper userMapper;

    public void init() {
        List<UserFavor> favorList = userMapper.listFavorAll();
        recommender.load(favorList);
        recommender.buildSimilarity();
    }

    public List<Integer> recommendForUser(int userId, int topK) {
        return recommender.recommend(userId, topK);
    }
}

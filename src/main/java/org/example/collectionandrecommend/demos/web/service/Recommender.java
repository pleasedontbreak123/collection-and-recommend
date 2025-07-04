package org.example.collectionandrecommend.demos.web.service;

import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.entity.UserFavor;

import java.util.*;
import java.util.stream.Collectors;

public class Recommender {
    // 输入：用户收藏数据 userId -> [eventId]
    private final Map<Integer, Set<Integer>> userFavorMap = new HashMap<>();

    // 倒排：eventId -> 用户集合
    private final Map<Integer, Set<Integer>> eventToUsers = new HashMap<>();

    // 赛事之间的相似度矩阵
    private final Map<Integer, Map<Integer, Double>> similarityMatrix = new HashMap<>();

    // 加载收藏数据
    public void load(List<UserFavor> favors) {
        for (UserFavor favor : favors) {
            userFavorMap.computeIfAbsent(favor.getUserId(), k -> new HashSet<>()).add(favor.getEventId());
            eventToUsers.computeIfAbsent(favor.getEventId(), k -> new HashSet<>()).add(favor.getUserId());
        }
    }

    // 构建相似度矩阵（余弦相似度）
    //枚举两两赛事（e1, e2）
    //
    //找到共同用户数（共现数 co）
    //
    //使用“余弦相似度”
    public void buildSimilarity() {
        for (Integer e1 : eventToUsers.keySet()) {
            similarityMatrix.putIfAbsent(e1, new HashMap<>());
            for (Integer e2 : eventToUsers.keySet()) {
                if (e1.equals(e2)) continue;

                Set<Integer> users1 = eventToUsers.get(e1);
                Set<Integer> users2 = eventToUsers.get(e2);

                // 计算共现次数
                int co = 0;
                for (Integer u : users1) {
                    if (users2.contains(u)) co++;
                }

                // 归一化（类似余弦）
                double sim = co / Math.sqrt(users1.size() * users2.size() * 1.0);
                similarityMatrix.get(e1).put(e2, sim);
            }
        }
    }

    // 推荐给某个用户
    public List<Integer> recommend(int userId, int topK) {
        Set<Integer> userEvents = userFavorMap.get(userId);
        if (userEvents == null) return Collections.emptyList();

        Map<Integer, Double> scoreMap = new HashMap<>();

        for (Integer event : userEvents) {
            Map<Integer, Double> similarEvents = similarityMatrix.getOrDefault(event, new HashMap<>());
            for (Map.Entry<Integer, Double> entry : similarEvents.entrySet()) {
                Integer target = entry.getKey();
                Double sim = entry.getValue();

                // 如果用户已收藏则不推荐
                if (userEvents.contains(target)) continue;

                scoreMap.put(target, scoreMap.getOrDefault(target, 0.0) + sim);
            }
        }

        return scoreMap.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(topK)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

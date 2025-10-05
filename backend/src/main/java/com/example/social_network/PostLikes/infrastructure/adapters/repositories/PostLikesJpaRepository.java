package com.example.social_network.PostLikes.infrastructure.adapters.repositories;

import com.example.social_network.PostLikes.infrastructure.persistance.entities.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesJpaRepository extends JpaRepository<LikeEntity, String> { }

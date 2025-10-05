package com.example.social_network.userPosts.infrastructure.adapters.repositories;

import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostJpaRepository extends JpaRepository<PostEntity, String>
{ }

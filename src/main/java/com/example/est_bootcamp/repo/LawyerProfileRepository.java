package com.example.est_bootcamp.repo;


import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.lawyer.BaseLawyerProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerProfileRepository extends BaseRepository<BaseLawyerProfile, Long> {
}

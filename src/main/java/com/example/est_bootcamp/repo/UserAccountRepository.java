package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.user.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends BaseRepository<UserAccount, Long> {
}
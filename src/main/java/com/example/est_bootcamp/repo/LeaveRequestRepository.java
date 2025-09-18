package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.leave.LeaveRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends BaseRepository<LeaveRequest, Long> {
}
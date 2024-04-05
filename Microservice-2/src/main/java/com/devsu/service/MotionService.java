package com.devsu.service;

import com.devsu.dto.request.MotionDTO;
import com.devsu.dto.request.MotionRequest;
import com.devsu.dto.response.MotionResponse;

import java.util.List;

public interface MotionService {

    List<MotionResponse> getAllMotions();

    MotionResponse getMotionById(Long id);

    MotionResponse createMotion(MotionDTO motionRequest);

    MotionResponse updateMotion(MotionDTO motionRequest, Long id);

    void deleteMotion(Long id);


}

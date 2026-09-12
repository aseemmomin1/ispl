package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.AboutContent;
import com.infyvaritaas.ispl.domain.Career;
import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.dto.ApiResponse;
import com.infyvaritaas.ispl.repository.AboutContentRepository;
import com.infyvaritaas.ispl.repository.CareerRepository;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository repairServiceRepository;
    private final CareerRepository careerRepository;
    private final AboutContentRepository aboutContentRepository;

    public AdminApiController(DeviceRepository deviceRepository,
            RepairServiceRepository repairServiceRepository,
            CareerRepository careerRepository,
            AboutContentRepository aboutContentRepository) {
        this.deviceRepository = deviceRepository;
        this.repairServiceRepository = repairServiceRepository;
        this.careerRepository = careerRepository;
        this.aboutContentRepository = aboutContentRepository;
    }

    @GetMapping("/devices")
    public ResponseEntity<ApiResponse<List<Device>>> devices() {
        return ResponseEntity.ok(new ApiResponse<>("Devices", deviceRepository.findAll()));
    }

    @GetMapping("/services")
    public ResponseEntity<ApiResponse<List<RepairService>>> services() {
        return ResponseEntity.ok(new ApiResponse<>("Services", repairServiceRepository.findAll()));
    }

    @GetMapping("/careers")
    public ResponseEntity<ApiResponse<List<Career>>> careers() {
        return ResponseEntity.ok(new ApiResponse<>("Careers", careerRepository.findAll()));
    }

    @GetMapping("/about")
    public ResponseEntity<ApiResponse<List<AboutContent>>> about() {
        return ResponseEntity.ok(new ApiResponse<>("About content", aboutContentRepository.findAll()));
    }
}

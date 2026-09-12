package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.AboutContent;
import com.infyvaritaas.ispl.domain.Career;
import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.DeviceCategory;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.dto.ApiResponse;
import com.infyvaritaas.ispl.repository.AboutContentRepository;
import com.infyvaritaas.ispl.repository.CareerRepository;
import com.infyvaritaas.ispl.repository.DeviceCategoryRepository;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PublicApiController {

    private final DeviceCategoryRepository deviceCategoryRepository;
    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository repairServiceRepository;
    private final CareerRepository careerRepository;
    private final AboutContentRepository aboutContentRepository;

    public PublicApiController(DeviceCategoryRepository deviceCategoryRepository,
            DeviceRepository deviceRepository,
            RepairServiceRepository repairServiceRepository,
            CareerRepository careerRepository,
            AboutContentRepository aboutContentRepository) {
        this.deviceCategoryRepository = deviceCategoryRepository;
        this.deviceRepository = deviceRepository;
        this.repairServiceRepository = repairServiceRepository;
        this.careerRepository = careerRepository;
        this.aboutContentRepository = aboutContentRepository;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<ApiResponse<List<DeviceCategory>>> categories() {
        return ResponseEntity.ok(new ApiResponse<>("Categories loaded", deviceCategoryRepository.findAll()));
    }

    @GetMapping("/public/devices")
    public ResponseEntity<ApiResponse<List<Device>>> devices() {
        return ResponseEntity.ok(new ApiResponse<>("Devices loaded", deviceRepository.findByActiveTrueAndDeletedFalseOrderByNameAsc()));
    }

    @GetMapping("/public/services")
    public ResponseEntity<ApiResponse<List<RepairService>>> services() {
        return ResponseEntity.ok(new ApiResponse<>("Services loaded", repairServiceRepository.findByActiveTrueAndDeletedFalseOrderByPriceAsc()));
    }

    @GetMapping("/public/careers")
    public ResponseEntity<ApiResponse<List<Career>>> careers() {
        return ResponseEntity.ok(new ApiResponse<>("Careers loaded", careerRepository.findByActiveTrueAndDeletedFalseOrderByCreatedAtDesc()));
    }

    @GetMapping("/public/about")
    public ResponseEntity<ApiResponse<List<AboutContent>>> about() {
        return ResponseEntity.ok(new ApiResponse<>("About content loaded", aboutContentRepository.findByActiveTrueOrderByUpdatedAtDesc()));
    }
}

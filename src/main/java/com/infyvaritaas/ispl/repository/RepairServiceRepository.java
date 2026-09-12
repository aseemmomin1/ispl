package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.RepairService;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairServiceRepository extends JpaRepository<RepairService, Long> {
    List<RepairService> findByDeviceIdAndActiveTrueAndDeletedFalseOrderByPriceAsc(Long deviceId);

    List<RepairService> findByActiveTrueAndDeletedFalseOrderByPriceAsc();
}

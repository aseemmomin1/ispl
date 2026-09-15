package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.RepairService;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairServiceRepository extends JpaRepository<RepairService, Long> {
    @EntityGraph(attributePaths = {"device", "device.category"})
    List<RepairService> findByDeviceIdAndActiveTrueAndDeletedFalseOrderByPriceAsc(Long deviceId);

    @EntityGraph(attributePaths = {"device", "device.category"})
    List<RepairService> findByActiveTrueAndDeletedFalseOrderByPriceAsc();

    @EntityGraph(attributePaths = {"device", "device.category"})
    List<RepairService> findByDeletedFalseOrderByPriceAsc();
}

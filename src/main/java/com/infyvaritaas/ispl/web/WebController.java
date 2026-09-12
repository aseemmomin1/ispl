package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.AboutContent;
import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.DeviceCategory;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.repository.AboutContentRepository;
import com.infyvaritaas.ispl.repository.CareerRepository;
import com.infyvaritaas.ispl.repository.DeviceCategoryRepository;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    private final DeviceCategoryRepository categoryRepository;
    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository serviceRepository;
    private final CareerRepository careerRepository;
    private final AboutContentRepository aboutContentRepository;

    public WebController(DeviceCategoryRepository categoryRepository,
            DeviceRepository deviceRepository,
            RepairServiceRepository serviceRepository,
            CareerRepository careerRepository,
            AboutContentRepository aboutContentRepository) {
        this.categoryRepository = categoryRepository;
        this.deviceRepository = deviceRepository;
        this.serviceRepository = serviceRepository;
        this.careerRepository = careerRepository;
        this.aboutContentRepository = aboutContentRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<DeviceCategory> categories = categoryRepository.findAll();
        List<Device> devices = deviceRepository.findByActiveTrueAndDeletedFalseOrderByNameAsc();
        model.addAttribute("categories", categories);
        model.addAttribute("devices", devices);
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("aboutItems", aboutContentRepository.findByActiveTrueOrderByUpdatedAtDesc());
        return "about";
    }

    @GetMapping("/careers")
    public String careers(Model model) {
        model.addAttribute("careers", careerRepository.findByActiveTrueAndDeletedFalseOrderByCreatedAtDesc());
        return "careers";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/device/{slug}")
    public String deviceDetail(@PathVariable String slug, Model model) {
        Device device = deviceRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        List<RepairService> services = serviceRepository.findByDeviceIdAndActiveTrueAndDeletedFalseOrderByPriceAsc(device.getId());
        model.addAttribute("device", device);
        model.addAttribute("services", services);
        return "device-detail";
    }

    @GetMapping("/device")
    public String deviceList(Model model) {
        List<DeviceCategory> categories = categoryRepository.findAll();
        List<Device> devices = deviceRepository.findByActiveTrueAndDeletedFalseOrderByNameAsc();
        List<Object> deviceCards = new ArrayList<>();
        for (Device categoryDevice : devices) {
            deviceCards.add(categoryDevice);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("devices", devices);
        model.addAttribute("deviceCards", deviceCards);
        return "device-list";
    }
}

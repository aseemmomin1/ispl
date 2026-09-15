package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.DeviceCategory;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.repository.AboutContentRepository;
import com.infyvaritaas.ispl.repository.CareerRepository;
import com.infyvaritaas.ispl.repository.DeviceCategoryRepository;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @ModelAttribute("loggedIn")
    public boolean populateLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("isAdmin")
    public boolean populateAdminFlag() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/")
    public String home(Model model) {
        List<DeviceCategory> categories = categoryRepository.findAll();
        List<Device> devices = deviceRepository.findByActiveTrueAndDeletedFalseOrderByNameAsc();
        List<RepairService> services = serviceRepository.findByActiveTrueAndDeletedFalseOrderByPriceAsc();
        Map<Long, List<RepairService>> servicesByDevice = services.stream()
                .collect(Collectors.groupingBy(service -> service.getDevice().getId()));

        List<com.infyvaritaas.ispl.domain.AboutContent> aboutItems = aboutContentRepository.findByActiveTrueOrderByUpdatedAtDesc();
        Map<String, com.infyvaritaas.ispl.domain.AboutContent> aboutByKey = aboutItems.stream()
                .collect(Collectors.toMap(com.infyvaritaas.ispl.domain.AboutContent::getSectionKey, item -> item));

        model.addAttribute("categories", categories);
        model.addAttribute("devices", devices);
        model.addAttribute("servicesByDevice", servicesByDevice);
        model.addAttribute("careers", careerRepository.findByActiveTrueAndDeletedFalseOrderByCreatedAtDesc());
        model.addAttribute("aboutItems", aboutItems);
        model.addAttribute("aboutByKey", aboutByKey);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkout(@RequestParam Long deviceId, @RequestParam Long serviceId, Model model) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        RepairService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        model.addAttribute("device", device);
        model.addAttribute("service", service);
        return "checkout";
    }

    @GetMapping("/device/{slug}")
    public String deviceDetail(@PathVariable String slug, Model model) {
        Device device = deviceRepository.findBySlugAndActiveTrueAndDeletedFalse(slug)
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
        model.addAttribute("categories", categories);
        model.addAttribute("devices", devices);
        return "device-list";
    }
}

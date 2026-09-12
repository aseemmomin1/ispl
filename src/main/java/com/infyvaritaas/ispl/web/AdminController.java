package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.AboutContent;
import com.infyvaritaas.ispl.domain.Career;
import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.DeviceCategory;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.repository.AboutContentRepository;
import com.infyvaritaas.ispl.repository.CareerRepository;
import com.infyvaritaas.ispl.repository.DeviceCategoryRepository;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import com.infyvaritaas.ispl.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DeviceCategoryRepository categoryRepository;
    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository serviceRepository;
    private final CareerRepository careerRepository;
    private final AboutContentRepository aboutContentRepository;
    private final UserRepository userRepository;

    public AdminController(DeviceCategoryRepository categoryRepository,
            DeviceRepository deviceRepository,
            RepairServiceRepository serviceRepository,
            CareerRepository careerRepository,
            AboutContentRepository aboutContentRepository,
            UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.deviceRepository = deviceRepository;
        this.serviceRepository = serviceRepository;
        this.careerRepository = careerRepository;
        this.aboutContentRepository = aboutContentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        model.addAttribute("deviceCount", deviceRepository.count());
        model.addAttribute("careerCount", careerRepository.count());
        model.addAttribute("orderCount", 0);
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/devices")
    public String devices(Model model) {
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/devices";
    }

    @PostMapping("/devices/save")
    public String saveDevice(@RequestParam Long categoryId,
            @RequestParam String name,
            @RequestParam String slug,
            @RequestParam(required = false) String shortDescription,
            @RequestParam(required = false) String fullDescription,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(defaultValue = "true") boolean active,
            @RequestParam(required = false) Long deviceId) {

        Device device = deviceId != null ? deviceRepository.findById(deviceId).orElse(new Device()) : new Device();
        DeviceCategory category = categoryRepository.findById(categoryId).orElseThrow();
        device.setCategory(category);
        device.setName(name);
        device.setSlug(slug);
        device.setShortDescription(shortDescription);
        device.setFullDescription(fullDescription);
        device.setImageUrl(imageUrl);
        device.setActive(active);
        device.setPriceFrom(BigDecimal.ZERO);
        deviceRepository.save(device);
        return "redirect:/admin/devices";
    }

    @PostMapping("/devices/{id}/delete")
    public String deleteDevice(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow();
        device.setDeleted(true);
        device.setActive(false);
        deviceRepository.save(device);
        return "redirect:/admin/devices";
    }

    @PostMapping("/devices/{id}/hard-delete")
    public String hardDeleteDevice(@PathVariable Long id) {
        deviceRepository.deleteById(id);
        return "redirect:/admin/devices";
    }

    @GetMapping("/careers")
    public String careers(Model model) {
        model.addAttribute("careers", careerRepository.findAll());
        return "admin/careers";
    }

    @PostMapping("/careers/save")
    public String saveCareer(@RequestParam(required = false) Long careerId,
            @RequestParam String title,
            @RequestParam String department,
            @RequestParam String location,
            @RequestParam String employmentType,
            @RequestParam String description,
            @RequestParam String requirements,
            @RequestParam(defaultValue = "true") boolean active) {
        Career career = careerId != null ? careerRepository.findById(careerId).orElse(new Career()) : new Career();
        career.setTitle(title);
        career.setDepartment(department);
        career.setLocation(location);
        career.setEmploymentType(employmentType);
        career.setDescription(description);
        career.setRequirements(requirements);
        career.setActive(active);
        careerRepository.save(career);
        return "redirect:/admin/careers";
    }

    @PostMapping("/careers/{id}/delete")
    public String deleteCareer(@PathVariable Long id) {
        Career career = careerRepository.findById(id).orElseThrow();
        career.setDeleted(true);
        career.setActive(false);
        careerRepository.save(career);
        return "redirect:/admin/careers";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("aboutItems", aboutContentRepository.findAll());
        return "admin/about";
    }

    @PostMapping("/about/save")
    public String saveAbout(@RequestParam(required = false) Long aboutId,
            @RequestParam String sectionKey,
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam(defaultValue = "true") boolean active) {
        AboutContent content = aboutId != null ? aboutContentRepository.findById(aboutId).orElse(new AboutContent()) : new AboutContent();
        content.setSectionKey(sectionKey);
        content.setTitle(title);
        content.setBody(body);
        content.setActive(active);
        aboutContentRepository.save(content);
        return "redirect:/admin/about";
    }

    @PostMapping("/about/{id}/delete")
    public String deleteAbout(@PathVariable Long id) {
        aboutContentRepository.deleteById(id);
        return "redirect:/admin/about";
    }

    @GetMapping("/services")
    public String services(Model model) {
        List<Device> devices = deviceRepository.findAll();
        model.addAttribute("devices", devices);
        model.addAttribute("services", serviceRepository.findAll());
        return "admin/services";
    }

    @PostMapping("/services/save")
    public String saveService(@RequestParam Long deviceId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam String duration,
            @RequestParam(defaultValue = "true") boolean active,
            @RequestParam(required = false) Long serviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow();
        RepairService service = serviceId != null ? serviceRepository.findById(serviceId).orElse(new RepairService()) : new RepairService();
        service.setDevice(device);
        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);
        service.setDuration(duration);
        service.setActive(active);
        serviceRepository.save(service);
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/delete")
    public String deleteService(@PathVariable Long id) {
        RepairService service = serviceRepository.findById(id).orElseThrow();
        service.setDeleted(true);
        service.setActive(false);
        serviceRepository.save(service);
        return "redirect:/admin/services";
    }
}

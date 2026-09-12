INSERT INTO device_categories (name, slug, description) VALUES
    ('Mobile', 'mobile', 'Smartphone repair, diagnostics and display work. '),
    ('Laptop', 'laptop', 'Laptop upgrades, battery repairs and keyboard servicing.'),
    ('Desktop', 'desktop', 'PC tower repairs, cooling optimization and upgrades.'),
    ('Tablet', 'tablet', 'Tablet screen and battery repair services for enterprise and personal use.');

INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Galaxy S Series', 'galaxy-s-series', 'Premium smartphone diagnostics and display solutions.', 'Expert repair support for flagship mobile devices, including quick diagnostics, chip-level fault isolation and software recovery.', 2499.00, 'https://images.unsplash.com/...', TRUE, FALSE FROM device_categories dc WHERE dc.slug = 'mobile';
INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'MacBook Pro', 'macbook-pro', 'Performance maintenance and battery diagnostics for premium laptops.', 'Built for power users, we handle battery replacement, keyboard servicing, SSD upgrades and thermal optimization.', 3299.00, 'https://images.unsplash.com/...', TRUE, FALSE FROM device_categories dc WHERE dc.slug = 'laptop';
INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Gaming Tower', 'gaming-tower', 'Custom desktop tuning for performance and reliability.', 'Desktop support for gaming rigs and office workstations covering PSU, GPU, cooling and storage upgrades.', 2899.00, 'https://images.unsplash.com/...', TRUE, FALSE FROM device_categories dc WHERE dc.slug = 'desktop';
INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'iPad Air', 'ipad-air', 'Tablet repair and hardware upgrades for everyday workflows.', 'Fast repair support for tablet devices including digitizer replacement, battery fixes and Wi-Fi board diagnostics.', 1999.00, 'https://images.unsplash.com/...', TRUE, FALSE FROM device_categories dc WHERE dc.slug = 'tablet';

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Screen Replacement', 'OEM-grade display replacement with calibration and touch verification.', 2499.00, '3-4 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'galaxy-s-series';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Swap', 'High-capacity battery installation with health check and power diagnostics.', 1899.00, '1-2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'galaxy-s-series';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Charging Port Repair', 'Port cleaning and connector replacement for stable charging performance.', 1499.00, '2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'galaxy-s-series';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Camera Module Service', 'Lens and sensor restoration with image calibration support.', 2199.00, '2-3 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'galaxy-s-series';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Data Recovery', 'Secure device data retrieval and backup assistance for failed storage.', 3999.00, '1 day', TRUE, FALSE FROM devices d WHERE d.slug = 'galaxy-s-series';

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Keyboard Replacement', 'Mechanical or membrane keyboard replacement with tested key response.', 2999.00, '2-4 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'macbook-pro';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'SSD Upgrade', 'Performance upgrade with cloning and data migration support.', 4999.00, '2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'macbook-pro';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Replacement', 'Battery health optimization with safe thermal checks.', 3699.00, '1-3 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'macbook-pro';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Motherboard Diagnostics', 'Trace-level board fault detection and component-level testing.', 5899.00, 'Same day', TRUE, FALSE FROM devices d WHERE d.slug = 'macbook-pro';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Liquid Damage Cleanup', 'Internal cleaning and corrosion mitigation to restore minimal functionality.', 4499.00, '1 day', TRUE, FALSE FROM devices d WHERE d.slug = 'macbook-pro';

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'PSU Replacement', 'Power supply replacement with load testing and cable verification.', 3499.00, '2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'gaming-tower';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'RAM Upgrade', 'Memory expansion and compatibility optimization for modern workloads.', 2199.00, '1-2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'gaming-tower';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Cooling Fan Clean', 'Fan and airflow maintenance to reduce heat and noise.', 1799.00, '1 hour', TRUE, FALSE FROM devices d WHERE d.slug = 'gaming-tower';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Virus Cleanup', 'Malware scanning and OS integrity remediation for slow systems.', 2499.00, '2-3 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'gaming-tower';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'GPU Diagnostic', 'Performance and thermal testing with board-level diagnosis.', 3999.00, 'Same day', TRUE, FALSE FROM devices d WHERE d.slug = 'gaming-tower';

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Glass Replacement', 'Screen glass repair with touch accuracy and anti-glare alignment.', 2299.00, '2-3 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'ipad-air';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Wi-Fi Board Repair', 'Wireless module replacement and signal recovery.', 2499.00, '2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'ipad-air';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Digitizer Repair', 'Touch panel digitizer replacement for precise interaction.', 2699.00, '3 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'ipad-air';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Swap', 'Tablet battery replacement with charge cycle testing.', 2099.00, '1-2 hours', TRUE, FALSE FROM devices d WHERE d.slug = 'ipad-air';
INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Device Unlock Service', 'Secure device unlock and setup assistance for operational continuity.', 1199.00, '1 hour', TRUE, FALSE FROM devices d WHERE d.slug = 'ipad-air';

INSERT INTO careers (title, department, location, employment_type, description, requirements, active, deleted)
VALUES
    ('Senior Hardware Technician', 'Repair Operations', 'Bengaluru', 'Full-time', 'Lead device diagnostics and repair quality checks for mobile, laptop, and desktop workflows.', '5+ years in hardware repair, strong troubleshooting mindset, customer-first communication.', TRUE, FALSE),
    ('Technical Instructor', 'Training Academy', 'Hyderabad', 'Full-time', 'Train junior technicians and certify new repair workflows for multi-device ecosystems.', 'Hands-on repair experience, training or coaching background, strong documentation skills.', TRUE, FALSE),
    ('Customer Success Executive', 'Support', 'Remote', 'Hybrid', 'Coordinate service appointments, explain repair status, and ensure premium customer experience across touchpoints.', 'Excellent communication, service orientation, familiarity with ticketing systems.', TRUE, FALSE),
    ('Field Service Engineer', 'Onsite Services', 'Pune', 'Full-time', 'Perform structured hardware and software issue resolution at client premises and retail outlets.', 'Technical certification, mobility readiness, familiarity with enterprise support workflows.', TRUE, FALSE);

INSERT INTO about_content (section_key, title, body, active)
VALUES
    ('story', 'Our Story', 'ISPL is a modern device repair and lifecycle services brand helping homes, offices, and enterprises keep technology productive and secure.', TRUE),
    ('mission', 'Our Mission', 'We combine certified technical expertise with a transparent service workflow, ensuring faster turnarounds and trust at every stage.', TRUE),
    ('stats', 'Impact At A Glance', '15,000+ repairs completed. 98% customer satisfaction. 48-hour average turnaround for standard service cases.', TRUE);

INSERT INTO users (username, email, password, role, deleted, created_at, updated_at)
VALUES ('admin', 'admin@ispl.local', '$2a$10$Tyb5/Hp65r3v4KnEsLDUNeQ028qDJxa6QQF1vBK87HFd9efpiU9na', 'ADMIN', FALSE, NOW(), NOW());

INSERT INTO device_categories (name, slug, description)
SELECT 'Mobile', 'mobile', 'Smartphone repair, diagnostics and display work.'
WHERE NOT EXISTS (SELECT 1 FROM device_categories WHERE slug = 'mobile');

INSERT INTO device_categories (name, slug, description)
SELECT 'Laptop', 'laptop', 'Laptop upgrades, battery repairs and keyboard servicing.'
WHERE NOT EXISTS (SELECT 1 FROM device_categories WHERE slug = 'laptop');

INSERT INTO device_categories (name, slug, description)
SELECT 'Desktop', 'desktop', 'PC tower repairs, cooling optimization and upgrades.'
WHERE NOT EXISTS (SELECT 1 FROM device_categories WHERE slug = 'desktop');

INSERT INTO device_categories (name, slug, description)
SELECT 'Tablet', 'tablet', 'Tablet screen and battery repair services for personal and business workflows.'
WHERE NOT EXISTS (SELECT 1 FROM device_categories WHERE slug = 'tablet');

INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Mobile', 'mobile', 'Fast smartphone diagnostics with premium parts and transparent pricing.', 'From cracked glass to battery health checks, our mobile repair team restores everyday performance quickly and safely.', 1499.00, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=900&q=80', TRUE, FALSE
FROM device_categories dc
WHERE dc.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM devices WHERE slug = 'mobile');

INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Laptop', 'laptop', 'Repair, upgrades and performance tuning for work and study laptops.', 'We cover damaged screens, battery issues, SSD upgrades, keyboard replacements and thermal maintenance for everyday productivity.', 1999.00, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=900&q=80', TRUE, FALSE
FROM device_categories dc
WHERE dc.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM devices WHERE slug = 'laptop');

INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Desktop', 'desktop', 'Performance-first desktop repair and custom PC support for homes and offices.', 'From PSU replacement to GPU diagnostics, we keep workstations stable with clean upgrades and deep hardware checks.', 2499.00, 'https://images.unsplash.com/photo-1591799264318-7e6ef8ddb7ea?auto=format&fit=crop&w=900&q=80', TRUE, FALSE
FROM device_categories dc
WHERE dc.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM devices WHERE slug = 'desktop');

INSERT INTO devices (category_id, name, slug, short_description, full_description, price_from, image_url, active, deleted)
SELECT dc.id, 'Tablet', 'tablet', 'Reliable tablet support for browsing, learning and enterprise tasks.', 'We repair cracked tablets, restore charging reliability, and tune software issues while keeping your workflow uninterrupted.', 1299.00, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?auto=format&fit=crop&w=900&q=80', TRUE, FALSE
FROM device_categories dc
WHERE dc.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM devices WHERE slug = 'tablet');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Screen Repair', 'OEM-grade display replacement with calibration and touch verification for crisp visuals.', 2499.00, '2-3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Screen Repair');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Replacement', 'High-capacity battery fitment with health diagnostics and safe power testing.', 1899.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Battery Replacement');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Camera Repair', 'Lens alignment and sensor diagnostics for clearer capture and restored autofocus.', 1999.00, '1-3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Camera Repair');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Charging Port Repair', 'Port cleaning and connector replacement to restore fast and stable charging.', 1499.00, '2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Charging Port Repair');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Motherboard Diagnosis', 'Detailed fault isolation for intermittent power, boot loops and hardware instability.', 2199.00, '3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'mobile'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Motherboard Diagnosis');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Screen Replacement', 'Panel replacement with brightness calibration and bezel checks for crisp output.', 3999.00, '2-4 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Screen Replacement');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Keyboard Replacement', 'Trackpad and keycap restoration for responsive typing and clean finishing.', 2999.00, '2-3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Keyboard Replacement');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Swap', 'Battery health check and installation to restore unplugged working time.', 2499.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Battery Swap');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'SSD Upgrade', 'Drive upgrade with migration assistance for faster boot and file access.', 4999.00, '2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'SSD Upgrade');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Thermal Rework', 'Cooling maintenance and thermal paste replacement to reduce overheating.', 2899.00, '2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'laptop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Thermal Rework');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'PC Build/Assembly', 'Custom desktop assembly with compatibility checks and cable management.', 3499.00, '3-5 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'PC Build/Assembly');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'PSU Replacement', 'Power supply replacement with testing for stable voltage and performance.', 2799.00, '2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'PSU Replacement');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'GPU Diagnostics', 'Graphics card health analysis and repair assistance for display issues.', 3299.00, '2-3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'GPU Diagnostics');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'OS Install', 'Clean operating system setup with driver installation and basic optimization.', 2499.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'OS Install');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Dust Cleaning', 'Internal cleanup and cooling optimization to restore airflow and reduce noise.', 1999.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'desktop'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Dust Cleaning');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Display Replacement', 'Glass or panel replacement with touch calibration and display quality checks.', 2999.00, '2-4 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Display Replacement');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Touch Digitizer', 'Digitizer repair for responsive taps, drag interactions and screen accuracy.', 2499.00, '2-3 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Touch Digitizer');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Battery Swap', 'Battery replacement and charging performance restoration for tablet reliability.', 2199.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Battery Swap');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Charging Port', 'Connector repair to restore consistent charging and cable seating.', 1799.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Charging Port');

INSERT INTO services (device_id, name, description, price, duration, active, deleted)
SELECT d.id, 'Software Troubleshooting', 'System tune-up and app or OS troubleshooting for performance issues.', 1499.00, '1-2 hours', TRUE, FALSE
FROM devices d
WHERE d.slug = 'tablet'
  AND NOT EXISTS (SELECT 1 FROM services WHERE device_id = d.id AND name = 'Software Troubleshooting');

INSERT INTO careers (title, department, location, employment_type, description, requirements, active, deleted)
SELECT 'Senior Hardware Technician', 'Repair Operations', 'Bengaluru', 'Full-time', 'Lead diagnostics and repair quality checks for mobile, laptop, and desktop devices across client and enterprise assignments.', '5+ years in hardware repair, strong troubleshooting mindset, quality-focused service delivery, and customer-first communication.', TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM careers WHERE title = 'Senior Hardware Technician');

INSERT INTO careers (title, department, location, employment_type, description, requirements, active, deleted)
SELECT 'Technical Instructor', 'Training Academy', 'Hyderabad', 'Full-time', 'Train junior technicians and document repair workflows for our growing field service team.', 'Hands-on repair experience, clear communication, coaching ability, and confidence working in multi-device environments.', TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM careers WHERE title = 'Technical Instructor');

INSERT INTO careers (title, department, location, employment_type, description, requirements, active, deleted)
SELECT 'Operations Coordinator', 'Service Desk', 'Pune', 'Full-time', 'Coordinate repair operations, customer updates, and service escalations across day-to-day workflows.', 'Strong organization, scheduling skills, process mindset, and customer support experience in a technical service environment.', TRUE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM careers WHERE title = 'Operations Coordinator');

INSERT INTO about_content (section_key, title, body, active)
SELECT 'company_story', 'About ISPL', 'ISPL helps homes, offices, and growing businesses keep technology running smoothly through expert repair, diagnostics, upgrades, and dependable support.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM about_content WHERE section_key = 'company_story');

INSERT INTO about_content (section_key, title, body, active)
SELECT 'repairs_per_month', 'Repairs / month', '1250', TRUE
WHERE NOT EXISTS (SELECT 1 FROM about_content WHERE section_key = 'repairs_per_month');

INSERT INTO about_content (section_key, title, body, active)
SELECT 'satisfaction_rate', 'Satisfaction rate', '98.6%', TRUE
WHERE NOT EXISTS (SELECT 1 FROM about_content WHERE section_key = 'satisfaction_rate');

INSERT INTO users (username, email, password, role, deleted, created_at, updated_at)
SELECT 'admin', 'admin@ispl.local', '$2a$10$Ge4wpZl2ZEnAZzgnzk1uterrP7fUBlhvQMyECSV6MBN4pyjIu911K', 'ADMIN', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, email, password, role, deleted, created_at, updated_at)
SELECT 'user', 'user@ispl.local', '$2a$10$z2YyupwkEmeRPuewnQJ7wOVlGTzMRiBzJ2Tlkrzuy6hcUfMvN4LEC', 'USER', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

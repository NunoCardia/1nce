CREATE TABLE triangle_info (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               first_side DECIMAL(19, 2),
                               second_side DECIMAL(19, 2),
                               third_side DECIMAL(19, 2),
                               category VARCHAR(255),
                               timestamp TIMESTAMP
);

-- Sample INSERT statements
INSERT INTO triangle_info (first_side, second_side, third_side, category, timestamp)
VALUES (3.0, 4.0, 5.0, 'Right', '2023-05-07 10:30:00');

INSERT INTO triangle_info (first_side, second_side, third_side, category, timestamp)
VALUES (5.5, 5.5, 5.5, 'Equilateral', '2023-05-07 11:45:00');

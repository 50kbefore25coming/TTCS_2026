# Mục tiêu
---
- **Dự án được xây dựng nhằm mục đích tự động hóa việc thu thập, lưu trữ và quy đổi tỷ giá hối đoái từ các đồng tiền quốc tế. Hệ thống giúp người dùng tra cứu tỷ giá theo thời gian thực (Real-time) và xem lại lịch sử biến động tỷ giá thông qua cơ sở dữ liệu nội bộ.**
- **Công nghệ sử dụng: Java 17, Spring Boot, Spring Data JPA, MySQL, Lombok, Maven**
---
# Danh sách API
### 1. Tra cứu tỷ giá (Real-time)
Lấy tỷ giá mới nhất và tự động lưu cặp tỷ giá với USD, VND vào database.
- **URL**: `GET /rates/{currency}`
- **Ví dụ**: `GET http://localhost:8080/rates/KRW`

### 2. Quy đổi tiền tệ
Thực hiện tính toán quy đổi từ đồng tiền gốc sang đồng tiền đích.
- **URL**: `GET /rates/convert?from={from}&to={to}&amount={amount}`
- **Ví dụ**: `GET http://localhost:8080/rates/convert?from=USD&to=VND&amount=100`

### 3. Tra cứu tỷ giá trong Database
Lấy tỷ giá được lưu trữ gần nhất trong cơ sở dữ liệu.
- **URL**: `GET /rates/db/latest?from={from}&to={to}`
- **Ví dụ**: `GET http://localhost:8080/rates/db/latest?from=USD&to=VND`

### 4. Xem lịch sử tỷ giá
Xem danh sách các bản ghi tỷ giá của một cặp tiền trong khoảng thời gian xác định.
- **URL**: `GET /rates/history?from={from}&to={to}&days={days}`
- **Ví dụ**: `GET http://localhost:8080/rates/history?from=USD&to=VND&days=7`

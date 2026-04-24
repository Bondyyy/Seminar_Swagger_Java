Hệ thống Quản lý Sinh viên API (Seminar Swagger Java)
Dự án này là một ứng dụng minh họa cách xây dựng RESTful API bằng Spring Boot và tự động tạo tài liệu hướng dẫn (documentation) sử dụng OpenAPI 3 và Swagger UI. Hệ thống mô phỏng các thao tác quản lý sinh viên cơ bản và các tính năng nâng cao như phân trang, bảo mật và xử lý tệp tin.

🚀 Tính năng chính
Hệ thống cung cấp đầy đủ các chức năng quản lý thông tin sinh viên:

Quản lý cơ bản: Xem danh sách, tìm kiếm theo ID, thêm mới, cập nhật (Full update/Patch) và xóa sinh viên.

Tìm kiếm & Phân trang: Hỗ trợ tìm kiếm tương đối theo tên và phân trang kết quả trả về.

Xử lý hàng loạt (Batch operations): Thêm hoặc xóa nhiều sinh viên cùng lúc thông qua mảng dữ liệu.

Tiện ích: Thống kê số lượng sinh viên và xuất dữ liệu ra định dạng CSV.

Xử lý tệp tin: Hỗ trợ tải lên ảnh đại diện (avatar) cho sinh viên.

Bảo mật: Mô phỏng API yêu cầu xác thực qua Bearer Token trong Header.

🛠 Công nghệ sử dụng
Java: Phiên bản 17.

Framework: Spring Boot 3.3.0.

Tài liệu API: springdoc-openapi (Swagger UI) phiên bản 2.5.0.

Quản lý dự án: Maven.

📋 Cấu hình hệ thống
Dự án được cấu hình sẵn các thông tin về API trong lớp OpenApiConfig.java:

Tiêu đề: Hệ thống Quản lý Sinh viên API.

Phiên bản: 1.0.0.

Môi trường hỗ trợ:

Dev: http://localhost:8080.

Production: https://api.nhieuhanh.com.

📖 Hướng dẫn sử dụng
1. Chạy ứng dụng
Bạn có thể chạy dự án bằng lệnh Maven:

Bash
mvn spring-boot:run
2. Truy cập Tài liệu API (Swagger UI)
Sau khi ứng dụng khởi chạy thành công, bạn có thể truy cập giao diện Swagger UI để kiểm thử các API trực tiếp tại địa chỉ:

URL: http://localhost:8080/swagger-ui.html

3. Các Endpoint chính
Phương thức	Đường dẫn	Mô tả
GET	/student/all	Lấy toàn bộ danh sách sinh viên.
GET	/student/search	Tìm kiếm sinh viên theo tên.
POST	/student	Thêm mới 1 sinh viên.
POST	/student/{id}/upload-avatar	Tải lên ảnh đại diện cho sinh viên.
GET	/student/secure-info	Truy cập thông tin bảo mật (Yêu cầu Token).
👤 Thông tin liên hệ
Tác giả: Huỳnh Đức Dũng - Bondy

Email: bondy@example.com

GitHub: https://github.com/Bondyyy

Dự án được thực hiện phục vụ cho mục đích Seminar và học tập về Swagger UI trong hệ sinh thái Java Spring Boot.

package com.nhieuhanh.__demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@Tag(name = "Student Management", description = "Các API dùng để quản lý thông tin Sinh Viên")
public class StudentController {

    private Map<String, Student> studentDatabase = new HashMap<>();

    public StudentController() {
        studentDatabase.put("24520001", new Student("24520001", "Nguyen Van Minh"));
        studentDatabase.put("24520002", new Student("24520002", "Tran Thi Lan"));
        studentDatabase.put("24520003", new Student("24520003", "Le Hoang Bach"));
        studentDatabase.put("24520004", new Student("24520004", "Pham Quang Hieu"));
        studentDatabase.put("24520005", new Student("24520005", "Vu Thi Mai"));
    }

    @Operation(summary = "Lấy toàn bộ danh sách sinh viên")
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(new ArrayList<>(studentDatabase.values()));
    }

    @Operation(summary = "Tìm sinh viên theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") String id) {
        if (studentDatabase.containsKey(id)) return ResponseEntity.ok(studentDatabase.get(id));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sinh viên!");
    }

    @Operation(
            summary = "Tìm kiếm sinh viên theo tên", 
            description = "Tìm kiếm tương đối theo họ tên sinh viên.",
            tags = {"Student Management", "Search Engine"} 
    )
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudent(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        List<Student> result = studentDatabase.values().stream()
                .filter(s -> s.getHoTen().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Lấy danh sách sinh viên có phân trang", 
            description = "Mô phỏng tính năng phân trang thường dùng trong thực tế.",
            tags = {"Student Management", "Search Engine"} 
    )
    @GetMapping("/page")
    public ResponseEntity<List<Student>> getStudentsByPage(
            @Parameter(description = "Số trang (Bắt đầu từ 1)", example = "1") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Số lượng trên mỗi trang", example = "2") @RequestParam(defaultValue = "2") int size) {
        
        List<Student> allStudents = new ArrayList<>(studentDatabase.values());
        int skip = (page - 1) * size;
        List<Student> pagedResult = allStudents.stream().skip(skip).limit(size).collect(Collectors.toList());
        return ResponseEntity.ok(pagedResult);
    }

    @Operation(summary = "Thêm 1 sinh viên mới")
    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student newStudent) {
        if (studentDatabase.containsKey(newStudent.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID đã tồn tại!");
        }
        studentDatabase.put(newStudent.getId(), newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thêm thành công!");
    }

    @Operation(summary = "Cập nhật TOÀN BỘ thông tin sinh viên", description = "Ghi đè dữ liệu cũ bằng dữ liệu mới.")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudentFull(
            @PathVariable("id") String id, 
            @RequestBody Student updatedStudent) {
        if (!studentDatabase.containsKey(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy!");
        updatedStudent.setId(id); // Giữ nguyên ID
        studentDatabase.put(id, updatedStudent);
        return ResponseEntity.ok("Cập nhật toàn bộ thành công!");
    }

    @Operation(summary = "Đổi tên sinh viên", description = "cập nhật một trường dữ liệu cụ thể.")
    @PatchMapping("/{id}/name")
    public ResponseEntity<String> updateStudentName(
            @PathVariable("id") String id, 
            @RequestParam("newName") String newName) {
        if (!studentDatabase.containsKey(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy!");
        studentDatabase.get(id).setHoTen(newName);
        return ResponseEntity.ok("Cập nhật tên thành công!");
    }

    @Operation(summary = "Xóa 1 sinh viên theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {
        if (studentDatabase.remove(id) != null) return ResponseEntity.ok("Xóa thành công!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy để xóa!");
    }

    @Operation(
            summary = "Thêm danh sách sinh viên", 
            description = "Truyền vào một mảng các sinh viên để lưu cùng lúc.",
            tags = {"Student Management", "Data Import/Export"}
    )
    @PostMapping("/batch")
    public ResponseEntity<String> createMultipleStudents(@RequestBody List<Student> students) {
        int count = 0;
        for (Student s : students) {
            if (!studentDatabase.containsKey(s.getId())) {
                studentDatabase.put(s.getId(), s);
                count++;
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Đã thêm thành công " + count + " sinh viên mới!");
    }

    @Operation(
            summary = "Xóa nhiều sinh viên cùng lúc (Batch Delete)", 
            description = "Truyền vào danh sách các ID cần xóa.",
            tags = {"Student Management", "Data Import/Export"}
    )
    @DeleteMapping("/batch")
    public ResponseEntity<String> deleteMultipleStudents(@RequestBody List<String> ids) {
        int count = 0;
        for (String id : ids) {
            if (studentDatabase.remove(id) != null) count++;
        }
        return ResponseEntity.ok("Đã xóa thành công " + count + " sinh viên!");
    }

    @Operation(
            summary = "Thống kê tổng số lượng sinh viên", 
            description = "Phục vụ cho việc vẽ biểu đồ trên Dashboard.",
            tags = {"Student Management", "Dashboard & Analytics"}
    )
    @GetMapping("/count")
    public ResponseEntity<String> countStudents() {
        return ResponseEntity.ok("Hệ thống hiện đang có: " + studentDatabase.size() + " sinh viên.");
    }

    @Operation(
            summary = "Xuất dữ liệu sinh viên ra file CSV", 
            description = "Mô phỏng API tải file báo cáo.",
            tags = {"Student Management", "Data Import/Export", "File System"}
    )
    @GetMapping("/export/csv")
    public ResponseEntity<String> exportToCSV() {
    
        String csvContent = "ID,Họ Tên\n" + studentDatabase.values().stream()
                .map(s -> s.getId() + "," + s.getHoTen())
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok("Nội dung file CSV:\n\n" + csvContent);
    }

    @Operation(
            summary = "Tải ảnh đại diện", 
            description = "Upload file avatar cho sinh viên.",
            tags = {"Student Management", "File System"} 
    )
    @PostMapping(value = "/{id}/upload-avatar", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadAvatar(
            @PathVariable("id") String id,
            @RequestParam("file") MultipartFile file) {
        if (!studentDatabase.containsKey(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sinh viên!");
        return ResponseEntity.ok("Đã nhận file: " + file.getOriginalFilename() + " cho sinh viên " + studentDatabase.get(id).getHoTen());
    }

    @Operation(
            summary = "Lấy dữ liệu bảo mật", 
            description = "Bắt buộc phải truyền token vào Header.",
            tags = {"Student Management", "Security & Auth"} 
    )
    @GetMapping("/secure-info")
    public ResponseEntity<String> getSecureInfo(
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.equals("Bearer DemoToken123")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi 401: Bạn không có quyền truy cập!");
        }
        return ResponseEntity.ok("Xác thực thành công! Đây là thông tin mật.");
    }
}
package com.example.projectemarketg3.service;

import com.example.projectemarketg3.entity.Image;
import com.example.projectemarketg3.entity.Product;
import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.exception.BadRequestException;
import com.example.projectemarketg3.repository.ImageRepository;
import com.example.projectemarketg3.repository.ProductRepository;
import com.example.projectemarketg3.repository.UserRepository;
import com.example.projectemarketg3.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Path folder để upload file
    private final Path rootPath = Paths.get("uploads");



    public FileService() {
        createFolder(rootPath.toString());
    }

    // Tạo folder
    public void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // Upload file
    public String uploadFile(Long id, MultipartFile file) {
        // B1 : Tạo folder tương ứng userId
        Path pathDir = Paths.get("uploads").resolve(String.valueOf(id));
        createFolder(pathDir.toString());

        // B2 : Validate file
        validate(file);

        // B3 : Tạo path tương ứng với fileUpload
        String generateFileId = String.valueOf(Instant.now().getEpochSecond());
        File fileServer = new File(pathDir + "/" + generateFileId);

        try {
            // Sử dụng Buffer để lưu dữ liệu
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileServer));

            stream.write(file.getBytes());
            stream.close();

//            String link = "/api/v1/users/" + id + "/files/" + generateFileId;
            String link = "/api/v1/product/" + id + "/files/" + generateFileId;

//            User user = userRepository.getUserById(id);
            Product product = productRepository.getProductById(id);

            Image image = Image.builder().id(generateFileId).createAt(new Date(System.currentTimeMillis())).url(link).product(product).build();

            imageRepository.save(image);
            return link;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi upload file");
        }
    }

    public void validate(MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BadRequestException("Tên file không hợp lệ");
        }

        // Kiểm tra đuôi file
        String fileExtension = Utils.getExtensionFile(fileName);
        if (!Utils.checkFileExtension(fileExtension)) {
            throw new BadRequestException("File không hợp lệ");
        }

        // Kiểm tra size (upload dưới 2MB)
        if ((double) file.getSize() / 1_000_000L > 2) {
            throw new BadRequestException("File không được vượt quá 2MB");
        }
    }

    // Xem file
    public byte[] readFile(Long id, String fileId) {
        // Lấy đường dẫn file tương ứng với user_id
        Path userPath = rootPath.resolve(String.valueOf(id));

        // Kiểm tra userPath có tồn tại hay không
        if (!Files.exists(userPath)) {
            throw new RuntimeException("Lỗi khi đọc file " + fileId);
        }

        try {
            Path file = userPath.resolve(fileId);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                InputStream stream = resource.getInputStream();
                byte[] bytes = StreamUtils.copyToByteArray(stream);

                stream.close();
                return bytes;
            } else {
                throw new RuntimeException("Lỗi khi đọc file " + fileId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc file " + fileId);
        }
    }

    // Xóa file
    public void deleteFile(Long id, String fileId) {
        // Lấy đường dẫn file tương ứng với user_id
        Path userPath = rootPath.resolve(String.valueOf(id));

        // Kiểm tra userPath có tồn tại hay không
        if (!Files.exists(userPath)) {
            throw new RuntimeException("Lỗi khi đọc file " + fileId);
        }

        // Tạo đường dẫn đến file
        Path file = userPath.resolve(fileId);
        if (!file.toFile().exists()) {
            throw new RuntimeException("file " + fileId + " không tồn tại");
        }
//xoa tren server
        file.toFile().delete();
//    xoa tren db
        imageRepository.deleteById(fileId);
    }

    // Lấy danh sách file
    public List<String> getFiles(Long id) {
        // Lấy ds file tương ứng với product_id
        List<Image> images = imageRepository.getByProduct_IdOrderByCreateAtDesc(id);

        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
package com.example.projectemarketg3.utils;

import java.util.Arrays;
import java.util.List;

public class Utils {
    // Lấy extension file
    // image.png, avatar.jpg => png, jpg
    public static String getExtensionFile(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if(lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1);
    }

    // Kiểm tra extension file có nằm trong danh sách được upload hay không
    public static boolean checkFileExtension(String fileExtension) {
        List<String> fileExtensions = Arrays.asList("png", "jpg", "jpeg");
        return fileExtensions.contains(fileExtension);
    }
}
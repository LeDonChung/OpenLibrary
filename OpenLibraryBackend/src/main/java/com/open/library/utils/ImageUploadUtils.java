package com.open.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUploadUtils {
    private final String UPLOAD_FOLDER_IMAGE_USER = "D:\\MyProject\\OpenLibrary\\OpenLibraryBackend\\src\\main\\resources\\static\\imageUser";

    public boolean checkExistedImageUser(MultipartFile imageUser) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER_IMAGE_USER + File.separator, imageUser.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }

    public boolean uploadImageUser(MultipartFile imageUser) {
        boolean isUpload = false;
        try {
            Files.copy(imageUser.getInputStream(), Paths.get(UPLOAD_FOLDER_IMAGE_USER + File.separator, imageUser.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }
}

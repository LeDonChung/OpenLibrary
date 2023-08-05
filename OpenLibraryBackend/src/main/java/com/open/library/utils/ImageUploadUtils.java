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
    private final String UPLOAD_FOLDER_BOOK_COVER = "D:\\MyProject\\OpenLibrary\\OpenLibraryBackend\\src\\main\\resources\\static\\bookCover";
    private final String UPLOAD_FOLDER_IMAGE_AUTHOR = "D:\\MyProject\\OpenLibrary\\OpenLibraryBackend\\src\\main\\resources\\static\\imageAuthor";
    public boolean checkExistedImageAuthor(MultipartFile imageAuthor) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER_IMAGE_AUTHOR + File.separator, imageAuthor.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }

    public boolean uploadImageAuthor(MultipartFile imageAuthor) {
        boolean isUpload = false;
        try {
            Files.copy(imageAuthor.getInputStream(), Paths.get(UPLOAD_FOLDER_IMAGE_AUTHOR + File.separator, imageAuthor.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }
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

    public boolean checkExistedBookCover(MultipartFile bookCover) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER_BOOK_COVER + File.separator, bookCover.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }

    public boolean uploadBookCover(MultipartFile bookCover) {
        boolean isUpload = false;
        try {
            Files.copy(bookCover.getInputStream(), Paths.get(UPLOAD_FOLDER_BOOK_COVER + File.separator, bookCover.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }
}

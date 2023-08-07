package com.open.library.service;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseService {
    Object uploadFile(MultipartFile file);
    Object updateFile(String fileNameOld, MultipartFile file);
    boolean isExists(MultipartFile file);
}

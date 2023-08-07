package com.open.library.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.open.library.service.FirebaseService;
import com.open.library.utils.OpenLibraryUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {

            ClassLoader classLoader = OpenLibraryUtils.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
            FileInputStream serviceAccount =new FileInputStream(file.getAbsolutePath());
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build().getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            if(isExists(file)) {
                return "";
            }
            String fileName = generateFileName(file.getOriginalFilename());
            Map<String, String> map = new HashMap<>();
            map.put("firebaseStorageDownloadTokens", fileName);
            BlobId blobId = BlobId.of("openlibrary-d2a93.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setMetadata(map)
                    .setContentType(file.getContentType())
                    .build();
            Blob blob = storage.create(blobInfo, file.getInputStream());
            return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", "openlibrary-d2a93.appspot.com", fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Object updateFile(String fileNameOld, MultipartFile file) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("firebaseStorageDownloadTokens", fileNameOld);
            BlobId blobId = BlobId.of("openlibrary-d2a93.appspot.com", fileNameOld);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setMetadata(map)
                    .setContentType(file.getContentType())
                    .build();
            Blob blob = storage.create(blobInfo, file.getInputStream());
            return fileNameOld;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean isExists(MultipartFile file) {
        try {
            BlobId blobId = BlobId.of("openlibrary-d2a93.appspot.com", generateFileName(file.getOriginalFilename()));
            Blob blob = storage.get(blobId);
            if(blob != null) {
                return true;
            }
            return  false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String generateFileName(String originalFileName) {
        return String.format("%s", originalFileName);
    }
//
//    private String getExtension(String originalFileName) {
//        return StringUtils.getFilenameExtension(originalFileName);
//    }

}

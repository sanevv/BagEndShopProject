package com.github.semiprojectshop.web.sihu.storage;

import com.github.semiprojectshop.service.sihu.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageController {
    private final StorageService storageService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> fileListUploadTester(@RequestPart List<MultipartFile> fileList){
        Path uploadDir = storageService.createFileDirectory("test", "fileList");

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : fileList) {
            if (!file.isEmpty()) {
                String path = storageService.returnTheFilePathAfterTransfer(file, uploadDir, "앞부분에_");

                filePaths.add(path);
            }
        }
        return filePaths;
    }



}

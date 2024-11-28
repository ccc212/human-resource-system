package com.hrsys.strategy.storage;

import com.hrsys.enums.StorageModeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Service
public class StorageStrategyContext {

    @Value("${storage.mode}")
    private String storageMode;

    @Autowired
    private Map<String, StorageStrategy> storageStrategyMap;

    public String executeUploadStrategy(String path, MultipartFile file) {
        return storageStrategyMap.get(StorageModeEnum.getStrategy(storageMode)).uploadFile(path, file);
    }

    public void executeDownloadStrategy(String path, String fileName, HttpServletResponse response) {
        storageStrategyMap.get(StorageModeEnum.getStrategy(storageMode)).downloadFile(path, fileName, response);
    }

    public MultipartFile executeGetFileStrategy(String path, String fileName) {
        return storageStrategyMap.get(StorageModeEnum.getStrategy(storageMode)).getFile(path, fileName);
    }

    public void executeDeleteStrategy(String path, String fileName) {
        storageStrategyMap.get(StorageModeEnum.getStrategy(storageMode)).deleteFile(path, fileName);
    }
}

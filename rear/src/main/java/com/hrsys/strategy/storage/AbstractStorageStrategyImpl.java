package com.hrsys.strategy.storage;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.util.FileUtil;
import lombok.Cleanup;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Service
public abstract class AbstractStorageStrategyImpl implements StorageStrategy {

    @Override
    public String uploadFile(String path, MultipartFile file) {
        try {
            @Cleanup
            InputStream inputStream = file.getInputStream();
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            String fileName = FileUtil.getMd5(inputStream);
            fileName += extName;
            upload(path, fileName, inputStream);
            return getFileUrl(fileName);
        } catch (Exception e) {
            throw new BizException(StatusCodeEnum.UPLOAD_FAILED);
        }
    }

    @Override
    public void downloadFile(String path, String fileName, HttpServletResponse response) {
        if (!exists(path + fileName)) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        try {
            download(path, fileName, response);
        } catch (Exception e) {
            throw new BizException(StatusCodeEnum.DOWNLOAD_FAILED);
        }
    }

    @Override
    public MultipartFile getFile(String path, String fileName) {
        if (!exists(path + fileName)) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        return get(path, fileName);
    }

    @Override
    public void deleteFile(String path, String fileName) {
        delete(path, fileName);
    }

    public abstract Boolean exists(String filePath);

    public abstract void upload(String path, String fileName, InputStream inputStream) throws IOException;

    public abstract void download(String path, String fileName, HttpServletResponse response);

    public abstract MultipartFile get(String path, String fileName);

    public abstract void delete(String path, String fileName);

    public abstract String getFileUrl(String fileName);

}

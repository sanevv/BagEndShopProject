package com.github.semiprojectshop.service.sanhae.product;

import com.github.semiprojectshop.config.FtpProperties;
import com.github.semiprojectshop.web.support.FtpClient;
import com.github.semiprojectshop.web.support.PathTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final PathTranslator pathTranslator;
    private final FtpClient ftpClient;
    private final FtpProperties ftpProperties;

    // 업로드 후 DB 저장용 공개 경로 반환
    // withHost=true 이면 ftp.host + 상대경로 결합한 전체 URL 반환
    public String uploadProductImage(int productId, MultipartFile file, String prefix, boolean withHost) throws IOException {
        if (file == null || file.isEmpty()) return null;
        String storedName = buildStoredFilename(file.getOriginalFilename(), prefix);
        String publicPath = pathTranslator.buildPublicPath(productId, storedName);   // 예: /uploads/product/16/...
        String ftpPath = pathTranslator.publicToFtp(publicPath);                     // 실제 FTP 업로드 경로
        ftpClient.upload(ftpPath, file.getInputStream());
        return withHost ? buildFullUrl(publicPath) : publicPath;
    }

    // prefix 적용
    private String buildStoredFilename(String original, String prefix) {
        String ext = "";
        if (original != null) {
            int dot = original.lastIndexOf('.');
            if (dot > -1) ext = original.substring(dot);
        }
        String safePrefix = (prefix == null || prefix.isBlank()) ? "sub_" : prefix;
        long now = Instant.now().toEpochMilli();
        int rand = ThreadLocalRandom.current().nextInt(1_000_000);
        return safePrefix + now + "_" + rand + ext;
    }

    private String buildFullUrl(String publicPath) {
        if (publicPath == null || publicPath.isBlank()) return null;
        String host = ftpProperties.getHost();
        if (host == null || host.isBlank()) return publicPath; // 설정 없으면 상대 경로 반환

        host = host.trim();
        // 끝의 / 제거
        while (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        // 스킴 없으면 http:// 추가
        if (!host.matches("(?i)^[a-z][a-z0-9+.+-]*://.*")) {
            host = "http://" + host;
        }
        return host + (publicPath.startsWith("/") ? publicPath : "/" + publicPath);
    }
}

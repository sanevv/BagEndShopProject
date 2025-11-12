package com.github.semiprojectshop.web.support;

import com.github.semiprojectshop.config.FtpProperties;
import org.springframework.stereotype.Component;

@Component
public class PathTranslator {

    private final String basePath;   // 물리 루트 (/html/uploads)
    private final String remoteDir;  // 공개 루트 (/uploads)

    public PathTranslator(FtpProperties props) {
        this.basePath  = normalizeNoTrailing(props.getBasePath());
        this.remoteDir = normalizeNoTrailing(props.getRemoteDir());
    }

    // DB 저장용 공개 경로 구성
    public String buildPublicPath(int productId, String filename) {
        return remoteDir + "/product/" + productId + "/" + filename;
    }

    // 공개 → FTP 물리
    public String publicToFtp(String publicPath) {
        if (publicPath == null) return null;
        String clean = ensureLeadingSlash(publicPath.trim());
        if (!clean.startsWith(remoteDir + "/")) return clean; // 이미 절대 경로이거나 예상 밖
        String remainder = clean.substring(remoteDir.length()); // "/product/.."
        return basePath + remainder;
    }

    // FTP 물리 → 공개
    public String ftpToPublic(String ftpPath) {
        if (ftpPath == null) return null;
        String clean = ensureLeadingSlash(ftpPath.trim());
        if (!clean.startsWith(basePath + "/")) return clean;
        String remainder = clean.substring(basePath.length()); // "/product/.."
        return remoteDir + remainder;
    }

    // 업로드 후 혹시 /html 경로가 넘어온 경우 DB 저장 전 정규화
    public String normalizeForDb(String anyPath) {
        if (anyPath == null) return null;
        String p = anyPath.trim();
        if (p.startsWith(basePath + "/")) return ftpToPublic(p);
        return p;
    }

    private String normalizeNoTrailing(String p) {
        if (p == null || p.isBlank()) return "";
        String v = p.trim();
        if (!v.startsWith("/")) v = "/" + v;
        if (v.endsWith("/")) v = v.substring(0, v.length() - 1);
        return v;
    }
    private String ensureLeadingSlash(String p) {
        return p.startsWith("/") ? p : "/" + p;
    }
}

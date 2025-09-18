package com.github.semiprojectshop.web.support;

import com.github.semiprojectshop.config.FtpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FtpUrlHelper {
    private final FtpProperties ftp;

    // /html/uploads/... -> /uploads/... 만 반환 (호스트 제외)
    public String toPublicPath(String rawPath) {
        if (rawPath == null || rawPath.isBlank()) return rawPath;
        if (isAbsoluteUrl(rawPath)) return rawPath; // 이미 http(s)
        String p = rawPath.replaceFirst("^/html", ""); // /html 제거
        return p;
    }

    // 호스트(프로토콜 포함) 정규화
    public String normalizedHost() {
        String host = ftp.getHost();
        if (host == null || host.isBlank()) return "";
        if (!host.startsWith("http://") && !host.startsWith("https://")) {
            host = "http://" + host; // 필요 시 https 로 변경
        }
        return host.replaceAll("/+$", "");
    }

    // 최종 전체 URL (이미 절대면 그대로)
    public String buildFullUrl(String rawPath) {
        if (rawPath == null || rawPath.isBlank()) return rawPath;
        if (isAbsoluteUrl(rawPath)) return rawPath;
        String publicPath = toPublicPath(rawPath);
        if (!publicPath.startsWith("/")) publicPath = "/" + publicPath;
        return normalizedHost() + publicPath;
    }

    private boolean isAbsoluteUrl(String v) {
        return v.startsWith("http://") || v.startsWith("https://");
    }
}

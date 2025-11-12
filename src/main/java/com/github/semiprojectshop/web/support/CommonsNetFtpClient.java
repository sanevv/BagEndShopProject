package com.github.semiprojectshop.web.support;

import com.github.semiprojectshop.config.FtpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonsNetFtpClient implements FtpClient {

    private final FtpProperties props;

    @Override
    public void upload(String absoluteFtpPath, InputStream input) throws IOException {
        if (absoluteFtpPath == null || absoluteFtpPath.isBlank()) {
            throw new IllegalArgumentException("FTP 경로 비어있음");
        }
        FTPClient client = new FTPClient();
        try {
            client.connect(props.getHost(), props.getPort());
            if (!client.login(props.getUser(), props.getPass())) {
                throw new IOException("FTP 로그인 실패");
            }
            // 필요하면 패시브 모드 사용:
            // client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setControlEncoding(StandardCharsets.UTF_8.name());

            String norm = normalize(absoluteFtpPath);
            String dir = norm.substring(0, norm.lastIndexOf('/'));
            String filename = norm.substring(norm.lastIndexOf('/') + 1);

            ensureDirs(client, dir);
            if (!client.storeFile(filename, input)) {
                throw new IOException("업로드 실패 reply=" + client.getReplyString());
            }
            log.debug("FTP 업로드 성공 {}", norm);
        } finally {
            safeClose(client);
        }
    }

    private void ensureDirs(FTPClient c, String fullDir) throws IOException {
        if (fullDir == null || fullDir.isEmpty() || "/".equals(fullDir)) return;
        String[] parts = fullDir.split("/");
        StringBuilder path = new StringBuilder();
        for (String p : parts) {
            if (p.isBlank()) continue;
            path.append("/").append(p);
            if (!c.changeWorkingDirectory(path.toString())) {
                if (!c.makeDirectory(path.toString())) {
                    throw new IOException("디렉터리 생성 실패: " + path);
                }
                if (!c.changeWorkingDirectory(path.toString())) {
                    throw new IOException("디렉터리 이동 실패: " + path);
                }
            }
        }
    }

    private String normalize(String p) {
        String v = p.replace('\\', '/').trim();
        if (!v.startsWith("/")) v = "/" + v;
        if (v.endsWith("/")) v = v.substring(0, v.length() - 1);
        return v;
    }

    private void safeClose(FTPClient c) {
        try {
            if (c.isConnected()) {
                try { c.logout(); } catch (IOException ignored) {}
                c.disconnect();
            }
        } catch (IOException ignored) {}
    }
}

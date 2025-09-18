package com.github.semiprojectshop.service.sanhae;

import com.github.semiprojectshop.config.FtpProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FtpUploadService {

    private static final Logger log = LoggerFactory.getLogger(FtpUploadService.class);
    private final FtpProperties ftp;

    // 예: application.yml 에서 주입
    @Value("${ftp.host}") private String host;
    @Value("${ftp.port}") private int port;
    @Value("${ftp.user}") private String username;
    @Value("${ftp.pass}") private String password;
    // 업로드 루트(절대 경로) 예: /upload 또는 /home/ftpuser/upload
    @Value("${ftp.base-path}") private String basePath;

    public String uploadFile(String subDir, String filename, InputStream in) {
        FTPClient client = new FTPClient();
        try {
            client.setConnectTimeout(10_000);
            client.setControlEncoding("UTF-8"); // 서버 인코딩이 EUC-KR이면 바꿀 것
            client.connect(host, port);
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                throw new IllegalStateException("FTP 접속 실패 reply=" + client.getReplyString());
            }
            if (!client.login(username, password)) {
                throw new IllegalStateException("로그인 실패 reply=" + client.getReplyString());
            }
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);

            String targetDir = normalizePath(basePath, subDir); // basePath + subDir
            ensureDirectories(client, targetDir);

            if (!client.changeWorkingDirectory(targetDir)) {
                throw new IllegalStateException("changeWorkingDirectory 실패: " + targetDir + " reply=" + client.getReplyString());
            }

            String remoteName = filename;
            if (!client.storeFile(remoteName, in)) {
                throw new IllegalStateException("파일 업로드 실패: " + targetDir + "/" + remoteName + " reply=" + client.getReplyString());
            }
            return targetDir + "/" + remoteName;
        } catch (IOException e) {
            throw new IllegalStateException("FTP 업로드 I/O 오류: " + e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
            if (client.isConnected()) {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ignored) {}
            }
        }
    }


    private String normalizePath(String base, String sub) {
        String b = base.endsWith("/") ? base.substring(0, base.length()-1) : base;
        if (sub == null || sub.isBlank()) return b;
        String s = sub.startsWith("/") ? sub.substring(1) : sub;
        return b + "/" + s;
    }

    // 디렉터리 생성
    private void ensureDirectories(FTPClient client, String absolutePath) throws IOException {
        // 절대경로
        if (!absolutePath.startsWith("/")) {
            throw new IllegalArgumentException("absolutePath 는 절대경로여야 함: " + absolutePath);
        }
        String[] segments = absolutePath.split("/");
        StringBuilder current = new StringBuilder();
        for (String seg : segments) {
            if (seg == null || seg.isBlank()) continue;
            current.append("/").append(seg);

            String path = current.toString();
            if (client.changeWorkingDirectory(path)) {
                continue; // 이미 존재
            }
            // 존재 안 하면 생성
            if (!client.makeDirectory(path)) {
                int code = client.getReplyCode();
                String reply = client.getReplyString();
                log.error("디렉터리 생성 실패 path={} code={} reply={}", path, code, reply);
                // 550이면 상위 디렉터리 권한/존재 문제 가능
                throw new IllegalStateException("디렉터리 생성 실패: " + path + " reply=" + reply);
            } else {
                log.info("디렉터리 생성 성공 path={}", path);
            }
        }
    }
}

package com.github.semiprojectshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component

//ftp.host, ftp.port 등 설정값을 필드 값에 자동 주입해줌!
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
    private String host;
    private int port;
    private String user;
    private String pass;
    private String remoteDir;
}

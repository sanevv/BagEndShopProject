package com.github.semiprojectshop.web.support;

import java.io.IOException;
import java.io.InputStream;

public interface FtpClient {
    void upload(String ftpFullPath, InputStream in) throws IOException;
}
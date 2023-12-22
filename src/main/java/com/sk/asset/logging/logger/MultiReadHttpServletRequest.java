package com.sk.asset.logging.logger;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;

// request body는 InputStream으로 읽을 수 있음
// InputStream은 한번 읽으면 다시 읽을 수 없음
// 따라서, LoggingFilter에서 HttpServletRequest를 HttpServletRequestWrapper를 상속한 MultiReadHttpServletRequest로 변환해서 전달
// MultiReadHttpServletRequest는 body를 읽어와서 cachedBytes에 담아두고 여러번 읽을 수 있게 만듦
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null)
            cacheInputStream();

        //cachedBytes를 읽어서 ServletInputStream으로 반환
        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        // body를 cachedBytes에 복사해서 담아둠
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }


    /* 캐시된 request body를 읽는 InputStream */

    private static class CachedServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream buffer;

        public CachedServletInputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}

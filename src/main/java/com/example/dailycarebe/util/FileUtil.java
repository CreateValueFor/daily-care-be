package com.example.dailycarebe.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FileUtil {
    private static final Tika tika = new Tika();

    public static Resource getResourceWithBase64Encoding(byte[] data, String fileName) {
        byte[] encodedData = Base64.getEncoder().encode(data);
        return getResource(encodedData, fileName);
    }

    public static Resource getResource(byte[] data, String fileName) {
        ByteArrayResource byteArrayResource = new ByteArrayResource(data) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        return byteArrayResource;
    }

    public static Resource getResource(File file) {
        try {
            return getResource(FileUtils.readFileToByteArray(file), file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Resource getResource(String path) {
        ClassPathResource byteArrayResource = new ClassPathResource(path) {
            @Override
            public String getFilename() {
                return super.getFilename();
            }
        };
        return byteArrayResource;
    }

    public static File toFile(MultipartFile multipartFile) {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static HttpHeaders generateHttpHeaders(HttpServletRequest httpServletRequest, Resource resource) {
        return generateHttpHeaders(httpServletRequest, resource, null);
    }

    public static HttpHeaders generateHttpHeaders(HttpServletRequest httpServletRequest, Resource resource, Long ttlSeconds) {
        HttpHeaders headers = new HttpHeaders();
        String contentType = null;
        Long contentLength = null;
        try {
            InputStream inputStream = resource.getInputStream();
            contentType = tika.detect(inputStream);
            contentLength = resource.contentLength();
            inputStream.close();
        } catch (IOException ignored) {
        }
        if (contentType == null) {
            contentType = MimeTypes.OCTET_STREAM;
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        if (contentLength != null) {
            headers.setContentLength(contentLength);
        }
        CacheControl cacheControl;
        if (ttlSeconds != null) {
            cacheControl = CacheControl.maxAge(ttlSeconds, TimeUnit.SECONDS).cachePublic();
        } else {
            cacheControl = CacheControl.noCache();
        }
        headers.add(HttpHeaders.CACHE_CONTROL, cacheControl.getHeaderValue());
        if (resource.getFilename() != null) {
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(resource.getFilename(), StandardCharsets.UTF_8).build();
            headers.setContentDisposition(contentDisposition);
        }

        headers.add("Content-Transfer-Encoding", "binary");
        return headers;
    }

    public static File resourceFrom(Resource resource) {
        try {
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getType(byte[] bytes) {
        return tika.detect(bytes);
    }
}

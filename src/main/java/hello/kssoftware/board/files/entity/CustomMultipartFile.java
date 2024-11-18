package hello.kssoftware.board.files.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter @Setter
public class CustomMultipartFile implements MultipartFile {
    private final File file;

    protected String uploadFileName;

    public CustomMultipartFile(File file, String uploadFileName) {
        this.file = file;
        this.uploadFileName = uploadFileName;
    }

    public String getUploadFileName() {
        if (uploadFileName == null) {
            return "파일존재하지않음";
        }
        return uploadFileName;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        return "application/octet-stream";
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return java.nio.file.Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        java.nio.file.Files.copy(file.toPath(), dest.toPath());
    }
}


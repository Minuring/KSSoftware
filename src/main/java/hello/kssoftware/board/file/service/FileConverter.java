package hello.kssoftware.board.file.service;

import hello.kssoftware.board.file.entity.CustomMultipartFile;
import hello.kssoftware.board.file.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public class FileConverter {

    public static MultipartFile convertToMultipartFile(UploadFile uploadFile) throws IOException {
        File file = new File(getFullPath(uploadFile.getStoreFileName()));
        String uploadFileName = uploadFile.getUploadFileName();

        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }
        return new CustomMultipartFile(file, uploadFileName);
    }

    private static String getFullPath(String storeFileName) {
        return "/usr/local/app/files/" + storeFileName;
    }
}


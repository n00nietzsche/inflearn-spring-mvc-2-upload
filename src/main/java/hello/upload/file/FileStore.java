package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 여러개의 파일 저장
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }

        return storeFileResult;
    }

    /**
     * 단일 파일 저장
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new UploadFile(originalFilename, storeFilename);
    }

    /**
     * 서버 저장용으로 유니크한 파일 이름 생성
     * 서버에 저장될 이름은 각기 달라야 하지만, 확장자는 유지해야 한다.
     */
    private String createStoreFilename(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + fileExtension;
    }

    /**
     * 파일 이름에서 확장자 추출
     */
    private String getFileExtension(String originalFilename) {
        if(StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
            int position = originalFilename.lastIndexOf(".");
            return originalFilename.substring(position + 1);
        }

        return "";
    }
}

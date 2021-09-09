package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
    private String uploadFileName; // 업로드한 파일 이름
    private String storeFileName; // 시스템에 저장되는 파일 이름
}

package com.github.semiprojectshop.service.sihu;

import com.github.semiprojectshop.config.web.WebConfig;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;


@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {
    private final WebConfig webConfig;


    /**
     * 파일이 저장될 폴더 경로 생성 메서드 1번째 인자는 첫번째 폴더 이름, 2번째 인자는 두번째 폴더 이름
     * 1번째 폴더는 테이블명, 2번째 폴더는 해당 행의 프라이머리 키 값 추천.
     * 3번째 인자는 폴더가 3개까지 필요할시 사용
     * 예시) product/1, product/2, product/3, ... 등등
     */
    public Path createFileDirectory(String firstFolder, String secondFolder, String thirdFolder) {
        Path uploadDir = Paths.get(webConfig.getEndPath(), firstFolder, secondFolder, thirdFolder);
        createFolder(uploadDir);
        return uploadDir;
    }

    /**
     * 폴더경로가 두개만 필요할경우 파라미터를 두개만 넣고 보냄
     */
    public Path createFileDirectory(String firstFolder, String secondFolder) {
        Path uploadDir = Paths.get(webConfig.getEndPath(), firstFolder, secondFolder);
        createFolder(uploadDir);
        return uploadDir;
    }

    /**
     * 폴더 생성 메서드 실패시 log에 기록을 남기고 익셉션을 던짐
     */
    private void createFolder(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("폴더 생성 실패: {}", e.getMessage(), e);
            throw CustomMyException.fromMessage("폴더 생성에 실패했습니다. 관리자에게 문의해주세요.");
        }
    }

    /**
     * 원본 파일명을 반환하는 메서드 필요시 사용 파일이 없는상태에서 호출시 null 이 반환 되니 주의 !!
     */
    public String getOriginalFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    /**
     * uploadPath에 파일을 저장 후 Web 에서 사용되는 경로를 반환
     * 파일이 없을 시 null 반환 주의 !!!
     * prefix 는 파일 이름 앞에 붙는 문자열로 예를들어 productImage1_ 등등
     */
    public String returnTheFilePathAfterTransfer(MultipartFile file, Path uploadDir, String prefix) {
        try {
            checkFile(file);// 파일이 비어있으면 null 반환
        } catch (NullPointerException e) {
            return null;
        }
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        Path destination = uploadDir.resolve(prefix + originalFileName);
        saveFile(file, destination);
        return createWebPath(destination);
    }




    /**
     * 파일 앞에 붙일 단어가 없을때. 그냥 원본파일명으로 저장된다.
     */
    public String returnTheFilePathAfterTransfer(MultipartFile file, Path uploadDir) {
        return this.returnTheFilePathAfterTransfer(file, uploadDir, "");
    }

    private void saveFile(MultipartFile file, Path destination) {
        try {
            file.transferTo(destination.toFile());
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw CustomMyException.fromMessage("파일 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }


    /**
     * byte[] 파일을 저장하는 메소드
     */
    public String returnTheFilePathAfterTransfer(byte[] file, Path uploadDir, String fileName) {

//        String originalFileName = Objects.requireNonNull(file.());
        Path destination = uploadDir.resolve(fileName + ".jpg");
        saveFile(file, destination);
        return createWebPath(destination);
    }
    /**
        * byte[] 파일을 저장하는 메소드
     */
    private void saveFile(byte[] file, Path destination) {
        try {
            Files.write(destination, file);
            log.info("파일이 성공적으로 저장되었습니다: {}", destination);
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw CustomMyException.fromMessage("파일 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }


    private void checkFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new NullPointerException("파일이 비어있음");
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            log.error("알 수 없는 오류 : 파일 이름이 비어있습니다. 확인값 : {} ", file.getOriginalFilename());
            throw CustomMyException.fromMessage("알 수 없는 오류 : 파일 이름이 비어있습니다.");
        }
    }


    /**파일 경로를 웹에서 접근할 수 있는 경로로 변환하는 메소드*/
    private String createWebPath(Path destination) {
        // 파일 경로를 '/'로 변경하고, uploadPath를 '/uploads/'로 대체
        if (destination == null)
            return null;
        String normalizedPath = normalizePath(destination.toString());
        return normalizedPath.replaceFirst("^" + Pattern.quote(webConfig.getEndPath()), webConfig.getUploadPath());
    }

    /**
     * 경로를  '\'  에서 '/'로 변경하는 메소드
     */
    private String normalizePath(String path) {
        return path.replace('\\', '/');
    }


}

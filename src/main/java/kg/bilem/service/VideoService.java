package kg.bilem.service;

import kg.bilem.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    String saveVideo(MultipartFile file) throws IOException;

    ResponseEntity<String> saveVideoForLesson(Long lessonId, String videoUrl, MultipartFile file, User user) throws IOException;
}

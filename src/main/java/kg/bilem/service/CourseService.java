package kg.bilem.service;

import kg.bilem.dto.course.RequestCourseDTO;
import kg.bilem.dto.course.ResponseCourseDTO;
import kg.bilem.dto.course.ResponseMainCourseDTO;
import kg.bilem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    ResponseEntity<String> signUpForCourse(Long courseId, User user);

    ResponseEntity<String> leaveCourse(Long courseId, User user);

    ResponseEntity<String> sendCourseForChecking(Long courseId, User user);

    ResponseCourseDTO createCourse(RequestCourseDTO courseDTO, User user);

    ResponseEntity<String> editCourse(Long courseId, RequestCourseDTO courseDTO, User user);

    ResponseEntity<String> archiveCourse(Long courseId, User user);

    ResponseEntity<String> deleteCourse(Long courseId, User user);

    ResponseCourseDTO getCourseById(Long courseId);

    Page<ResponseMainCourseDTO> getAllCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getTopCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getNewestAndFreeCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getPopularAndFreeCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getNewestAndPaidCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getPopularAndPaidCourses(Pageable pageable);

    Page<ResponseMainCourseDTO> getAllCoursesWithSearchByQueryAndLanguageAndCourseType(String query, String language, String courseType, Pageable pageable);

    ResponseEntity<String> approveCourse(Long courseId, User user);

    ResponseEntity<String> rejectCourse(Long courseId, String reason, User user);

    Page<ResponseMainCourseDTO> getCoursesOnChecking(Pageable pageable, User user);

    Page<ResponseMainCourseDTO> getCoursesOfTeacher(Pageable pageable, User user);

    Page<ResponseMainCourseDTO> getCoursesOfStudent(Pageable pageable, User user);
}

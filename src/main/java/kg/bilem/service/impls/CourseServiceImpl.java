package kg.bilem.service.impls;

import kg.bilem.dto.course.RequestCourseDTO;
import kg.bilem.dto.course.ResponseCourseDTO;
import kg.bilem.enums.CourseType;
import kg.bilem.enums.Language;
import kg.bilem.enums.Role;
import kg.bilem.enums.Status;
import kg.bilem.exception.AlreadyExistException;
import kg.bilem.exception.NotFoundException;
import kg.bilem.model.Course;
import kg.bilem.model.User;
import kg.bilem.repository.CourseRepository;
import kg.bilem.repository.SubcategoryRepository;
import kg.bilem.repository.UserRepository;
import kg.bilem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kg.bilem.dto.course.ResponseCourseDTO.toResponseCourseDTO;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public ResponseCourseDTO createCourse(RequestCourseDTO courseDTO, User user) {
        if (courseRepository.existsByTitle(courseDTO.getTitle())) {
            throw new AlreadyExistException("Курс с таким названием уже существует");
        }

        if (user.getRole() == Role.STUDENT) {
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        }

        Course course = buildCourse(courseDTO, user);
        course.setStatus(Status.CHECKING);

        return toResponseCourseDTO(courseRepository.save(course));
    }

    @Override
    public ResponseEntity<String> editCourse(Long courseId, RequestCourseDTO courseDTO, User user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс с айди " + courseId + " не найден"));

        if(user.getRole() != Role.ADMIN || !user.getEmail().equals(course.getOwner().getEmail())){
            return ResponseEntity.badRequest().body("Вы не имеете права на редактирование данного курса");
        }

        course = buildCourse(courseDTO, user);
        course.setId(courseId);
        course.setStatus(Status.NOT_ACTIVATED);
        courseRepository.save(course);

        return ResponseEntity.ok("Курс отправлен на модерацию");
    }

    private Course buildCourse(RequestCourseDTO courseDTO, User user) {
        Set<User> teachers = new HashSet<>();
        if(courseDTO.getTeacherIds() != null) {
            for (Long id : courseDTO.getTeacherIds()) {
                User teacher = userRepository.findById(id)
                        .filter(t -> t.getRole() == Role.TEACHER)
                        .orElseThrow(() -> new NotFoundException("Преподаватель с айди " + id + " не найден"));
                teachers.add(teacher);
            }
        }

        return Course.builder()
                .title(courseDTO.getTitle())
                .courseType(CourseType.of(courseDTO.getCourseType()))
                .imageUrl(courseDTO.getImageUrl())
                .videoUrl(courseDTO.getVideoUrl())
                .description(courseDTO.getDescription())
                .whatStudentGet(courseDTO.getWhatStudentGet())
                .price(CourseType.of(courseDTO.getCourseType()) == CourseType.PAID ? courseDTO.getPrice() : 0)
                .subcategory(subcategoryRepository.findById(courseDTO.getSubcategoryId())
                        .orElseThrow(() -> new NotFoundException("Подкатегория с таким айди не найдена")))
                .teachers(teachers)
                .students(new HashSet<>())
                .owner(user)
                .language(Language.of(courseDTO.getLanguage()))
                .build();

    }
}

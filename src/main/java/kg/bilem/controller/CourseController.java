package kg.bilem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.bilem.dto.course.RequestCourseDTO;
import kg.bilem.dto.course.ResponseCourseDTO;
import kg.bilem.dto.course.ResponseMainCourseDTO;
import kg.bilem.model.User;
import kg.bilem.service.impls.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с курсами",
        description = "В этом контроллере есть возможность получения, добавления и изменения курсов"
)
public class CourseController {
    private final CourseServiceImpl courseService;

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание курса"
    )
    public ResponseCourseDTO createCourse(@RequestBody RequestCourseDTO courseDTO,
                                          @AuthenticationPrincipal User user) {
        return courseService.createCourse(courseDTO, user);
    }

    @PutMapping("/edit/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Редактирование курса"
    )
    public ResponseEntity<String> editCourse(@PathVariable Long id, 
                                             @RequestBody RequestCourseDTO courseDTO,
                                             @AuthenticationPrincipal User user){
        return courseService.editCourse(id, courseDTO, user);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех курсов с основной информацией"
    )
    public Page<ResponseMainCourseDTO> getAllCourses(@PageableDefault Pageable pageable){
        return courseService.getAllCourses(pageable);
    }

    @GetMapping("/top")
    @Operation(
            summary = "Получение лучших курсов по количеству студентов"
    )
    public Page<ResponseMainCourseDTO> getTopCourses(@PageableDefault Pageable pageable){
        return courseService.getTopCourses(pageable);
    }

    @GetMapping("/newest-and-free-courses")
    @Operation(
            summary = "Получение всех новейших бесплатных курсов"
    )
    public Page<ResponseMainCourseDTO> getNewestAndFreeCourses(@PageableDefault Pageable pageable){
        return courseService.getNewestAndFreeCourses(pageable);
    }

    @GetMapping("/popular-and-free-courses")
    @Operation(
            summary = "Получение всех популярных бесплатных курсов"
    )
    public Page<ResponseMainCourseDTO> getPopularAndFreeCourses(@PageableDefault Pageable pageable){
        return courseService.getPopularAndFreeCourses(pageable);
    }

    @GetMapping("/newest-and-paid-courses")
    @Operation(
            summary = "Получение всех новейших платных курсов"
    )
    public Page<ResponseMainCourseDTO> getNewestAndPaidCourses(@PageableDefault Pageable pageable){
        return courseService.getNewestAndPaidCourses(pageable);
    }

    @GetMapping("/popular-and-paid-courses")
    @Operation(
            summary = "Получение всех популярных платных курсов"
    )
    public Page<ResponseMainCourseDTO> getPopularAndPaidCourses(@PageableDefault Pageable pageable){
        return courseService.getPopularAndPaidCourses(pageable);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Поиск курса по названию"
    )
    public Page<ResponseMainCourseDTO> getAllCoursesWithSearchByQuery(@RequestParam(required = false) String query,
                                                                      @PageableDefault Pageable pageable){
        return courseService.getAllCoursesWithSearchByQuery(query, pageable);
    }

    @GetMapping("/search/{language}")
    @Operation(
            summary = "Поиск курса по названию и языку"
    )
    public Page<ResponseMainCourseDTO> getAllCoursesWithSearchByQueryAndLanguage(@RequestParam(required = false) String query,
                                                                      @PathVariable String language,
                                                                      @PageableDefault Pageable pageable){
        return courseService.getAllCoursesWithSearchByQueryAndLanguage(query, language, pageable);
    }

    @GetMapping("/search/{language}/{course-type}")
    @Operation(
            summary = "Поиск курса по названию и языку"
    )
    public Page<ResponseMainCourseDTO> getAllCoursesWithSearchByQueryAndLanguageAndCourseType(@RequestParam(required = false) String query,
                                                                                 @PathVariable("language") String language,
                                                                                 @PathVariable("course-type") String courseType,
                                                                                 @PageableDefault Pageable pageable){
        return courseService.getAllCoursesWithSearchByQueryAndLanguageAndCourseType(query, language, courseType, pageable);
    }
}

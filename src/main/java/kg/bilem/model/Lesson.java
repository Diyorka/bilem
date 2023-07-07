package kg.bilem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kg.bilem.enums.LessonType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson extends BaseEntity{
    String title;

    String content;

    String videoUrl;

    LessonType lessonType;

    @ManyToOne
    Module module;
}

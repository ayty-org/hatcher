package org.ayty.hatcher.api.v1.course.service;

import lombok.RequiredArgsConstructor;
import org.ayty.hatcher.api.v1.course.exception.CourseNotFoundException;
import org.ayty.hatcher.api.v1.course.jpa.Course;
import org.ayty.hatcher.api.v1.course.jpa.CourseRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateCourseServiceImpl implements UpdateCourseService{

    private final CourseRepository courseRepository;

    @Override
    public void update(Long id, Course obj) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Objeto não encontrado! Id: " + id));
        course.setName(obj.getName());
        course.setDescription(obj.getDescription());
        this.courseRepository.save(course);
    }
}


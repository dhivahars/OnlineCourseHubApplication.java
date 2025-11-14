package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.CourseDto;
import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.dto.MentorStudentDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.MentorRepository;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MentorRepository mentorRepository;
    @Autowired
    StudentRepository studentRepository;

    public CourseDto addCourse(Course course){
        if(courseRepository.existsByTitle(course.getTitle())){
            throw new RuntimeException("Course already exists with title: " + course.getTitle());
        }
        Mentor mentor = mentorRepository.findById(course.getMentor().getId())
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + course.getMentor().getId()));
        if(mentorRepository.findById(course.getMentor().getId()).get().getCourseList().size()>=5)
            throw new RuntimeException("Mentor is not available,choose another mentor to create course");
       return entityToDto(courseRepository.save(course));

    }


    public List<CourseDto> listCourse() {
        if(courseRepository.findAll().isEmpty())
            throw new RuntimeException("No records found...........");
        return courseRepository.findAll().stream().map(this::entityToDto).toList();
    }

    public Optional<CourseDto> showById(long id) {
        if(!courseRepository.existsById(id))
            throw new RuntimeException("No course with Id-"+id+" found");
        return Optional.ofNullable(entityToDto(courseRepository.findById(id).orElseThrow(()->new RuntimeException("Course not found"))));
    }


    public CourseDto updateCourse(long id, Course c) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + c.getId()));
        if(c.getTitle() != null)
            existing.setTitle(c.getTitle());
        if(c.getDescription() != null)
            existing.setDescription(c.getDescription());
        if(c.getCapacity() != 0)
            existing.setCapacity(c.getCapacity());
        if(c.getSkill()!=null)
            existing.setSkill(c.getSkill());


        return entityToDto(courseRepository.save(existing));
    }


    public String deleteCourseById(long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);
            return "Course Deleted Successfully";}

        throw new RuntimeException( "Course Not Found");
    }


    public String getCourseCapacityById(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with Id: " + courseId));
        int availableSeats = course.getCapacity() - course.getEnrollments().size();
        return "Total Seats: " + course.getCapacity() +
                ", Enrolled: " + course.getEnrollments().size() +
                ", Available: " + availableSeats;
    }

    public Course createCourseWithMentor(Course course, Long mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + mentorId));

        course.setMentor(mentor);
        return courseRepository.save(course);
    }
    public Course modifyMentorToCourse(Long courseId, Long mentorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + mentorId));

        course.setMentor(mentor);
        return courseRepository.save(course);
    }
//
public List<MentorStudentDto> getStudentsUnderMentor(Long mentorId) {

  // ✅ Get all courses taught by this mentor
  List<Course> courses = courseRepository.findByMentorId(mentorId);

  // ✅ Collect all enrollments and map them into DTOs
  List<MentorStudentDto> studentList = new ArrayList<>();

  for (Course course : courses) {
    for (Enrollment enrollment : course.getEnrollments()) {
      Student student = enrollment.getStudent();
      MentorStudentDto dto = MentorStudentDto.builder()
        .studentName(student.getName())
        .courseName(course.getTitle())
        .progressPercentage(enrollment.getProgressPercentage())
        .build();
      studentList.add(dto);
    }
  }

  return studentList;
}
//

     public CourseDto entityToDto(Course course){
        CourseDto courseDto=new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setCapacity(course.getCapacity());
        courseDto.setUrl(course.getUrl());
        courseDto.setSkill(course.getSkill());
        courseDto.setMentorName(mentorRepository.findById(course.getMentor().getId()).get());
        courseDto.setPrerequisites(course.getPrerequisites());
        int enrolledCount = (course.getEnrollments() != null) ? course.getEnrollments().size() : 0;
        courseDto.setEnrolledCount(enrolledCount);
        return courseDto;
    }
    public List<CourseDto> getCoursesByMentor(String mentorEmail) {
        if (!mentorRepository.existsByEmail(mentorEmail)) {
            throw new RuntimeException("Mentor not found with ID: " + mentorEmail);
        }

        List<Course> courses = courseRepository.findByMentorEmail(mentorEmail);

        // Convert to DTO list
        return courses.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }




    public  String studentPerCourse(long id) {
        if(!courseRepository.existsById(id))
            throw new RuntimeException("No course With Id"+id+"Exists");
        if(courseRepository.findById(id).get().getEnrollments().isEmpty()||courseRepository.findById(id).get().getEnrollments()==null)
            throw new RuntimeException("No records found.....");
        Course course=courseRepository.getById(id);
        List<String> students=courseRepository.getById(id).getEnrollments().stream().filter(a->a.getCourse().getId()==course.getId()).map(a->a.getStudent().getName()).toList();
        long count=courseRepository.getById(id).getEnrollments().stream().filter(a->a.getCourse().getId()==course.getId()).count();
        return "Course Name:"+course.getTitle()+
                "\n Total number of students:"+count+
                "\n Students List:"+students;
    }


    public String studentProgress(long id) {
        if(!studentRepository.existsById(id))
            throw new RuntimeException("Student doesn't exists...........");
        if(studentRepository.getById(id).getEnrollments().isEmpty())
            throw new RuntimeException("Student has no enrollments.........");

        Student student=studentRepository.getById(id);
        HashMap<String,Double> progress= (HashMap<String, Double>) studentRepository.getById(id).getEnrollments().stream().collect(Collectors.toMap(e->e.getCourse().getTitle(),Enrollment::getProgressPercentage));
        return "Progress of "+studentRepository.getById(id).getName()+":\n"+
                progress;
    }
}

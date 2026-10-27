package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> studentList = new ArrayList<>();

    public StudentController() {
        studentList.add(new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));
        studentList.add(new Student(102, "Tushar Choudhary", "tushar@kiet.edu", "CSE(AI & ML)", 2, 9.15));
        studentList.add(new Student(103, "Vaibhav Saini", "vaibhav@kiet.edu", "CSE", 2, 8.90));
        studentList.add(new Student(104, "Adarsh Kumar", "adarsh@kiet.edu", "CSE", 2, 9.25));
        studentList.add(new Student(105, "Riya Verma", "riya@kiet.edu", "IT", 3, 8.40));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {
        for (Student s : studentList) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }
        return Collections.singletonMap("message", "Student not found");
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        studentList.add(student);
        return student;
    }

    @PutMapping("/{studentId}")
    public Object updateStudent(@PathVariable int studentId, @RequestBody Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getStudentId() == studentId) {
                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());
                return s;
            }
        }
        return Collections.singletonMap("message", "Student not found");
    }

    @DeleteMapping("/{studentId}")
    public Object deleteStudent(@PathVariable int studentId) {
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.getStudentId() == studentId) {
                iterator.remove();
                return Collections.singletonMap("message", "Student deleted successfully");
            }
        }
        return Collections.singletonMap("message", "Student not found");
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String branch, @RequestParam int year) {
        List<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getBranch().equalsIgnoreCase(branch) && s.getYear() == year) {
                result.add(s);
            }
        }
        return result;
    }

    @GetMapping("/topper")
    public List<Student> getToppers(@RequestParam double cgpa) {
        List<Student> toppers = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getCgpa() >= cgpa) {
                toppers.add(s);
            }
        }
        return toppers;
    }
}
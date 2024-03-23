package com.ssvv;

import com.ssvv.domain.Student;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.ValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddStudentTest {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        this.studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    void testAddStudentOnGroup() {
        Student newStudent1 = new Student("123", "Ana", 931, "lupu@gmail.com");
        Student newStudent2 = new Student("1234", "Andrei", -6, "lupu@gmail.com");
        Student newStudent3 = new Student("42345", "Vlad", 0, "lupu@gmail.com");
        this.service.addStudent(newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        this.service.addStudent(newStudent3);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertEquals(students.next(), newStudent3);

        this.service.deleteStudent("123");
        this.service.deleteStudent("42345");
    }

    @Test
    void testAddStudentOnName() {
        Student newStudent1 = new Student("1111", "Ana", 100, "s1@email.com");
        Student newStudent2 = new Student("1111211", "", 100, "s2@email.com");
        Student newStudent3 = new Student("1111211", "Andrei", -3, "s3@email.com");
        this.service.addStudent(newStudent1);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));

        this.service.deleteStudent("1111");
    }

    @Test
    void testAddStudentOnEmail() {
        Student newStudent1 = new Student("1111", "Ana", 333, "lupu@gmail.com");
        Student newStudent2 = new Student("1111211", "", 100, "");
        this.service.addStudent(newStudent1);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));

        this.service.deleteStudent("1111");
    }

    @Test
    void testValidStudent() {
        final Student student = new Student("1", "Edi", 934, "lupu@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("1");
    }

    @Test
    void testInvalidStudent() {
        final Student student = new Student("", "Edi", 934, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidId() {
        final Student student = new Student("1", "Andrei", 933, "a@example.com");
        service.addStudent(student);
        service.deleteStudent("1");
    }

    @Test
    void testEmptyId() {
        final Student student = new Student("", "Edi", 934, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testNullId() {
        final Student student = new Student("null", "Edi", 934, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testInvalidNameCharacters() {
        final Student student = new Student("E", "Test+++---", 934, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testEmptyName() {
        final Student student = new Student("E", "", 934, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidGroup() {
        final Student student = new Student("E", "Edi", 934, "lupu@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("E");
    }

    @Test
    void testInvalidGroupLess() {
        final Student student = new Student("E", "Edi", -1, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testInvalidGroupGreater() {
        final Student student = new Student("E", "Edi", Integer.MAX_VALUE + 1, "lupu@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidEmail() {
        final Student student = new Student("E", "Edi", 934, "lupu@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("E");
    }

    @Test
    void testInvalidEmail() {
        final Student student = new Student("E", "Edi", 934, "com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }
}

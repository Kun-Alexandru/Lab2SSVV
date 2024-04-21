package com.ssvv;

import com.ssvv.curent.Curent;
import com.ssvv.domain.Nota;
import com.ssvv.domain.Student;
import com.ssvv.domain.Tema;
import com.ssvv.repository.NotaXMLRepo;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.NotaValidator;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import com.ssvv.validation.ValidationException;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class IntegrationTest extends TestCase {

    private Service service;

    @BeforeAll
    static void createXML() {
        File xmlStudent = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudent))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File xmlAssignment = new File("fisiere/assignmentsTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignment))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File xmlGrade = new File("fisiere/gradesTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGrade))) {
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
        StudentXMLRepo studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        StudentValidator studentValidator = new StudentValidator();

        TemaXMLRepo temaFileRepository = new TemaXMLRepo("fisiere/assignmentsTest.xml");
        TemaValidator temaValidator = new TemaValidator();

        NotaXMLRepo notaFileRepository = new NotaXMLRepo("fisiere/gradesTest.xml");
        NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);

        this.service = new Service(studentFileRepository, studentValidator, temaFileRepository, temaValidator, notaFileRepository, notaValidator);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
        new File("fisiere/assignmentsTest.xml").delete();
        new File("fisiere/gradesTest.xml").delete();
    }

    private void assertThrows(Runnable runnable) {
        try {
            runnable.run();
            assert(false);
        } catch (ValidationException e) {
            assert(true);
        } catch (Exception e) {
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    public void testValidStudent() {
        final Student student = new Student("1", "Edi", 934, "edi@gmail.com");
        service.addStudent(student);
    }

    @Test
    public void testValidAssignment() {
        final Tema tema = new Tema("1", "d", 5, 5);
        service.addTema(tema);
    }

    @Test
    public void testValidGrade() {
        final Nota nota = new Nota("1", "1", "1", 10, Curent.getStartDate().plusDays(14));
        service.addNota(nota, "feedback");
    }

    @Test
    public void testAllTogether() {
        final Student student = new Student("1", "Edi", 934, "edi@gmail.com");
        final Tema tema = new Tema("1", "d", 5, 5);
        final Nota nota = new Nota("1", "1", "1", 10, Curent.getStartDate().plusDays(14));

        service.addStudent(student);
        service.addTema(tema);
        service.addNota(nota, "f");
    }

    @Test
    public void testStudent() {
        final Student student = new Student("1", "Edi", 9344, "edi@example.com");
        service.addStudent(student);
    }

    @Test
    public void testAssignment() {
        final Tema tema = new Tema("1", "desc", 5, 4);
        service.addTema(tema);
    }

    @Test
    public void testGrade() {

        final Nota nota = new Nota("1", "1", "1", 10, Curent.getStartDate().plusDays(14));
        service.addNota(nota, "nice");
    }
}

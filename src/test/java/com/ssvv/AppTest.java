package com.ssvv;

import com.ssvv.domain.Student;
import com.ssvv.repository.NotaXMLRepo;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.NotaValidator;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import com.ssvv.validation.ValidationException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    Service service;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testValidStudent() {
        final Student student = new Student("1", "Andrei", 933, "a@example.com");
        service.addStudent(student);
    }

    public void testInvalidStudent() {
        final Student student = new Student("", "Andrei", 933, "a@example.com");
        try {
            service.addStudent(student);
            assert(false);
        } catch (ValidationException e) {}
    }
}

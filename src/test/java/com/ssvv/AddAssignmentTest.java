package com.ssvv;

import com.ssvv.domain.Tema;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.TemaValidator;
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

public class AddAssignmentTest {
    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/assignmentsTest.xml");
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
        this.temaFileRepository = new TemaXMLRepo("fisiere/assignmentsTest.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null, this.temaFileRepository, this.temaValidator, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/assignmentsTest.xml").delete();
    }

    @Test
    public void testValidAssignment() {
        final Tema tema = new Tema("1", "d", 10, 10);
        this.service.addTema(tema);
    }

    @Test
    public void testAssignmentLessDeadline() {
        final Tema tema = new Tema("1", "d", -5, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentMoreDeadline() {
        final Tema tema = new Tema("1", "d", 25, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentLessSubmittedAt() {
        final Tema tema = new Tema("1", "d", 10, -5);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentMoreSubmittedAt() {
        final Tema tema = new Tema("1", "d", 10, 25);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testInvalidAssignment() {
        final Tema tema = new Tema("1", "d", 25, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentEmptyId() {
        final Tema tema = new Tema("", "d", 10, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentNullId() {
        final Tema tema = new Tema("null", "d", 10, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    public void testAssignmentEmptyDescription() {
        final Tema tema = new Tema("1", "", 10, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }
}

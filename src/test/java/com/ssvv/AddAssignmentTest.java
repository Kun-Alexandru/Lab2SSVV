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
    void testInvalidTema() {
        final Tema tema = new Tema("", "Description", 5, 3);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    void testValidTema() {
        final Tema tema = new Tema("1", "Description", 5, 3);
        this.service.addTema(tema);
        assertEquals(countTeme(), 1);
        this.service.deleteTema("1");
    }

    @Test
    void testInvalidEmptyId() {
        final Tema tema = new Tema("", "Description", 5, 3);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    void testInvalidNullId() {
        final Tema tema = new Tema(null, "Description", 5, 3);
        assertThrows(NullPointerException.class, () -> this.service.addTema(tema));
    }

    @Test
    void testInvalidEmptyDescription() {
        final Tema tema = new Tema("1", "", 5, 3);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    void testInvalidDeadlineOutOfRange() {
        final Tema tema = new Tema("1", "Description", 0, 3);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    @Test
    void testInvalidPrimireOutOfRange() {
        final Tema tema = new Tema("1", "Description", 5, 15);
        assertThrows(ValidationException.class, () -> this.service.addTema(tema));
    }

    private int countTeme() {
        int count = 0;
        for (Tema t : this.service.getAllTeme()) {
            count++;
        }
        return count;
    }

}

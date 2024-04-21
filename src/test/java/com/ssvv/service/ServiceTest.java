package com.ssvv.service;

import com.ssvv.domain.Nota;
import com.ssvv.repository.NotaXMLRepo;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.validation.NotaValidator;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public final class ServiceTest {
    ///region Test suites for executable com.ssvv.service.Service.deleteNota

    ///region FUZZER: SUCCESSFUL EXECUTIONS for method deleteNota(java.lang.String)

    /**
     * @utbot.classUnderTest {@link Service}
     * @utbot.methodUnderTest {@link Service#deleteNota(String)}
     */
    @Test
    @DisplayName("deleteNota: id = '-' -> return null")
    public void testDeleteNotaWithNonEmptyString() {
        StudentXMLRepo studentXMLRepo = new StudentXMLRepo("\n\t\r");
        StudentValidator studentValidator = new StudentValidator();
        TemaXMLRepo temaXMLRepo = new TemaXMLRepo("\n\t\r");
        TemaValidator temaValidator = new TemaValidator();
        NotaXMLRepo notaXMLRepo = new NotaXMLRepo("abc");
        StudentXMLRepo studentXMLRepo1 = new StudentXMLRepo("abc");
        TemaXMLRepo temaXMLRepo1 = new TemaXMLRepo("");
        NotaValidator notaValidator = new NotaValidator(studentXMLRepo1, temaXMLRepo1);
        Service service = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);

        Nota actual = service.deleteNota("-");

        assertNull(actual);
    }
    ///endregion

    ///endregion

    ///region Test suites for executable com.ssvv.service.Service.findNota

    ///region FUZZER: SUCCESSFUL EXECUTIONS for method findNota(java.lang.String)

    /**
     * @utbot.classUnderTest {@link Service}
     * @utbot.methodUnderTest {@link Service#findNota(String)}
     */
    @Test
    @DisplayName("findNota: id = '-' -> return null")
    public void testFindNotaWithNonEmptyString() {
        StudentXMLRepo studentXMLRepo = new StudentXMLRepo("\n\t\r");
        StudentValidator studentValidator = new StudentValidator();
        TemaXMLRepo temaXMLRepo = new TemaXMLRepo("\n\t\r");
        TemaValidator temaValidator = new TemaValidator();
        NotaXMLRepo notaXMLRepo = new NotaXMLRepo("abc");
        StudentXMLRepo studentXMLRepo1 = new StudentXMLRepo("abc");
        TemaXMLRepo temaXMLRepo1 = new TemaXMLRepo("");
        NotaValidator notaValidator = new NotaValidator(studentXMLRepo1, temaXMLRepo1);
        Service service = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);

        Nota actual = service.findNota("-");

        assertNull(actual);
    }
    ///endregion

    ///endregion
}

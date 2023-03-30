package com.example.demo.student;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository subject;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        subject.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        //given
        String email = "alex@gmail.com";
        Student student = Student.builder()
                .name("Alex")
                .email(email)
                .gender(Gender.MALE)
                .build();
        subject.save(student);
        //when
        Boolean expected = subject.selectExistsEmail(email);
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        //given
        String email = "alex@gmail.com";
        //when
        Boolean expected = subject.selectExistsEmail(email);
        //then
        assertThat(expected).isFalse();
    }
}
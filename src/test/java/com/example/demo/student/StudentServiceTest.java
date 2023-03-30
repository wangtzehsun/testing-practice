package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService subject;
    @Mock
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        subject = new StudentService(repository);
    }

    @Test
    void canGetAllStudents() {
        //when
        subject.getAllStudents();
        //then
        verify(repository).findAll();
    }

    @Test
    void canAddStudent() {
        //given
        Student student = Student.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .gender(Gender.MALE)
                .build();
        //when
        subject.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(repository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(student).isEqualTo(capturedStudent);
    }

    @Test
    void addStudentAndThrowException() {
        //given
        String email = "alex@gmail.com";
        Student student = Student.builder()
                .name("Alex")
                .email(email)
                .gender(Gender.MALE)
                .build();
        when(repository.selectExistsEmail(email)).thenReturn(true);
        //when
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> subject.addStudent(student));
        //then
        String expectedMessage = "Email alex@gmail.com taken";
        String actualMessage = badRequestException.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteStudent() {
    }
}
package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @Test
    void testDoctorEntity() {
        d1 = new Doctor("Adrian", "Avila", 33, "adrian.avila@example.com");
        entityManager.persistAndFlush(d1);

        Doctor retrievedDoctor = entityManager.find(Doctor.class, d1.getId());
        assertThat(retrievedDoctor).isNotNull();
        assertThat(retrievedDoctor.getFirstName()).isEqualTo("Adrian");
    }

    @Test
    void testPatientEntity() {
        p1 = new Patient("Alicia", "Perez", 30, "alicia.perez@example.com");
        entityManager.persistAndFlush(p1);

        Patient retrievedPatient = entityManager.find(Patient.class, p1.getId());
        assertThat(retrievedPatient).isNotNull();
        assertThat(retrievedPatient.getLastName()).isEqualTo("Perez");
    }

    @Test
    void testRoomEntity() {
        r1 = new Room("Dermatology");
        entityManager.persistAndFlush(r1);

        Room retrievedRoom = entityManager.find(Room.class, r1.getRoomName());
        assertThat(retrievedRoom).isNotNull();
        assertThat(retrievedRoom.getRoomName()).isEqualTo("Dermatology");
    }

    @Test
    void testAppointmentEntity() {
        d1 = new Doctor("Adrian", "Avila", 33, "adrian.avila@example.com");
        p1 = new Patient("Alicia", "Perez", 30, "alicia.perez@example.com");
        r1 = new Room("Dermatology");
        entityManager.persistAndFlush(d1);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(r1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt = LocalDateTime.parse("14:00 25/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("15:00 25/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        entityManager.persistAndFlush(a1);

        Appointment retrievedAppointment = entityManager.find(Appointment.class, a1.getId());
        assertThat(retrievedAppointment).isNotNull();
        assertThat(retrievedAppointment.getDoctor().getFirstName()).isEqualTo("Adrian");
        assertThat(retrievedAppointment.getPatient().getLastName()).isEqualTo("Perez");
    }

    @Test
    void testAppointmentEntity2() {
        Doctor doctor = new Doctor("Juan", "Garcia", 40, "juan.garcia@example.com");
        Patient patient = new Patient("Francisco", "Poveda", 45, "fran.poveda@example.com");
        Room room = new Room("Oncology");
        entityManager.persistAndFlush(doctor);
        entityManager.persistAndFlush(patient);
        entityManager.persistAndFlush(room);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt = LocalDateTime.parse("10:00 26/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("11:00 26/04/2023", formatter);

        a2 = new Appointment(patient, doctor, room, startsAt, finishesAt);
        entityManager.persistAndFlush(a2);

        Appointment retrievedAppointment = entityManager.find(Appointment.class, a2.getId());
        assertThat(retrievedAppointment).isNotNull();
        assertThat(retrievedAppointment.getDoctor().getFirstName()).isEqualTo("Juan");
        assertThat(retrievedAppointment.getPatient().getLastName()).isEqualTo("Poveda");
    }

    @Test
    void testAppointmentEntity3() {
        Doctor doctor = new Doctor("Maria", "Sanchez", 35, "maria.sanchez@example.com");
        Patient patient = new Patient("Luis", "Prada", 50, "luis.prada@example.com");
        Room room = new Room("Cardiology");
        entityManager.persistAndFlush(doctor);
        entityManager.persistAndFlush(patient);
        entityManager.persistAndFlush(room);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt = LocalDateTime.parse("08:00 27/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("09:00 27/04/2023", formatter);

        a3 = new Appointment(patient, doctor, room, startsAt, finishesAt);
        entityManager.persistAndFlush(a3);

        Appointment retrievedAppointment = entityManager.find(Appointment.class, a3.getId());
        assertThat(retrievedAppointment).isNotNull();
        assertThat(retrievedAppointment.getDoctor().getFirstName()).isEqualTo("Maria");
        assertThat(retrievedAppointment.getPatient().getLastName()).isEqualTo("Prada");
    }
    
}

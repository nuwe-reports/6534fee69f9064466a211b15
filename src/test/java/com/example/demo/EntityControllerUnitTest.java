package com.example.demo;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllDoctors() throws Exception {

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Adrian", "Avila", 33, "adrian.avila@example.com"));

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Adrian")));
    }

    @Test
    void testGetDoctorById() throws Exception {

        Doctor doctor = new Doctor("Adrian", "Avila", 33, "adrian.avila@example.com");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/api/doctors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Adrian")));
    }

    @Test
    void testGetDoctorByIdNotFound() throws Exception {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/doctors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPatients() throws Exception {

        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Alicia", "Perez", 30, "alicia.perez@example.com"));

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is("Perez")));
    }

    @Test
    void testGetPatientById() throws Exception {

        Patient patient = new Patient("Alicia", "Perez", 30, "alicia.perez@example.com");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Alicia")));
    }

    @Test
    void testGetPatientByIdNotFound() throws Exception {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllRooms() throws Exception {

        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Dermatology"));

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName", is("Dermatology")));
    }

    @Test
    void testGetRoomByName() throws Exception {

        Room room = new Room("Dermatology");

        when(roomRepository.findById("Dermatology")).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/rooms/Dermatology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName", is("Dermatology")));
    }

    @Test
    void testGetRoomByNameNotFound() throws Exception {
        when(roomRepository.findById("Dermatology")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rooms/Dermatology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

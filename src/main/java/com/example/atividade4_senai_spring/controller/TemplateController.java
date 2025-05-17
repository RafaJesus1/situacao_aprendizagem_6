package com.example.atividade4_senai_spring.controller;

import com.example.atividade4_senai_spring.model.Paciente;
import com.example.atividade4_senai_spring.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class TemplateController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/paciente/cadastro")
    public String mostrarFormulario() {
        return "cadastro-paciente";
    }

    @GetMapping("/paciente/home")
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        model.addAttribute("pacientes", pacientes);
        return "home";
    }

    @GetMapping("/paciente/editar/{id}")
    public String formularioEdicao(@PathVariable Long id, Model model){
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente inválido: " + id));
        model.addAttribute("paciente", paciente);
        return "editar-paciente";
    }

    @PostMapping("/paciente/editar/{id}")
    public String atualizarPaciente(@PathVariable Long id, @ModelAttribute Paciente pacienteAtualizado){
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setIdade(pacienteAtualizado.getIdade());
        pacienteExistente.setSexo(pacienteAtualizado.getSexo());
        pacienteExistente.setData_consulta(pacienteAtualizado.getData_consulta());

        pacienteRepository.save(pacienteExistente);

        return "redirect:/paciente/home";
    }

    @GetMapping("/paciente/deletar/{id}")
    public String deletarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        pacienteRepository.delete(paciente);
        return "redirect:/paciente/home";
    }
}
package com.example.atividade4_senai_spring.controller;

import java.util.List;
import java.util.Optional;

import com.example.atividade4_senai_spring.model.Paciente;
import com.example.atividade4_senai_spring.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> listar(){
        return pacienteRepository.findAll();
    }


    @GetMapping({"/{id}"})
    public ResponseEntity<Optional<Paciente>> procurar(@PathVariable Long id){
        return ResponseEntity.ok(pacienteRepository.findById(id));
    }

    @PostMapping
    public String adicionar(@ModelAttribute Paciente paciente){
        pacienteRepository.save(paciente);

        return "redirect:/paciente/home";
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deletar(@PathVariable Long id){
        pacienteRepository.deleteById(id);
        return ResponseEntity.ok("Paciente deletado com sucesso");
    }

    @PutMapping("/{id}")
    public Paciente atualizar(@PathVariable Long id, @RequestBody Paciente pacienteAtualizado){
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setSexo(pacienteAtualizado.getSexo());

        return pacienteRepository.save(pacienteExistente);

    }

}

package br.com.higo.apiescola.controller;

import br.com.higo.apiescola.dto.DisciplinaRequestDTO;
import br.com.higo.apiescola.dto.DisciplinaResponseDTO;
import br.com.higo.apiescola.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> salvar (@RequestBody DisciplinaRequestDTO request) {
        DisciplinaResponseDTO salvo = disciplinaService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<DisciplinaResponseDTO> listar() {
        return disciplinaService.listar();
    }

    @GetMapping("/{id}")
    public DisciplinaResponseDTO obterPorId(@PathVariable Long id) {
        return disciplinaService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public DisciplinaResponseDTO alterar(@PathVariable Long id, @RequestBody DisciplinaRequestDTO request) {
        return disciplinaService.alterar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        disciplinaService.excluir(id);
    }
}

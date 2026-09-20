package br.com.higo.apiescola.controller;

import br.com.higo.apiescola.dto.AlunoRequestDTO;
import br.com.higo.apiescola.dto.AlunoResponseDTO;
import br.com.higo.apiescola.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> salvar (@RequestBody AlunoRequestDTO request) {
        AlunoResponseDTO salvo = alunoService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listar() {
        List<AlunoResponseDTO> alunos = alunoService.listar();

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> obterPorId(@PathVariable Long id) {
        AlunoResponseDTO aluno = alunoService.obterPorId(id);

        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> alterar(@PathVariable Long id, @RequestBody AlunoRequestDTO request) {
        AlunoResponseDTO alterado = alunoService.alterar(id, request);

        return ResponseEntity.ok(alterado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        alunoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

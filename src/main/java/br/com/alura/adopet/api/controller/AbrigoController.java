package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDeCadastroAbrigoDto;
import br.com.alura.adopet.api.dto.DadosDeCadastroPetDto;
import br.com.alura.adopet.api.dto.DetalhesAbrigoDto;
import br.com.alura.adopet.api.dto.DetalhesPetDto;
import br.com.alura.adopet.api.excpetion.ValidacaoExcpetion;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<DetalhesAbrigoDto>> listar() {
        List<DetalhesAbrigoDto> abrigos = abrigoService.listar();
        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosDeCadastroAbrigoDto dto) {

        try {
            abrigoService.cadastrar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoExcpetion e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DetalhesPetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            List<DetalhesPetDto> pets = abrigoService.listarPetsDoAbrigo(idOuNome);
            return ResponseEntity.ok(pets);
        } catch (ValidacaoExcpetion e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid DadosDeCadastroPetDto dto) {
        try {
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            petService.cadastrarPet(abrigo, dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoExcpetion e) {
            return ResponseEntity.notFound().build();
        }
    }

}

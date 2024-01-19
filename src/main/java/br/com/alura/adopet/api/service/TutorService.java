package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDeCadastroTutorDto;
import br.com.alura.adopet.api.dto.DadosParaAtualizacaoTutorDto;
import br.com.alura.adopet.api.excpetion.ValidacaoExcpetion;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public void cadastrar(@RequestBody @Valid DadosDeCadastroTutorDto dto) {

        boolean jaCadastrado = tutorRepository.existsByEmailOrTelefone(dto.email(), dto.telefone());

        if (jaCadastrado) {
            throw new ValidacaoExcpetion("Esse tutor já está cadastrado!");
        }

        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(@RequestBody @Valid DadosParaAtualizacaoTutorDto dto) {
        Tutor novoTutor = tutorRepository.getReferenceById(dto.id());
        novoTutor.atualizarDados(dto);
    }

}

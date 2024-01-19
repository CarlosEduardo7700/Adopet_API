package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDeCadastroAbrigoDto;
import br.com.alura.adopet.api.dto.DetalhesAbrigoDto;
import br.com.alura.adopet.api.dto.DetalhesPetDto;
import br.com.alura.adopet.api.excpetion.ValidacaoExcpetion;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private PetRepository petRepository;

    public List<DetalhesAbrigoDto> listar() {
        return abrigoRepository
                .findAll()
                .stream()
                .map(DetalhesAbrigoDto::new)
                .toList();
    }

    public void cadastrar(DadosDeCadastroAbrigoDto dto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new ValidacaoExcpetion("Dados já cadastrados para outro abrigo!");
        }

        abrigoRepository.save(new Abrigo(dto));
    }

    public List<DetalhesPetDto> listarPetsDoAbrigo(String idOuNome) {
        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository
                .findByAbrigo(abrigo)
                .stream()
                .map(DetalhesPetDto::new)
                .toList();
    }

    public Abrigo carregarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException e) {
            optional = abrigoRepository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidacaoExcpetion("Abrigo não encontrado."));
    }


}

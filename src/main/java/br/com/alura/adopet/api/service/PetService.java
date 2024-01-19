package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDeCadastroPetDto;
import br.com.alura.adopet.api.dto.DetalhesPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<DetalhesPetDto> buscarPorPetDisponivel() {
        return petRepository
                .findAllByAdotadoFalse()
                .stream()
                .map(DetalhesPetDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, DadosDeCadastroPetDto dto) {
        petRepository.save(new Pet(dto, abrigo));
    }

}

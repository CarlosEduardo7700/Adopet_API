package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DetalhesAbrigoDto(Long id, String nome, @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}") String telefone, @Email String email) {
    public DetalhesAbrigoDto(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }
}

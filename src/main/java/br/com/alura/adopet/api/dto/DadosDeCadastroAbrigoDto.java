package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DadosDeCadastroAbrigoDto(String nome, @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}") String telefone, @Email String email) {
    public DadosDeCadastroAbrigoDto(Abrigo abrigo) {
        this(abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }
}

package com.f3pro.helpdesk.repositories;

import com.f3pro.helpdesk.domain.Pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

Optional<Pessoa> findByCpf(String cpf);

Optional<Pessoa> findByEmail(String email);
}
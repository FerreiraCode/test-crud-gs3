package com.example.testcrudgs3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	List<Funcionario> findBycpfStartsWithIgnoreCase(String CPF);
}

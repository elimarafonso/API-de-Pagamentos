package com.elimarAfonso.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elimarAfonso.pagamento.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
}

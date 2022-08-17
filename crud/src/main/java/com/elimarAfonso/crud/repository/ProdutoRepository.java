package com.elimarAfonso.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elimarAfonso.crud.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}

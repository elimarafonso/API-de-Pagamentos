package com.elimarAfonso.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elimarAfonso.crud.dataVO.ProdutoVO;
import com.elimarAfonso.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {

	
	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	
	private ProdutoVO create(ProdutoVO produtoVO) {

		return null;
	}
	
	
	
	
}

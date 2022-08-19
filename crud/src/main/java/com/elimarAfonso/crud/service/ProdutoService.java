package com.elimarAfonso.crud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elimarAfonso.crud.dataVO.ProdutoVO;
import com.elimarAfonso.crud.entity.Produto;
import com.elimarAfonso.crud.exception.ResourceNotFoundException;
import com.elimarAfonso.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	/* CRIA UM NOVO PRODUTO */
	public ProdutoVO create(ProdutoVO produtoVO) {
		ProdutoVO produtoVORetorno = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
		return produtoVORetorno;
	}

	/* LISTA TODOS OS PRODUTOS, RECEBENDO UMA PAGINAÇÃO UMA PAGINAÇÃO */
	public Page<ProdutoVO> findAll(Pageable pageable) {
		var page = produtoRepository.findAll(pageable);
		return page.map(this::convertToProdutoVO);
	}

	/* MÉTODO USADO NO findAll PARA CONVERTER EM ProdutoVO */
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);
	}

	/* ENCONTRA UM PRODUTO PELO IDENTIFICADOR */
	public ProdutoVO findById(Long id) {
		/*
		 * Procura na base o produto com o ID passado no parametro, caso não encontrado
		 * lança a Exception criada
		 */
		var produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PRODUTO NÃO ENCONTRADO!"));
		return ProdutoVO.create(produto);
	}

	/* ATUALIZA UM PRODUTO NA BASE DE DADOS */
	public ProdutoVO update(ProdutoVO produtoVOAlterado) {

		produtoRepository.findById(produtoVOAlterado.getId())
				.orElseThrow(() -> new ResourceNotFoundException("PRODUTO NÃO ENCONTRADO! CARALHOOO"));
		/*
		 * antes de salvar converte para classe MODEL e depois converte novamente para
		 * classe VO para retornar
		 */
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVOAlterado)));
	}

	/* DELETA UM PRODUTO */
	public void delete(long id) {
		/*
		 * PRIMEIRO ELE PROCURA O PRODUTO COM O ID PASSADO, CASO NÃO ENCONTRADO RETORNA
		 * UMA EXCEPTION OUTRA MANEIRA DE SE FAZER A VERIFICAÇÃO VIDEO METODO "UPDATE"
		 */
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PRODUTO NÃO ENCONTRADO!"));

		produtoRepository.delete(entity);
	}

}

package com.elimarAfonso.pagamento.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elimarAfonso.pagamento.entity.ProdutoVenda;
import com.elimarAfonso.pagamento.entity.Venda;
import com.elimarAfonso.pagamento.exception.ResourceNotFoundException;
import com.elimarAfonso.pagamento.repository.ProdutoVendaRepository;
import com.elimarAfonso.pagamento.repository.VendaRepository;
import com.elimarAfonso.pagamento.vo.VendaVO;

@Service
public class VendaService {
	private final VendaRepository vendaRepository;
	private final ProdutoVendaRepository produtoVendaRepository;

	@Autowired
	public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository = produtoVendaRepository;
	}

	/* CRIA UMA NOVA VENDA */
	public VendaVO create(VendaVO vendaVO) {
		Venda venda = vendaRepository.save(Venda.create(vendaVO));

		List<ProdutoVenda> produtosSalvos = new ArrayList<>();
		vendaVO.getProdutos().forEach(p -> {
			ProdutoVenda pv = ProdutoVenda.create(p);
			pv.setVenda(venda);
			produtosSalvos.add(produtoVendaRepository.save(pv));
		});

		venda.setProdutos(produtosSalvos);
		return VendaVO.create(venda);
	}

	/* LISTA TODAS VENDAS, RECEBENDO UMA PAGINAÇÃO UMA PAGINAÇÃO */
	public Page<VendaVO> findAll(Pageable pageable) {
		var page = vendaRepository.findAll(pageable);
		return page.map(this::convertToVendaVO);
	}

	/* MÉTODO USADO NO findAll PARA CONVERTER EM VendaVO */
	private VendaVO convertToVendaVO(Venda venda) {
		return VendaVO.create(venda);
	}

	/* ENCONTRA UMA VENDA PELO IDENTIFICADOR */
	public VendaVO findById(Long id) {
		var venda = vendaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("VENDA NÃO ENCONTRADA!"));
		return VendaVO.create(venda);
	}
}

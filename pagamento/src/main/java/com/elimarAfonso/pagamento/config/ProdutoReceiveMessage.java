package com.elimarAfonso.pagamento.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.elimarAfonso.pagamento.entity.Produto;
import com.elimarAfonso.pagamento.repository.ProdutoRepository;
import com.elimarAfonso.pagamento.vo.ProdutoVO;

@Component
public class ProdutoReceiveMessage {

	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	/*
	 * anotação para escutar a fila do rabbitmq
	 * application.yml
	 */
	@RabbitListener(queues = {"${crud.rabbitmq.queue}"})
	public void receive(@Payload ProdutoVO produtoVO) {
		produtoRepository.save(Produto.create(produtoVO));
	}

}

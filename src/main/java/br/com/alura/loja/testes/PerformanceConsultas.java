package br.com.alura.loja.testes;

import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PerformanceConsultas {

    public static void main(String[] args) {
        CadastroDeProduto.cadastrarProdutos();
        CadastroDePedido.cadastrarPedidos();

        EntityManager em = JPAUtil.getEntityManager();
        PedidoDao pedidoDao = new PedidoDao(em);
        Pedido pedido = pedidoDao.buscarPedidoComCliente(1L);

        ProdutoDao produtoDao = new ProdutoDao(em);
        List<Produto> produtosList = produtoDao.buscarPorParametros("Xiaomi Redmi", new BigDecimal(800), LocalDate.now());
        produtosList.forEach(System.out::println);

        em.close();
        System.out.println("Cliente do pedido: " + pedido.getCliente().getNome());
    }
}

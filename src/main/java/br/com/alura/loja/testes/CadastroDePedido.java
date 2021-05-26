package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        CadastroDeProduto.cadastrarProdutos();
        cadastrarPedidos();
    }

    public static void cadastrarPedidos() {
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        Produto celular = produtoDao.buscarPorId(1L);
        Produto videogame = produtoDao.buscarPorId(2L);
        Produto macbook = produtoDao.buscarPorId(3L);

        Cliente cliente = new Cliente("Rafael", "123456");
        Pedido pedido_1 = new Pedido(cliente);
        pedido_1.adicionarItem(new ItemPedido(3, pedido_1, celular));
        pedido_1.adicionarItem(new ItemPedido(2, pedido_1, videogame));

        Pedido pedido_2 = new Pedido(cliente);
        pedido_2.adicionarItem(new ItemPedido(1, pedido_2, macbook));
        pedido_2.adicionarItem(new ItemPedido(1, pedido_2, celular));

        PedidoDao pedidoDao = new PedidoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();
        clienteDao.cadastrar(cliente);
        pedidoDao.cadastrar(pedido_1);
        pedidoDao.cadastrar(pedido_2);
        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("Valor total vendido: " + totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();

        relatorio.forEach(System.out::println);

        em.close();
    }
}

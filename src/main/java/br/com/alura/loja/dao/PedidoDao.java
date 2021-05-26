package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public Pedido buscarPorId(Long Id) {
        return this.em.find(Pedido.class, Id);
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return this.em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo(\n"
                + "prod.nome,\n"
                + "SUM(item.quantidade),\n"
                + "MAX(p.data))\n"
                + "FROM Pedido p\n"
                + "JOIN p.itens item\n"
                + "JOIN item.produto prod\n"
                + "GROUP BY prod.nome\n"
                + "ORDER BY SUM(item.quantidade) DESC\n";
        return this.em.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id";
        return this.em.createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}

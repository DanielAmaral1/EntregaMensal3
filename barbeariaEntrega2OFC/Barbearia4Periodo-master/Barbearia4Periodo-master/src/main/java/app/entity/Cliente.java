package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;




@Entity
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    private String nome;
    private String celular;
    private String email;
    private Integer idade;



    public Cliente() {}

    public Cliente(String nome, String celular, String email, Integer idade) {
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.idade = idade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return java.util.Objects.equals(id_cliente, cliente.id_cliente);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_cliente);
    }
}

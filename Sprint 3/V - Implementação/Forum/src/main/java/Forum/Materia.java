package Forum;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
public class Materia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cargo> Cargos;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> Perguntas;

    public List<Cargo> getCargos() {
        return Cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        Cargos = cargos;
    }

    public List<Pergunta> getPerguntas() {
        return Perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        Perguntas = perguntas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

package Forum;

import Forum.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Integer>{
    Pergunta findById(int id);
    List<Pergunta> findByContaId(int idConta);
    List<Pergunta> findByMateriaId(int idMateria);

    List<Pergunta> findByStatus(int status);
}

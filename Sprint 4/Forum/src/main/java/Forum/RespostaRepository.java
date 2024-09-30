package Forum;

import Forum.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    List<Resposta> findByPerguntaId(int idPergunta);
    List<Resposta> findByContaId(int idConta);
}

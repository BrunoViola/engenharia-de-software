package Forum;

import Forum.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContaRepository extends CrudRepository<Conta, Integer> {

    Optional<Conta> findByEmail(String email);
}

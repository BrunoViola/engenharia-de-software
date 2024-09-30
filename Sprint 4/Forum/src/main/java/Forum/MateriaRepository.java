package Forum;

import Forum.Materia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MateriaRepository extends CrudRepository<Materia, Integer> {

    List<Materia> findAll();
}

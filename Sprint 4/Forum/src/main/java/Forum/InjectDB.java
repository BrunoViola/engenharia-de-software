package Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectDB {

    @Autowired
    private ContaRepository contaRepository;

    public void injectDB(){
        Conta contaAdmin = new Conta();
        contaAdmin.setNome("Admin");
        contaAdmin.setEmail("admin@uel.br");
        contaAdmin.setSenha(SenhaUtil.encoder("admin"));

        contaRepository.save(contaAdmin);
    }
}

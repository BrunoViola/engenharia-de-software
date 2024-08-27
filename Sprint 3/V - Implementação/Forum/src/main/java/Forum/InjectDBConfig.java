package Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectDBConfig {

    @Autowired
    private InjectDB injectDB;

    @Autowired
    private ContaRepository contaRepository;

    @Bean
    public Boolean injectDBInfo(){
        if(contaRepository.findByEmail("admin@uel.br").isEmpty()){
            this.injectDB.injectDB();
            return true;
        }
        return false;
    }
}

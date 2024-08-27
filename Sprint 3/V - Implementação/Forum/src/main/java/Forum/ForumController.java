package Forum;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    private UserService userService;

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @GetMapping("/nova-conta")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("conta", new Conta());
        return "nova-conta";
    }

    /*@GetMapping("/contas")
    public String mostrarListaContas(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "contas";
    }*/

    @PostMapping("/adicionar-conta")
    public String adicionarConta(@Valid Conta conta, BindingResult result) {
        if (result.hasErrors()) {
            return "nova-conta";
        }
        if(contaRepository.findByEmail(conta.getEmail()).isEmpty()){
            String hashSenha = SenhaUtil.encoder(conta.getSenha());
            conta.setSenha(hashSenha);

            contaRepository.save(conta);
            return "redirect:/login";
        }else {
            return "nova-conta";
        }

    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("errorMsg", "Usuário ou senha inválido(s).");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Logout da conta realizado com sucesso.");
        }
        return "login";
    }

    @GetMapping(value = {"/mostrar-perguntas", "/"})
    public String mostrarPerguntas(Model model){
        List<Pergunta> perguntas = perguntaRepository.findByStatus(0);
        model.addAttribute("perguntas", perguntas);
        return "mostrar-perguntas";
    }

    @PostMapping("/nova-pergunta")
    public String mostrarPaginaNovaPergunta(Model model) {
        List<Materia> materias = materiaRepository.findAll();
        model.addAttribute("materias", materias);
        model.addAttribute("pergunta", new Pergunta());
        return "nova-pergunta";
    }

    @PostMapping("/salvar-pergunta")
    public String salvarPergunta(@ModelAttribute Pergunta pergunta) {
        Conta conta = userService.getLoggedInUser();
        if (conta != null) {
            pergunta.setConta(conta);
            /*if(pergunta.getAnonimo()!=1){
                pergunta.setAnonimo(0);
            }*/
            perguntaRepository.save(pergunta);
        }
        return "redirect:/mostrar-perguntas";
    }

    @GetMapping("/mostrar-suas-perguntas")
    public String mostrarPaginaSuasPerguntas(){
        return "mostrar-suas-perguntas";
    }
}

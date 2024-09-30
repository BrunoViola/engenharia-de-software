package Forum;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
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

    /*@GetMapping("/contas")
    public String mostrarListaContas(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "contas";
    }*/

    //CASO DE USO: CRIAR CONTA
    @GetMapping("/nova-conta")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("conta", new Conta());
        return "nova-conta";
    }

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

    //CASO DE USO: LOGIN
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

    //CASO DE USO: MOSTRAR PERGUNTAS
    @GetMapping(value = {"/mostrar-perguntas", "/"})
    public String mostrarPerguntas(Model model){
        List<Pergunta> perguntas = perguntaRepository.findByStatus(0);
        Collections.reverse(perguntas);
        model.addAttribute("perguntas", perguntas);
        return "mostrar-perguntas";
    }

    //CASO DE USO: CRIAR PERGUNTA
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

    //CASO DE USO: MOSTRAR SUAS PERGUNTAS
    @GetMapping("/mostrar-suas-perguntas")
    public String mostrarPaginaSuasPerguntas(Model model){
        Conta conta = userService.getLoggedInUser();
        List<Pergunta> perguntas = perguntaRepository.findByContaId(conta.getId());
        Collections.reverse(perguntas);
        model.addAttribute("perguntas", perguntas);
        return "mostrar-suas-perguntas";
    }

    //CASO DE USO: RESPONDER PERGUNTA
    @GetMapping("/responder-pergunta/{id}")
    public String mostrarFormularioResposta(@PathVariable("id") int idPergunta, Model model){
        //Conta conta = userService.getLoggedInUser();
        Pergunta pergunta = perguntaRepository.findById(idPergunta);
        List <Resposta> respostas = respostaRepository.findByPerguntaId(idPergunta);
        Collections.reverse(respostas);
        Resposta novaResposta = new Resposta();
        /*novaResposta.setConta(conta);
        novaResposta.setPergunta(pergunta);*/
        model.addAttribute("pergunta", pergunta);
        model.addAttribute("respostas", respostas);
        model.addAttribute("novaResposta", novaResposta);
        return "nova-resposta";
    }

    @PostMapping("/salvar-resposta/{id}")
    public String salvarResposta(@PathVariable("id") int idPergunta, @ModelAttribute Resposta novaResposta, Model model){
        Conta conta = userService.getLoggedInUser();
        Pergunta pergunta = perguntaRepository.findById(idPergunta);
        novaResposta.setConta(conta);
        novaResposta.setPergunta(pergunta);
        respostaRepository.save(novaResposta);
        return "redirect:/responder-pergunta/"+idPergunta;
    }
}

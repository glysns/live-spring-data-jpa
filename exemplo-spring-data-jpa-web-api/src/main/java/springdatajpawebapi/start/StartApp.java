package springdatajpawebapi.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdatajpawebapi.model.Cliente;
import springdatajpawebapi.model.Profissao;
import springdatajpawebapi.model.cliente.Endereco;
import springdatajpawebapi.model.cliente.Sexo;
import springdatajpawebapi.model.cliente.Telefone;
import springdatajpawebapi.model.cliente.TelefoneTipo;
import springdatajpawebapi.repository.ClienteRepository;
import springdatajpawebapi.repository.ProfissaoRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private ProfissaoRepository profissaoCrud;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
       /* Profissao profissao = incluirProfissao();
        incluirCliente1(profissao);
        incluirCliente2(profissao);
        listarClientes();
        buscarClienteCompleto(1);
        buscarClienteCompleto(2);*/
        alterarCliente();
    }
    private  void alterarCliente(){
        Cliente cliente  = clienteRepository.findById(2).orElse(null);
        if(cliente!=null){
            cliente.setNome("izabelly noronha");
            clienteRepository.save(cliente);
            System.out.println("alterando um cliente");
        }else {
            System.out.println("Não foi possivel alterar o cliente");
        }
    }
    //service
    private void listarClientes(){
        System.out.println("LISTANDO OS CLIENTES COM LAZY HABILITADO: ");
        for(Cliente cli: clienteRepository.findAll()){
            System.out.println(cli.getNome());
        }
    }
    private void buscarClienteCompleto(Integer id){
        System.out.println("BUSCANDO O CLIENTE COMPLETO COM ID: " + id);
        System.out.println("ESTA OPERAÇÃO FORÇA A EXECUÇÃO DE CONSULTAS EM OUTRAS TABELAS");
        Cliente cliente  = clienteRepository.getFull(id);
        if(cliente!=null){
            System.out.println(cliente.getNome());
            if(cliente.getProfissao()!=null)
                System.out.println("profissao " + cliente.getProfissao().getNome() );
            if(cliente.getTelefones()!=null) {
                for(Telefone t: cliente.getTelefones()){
                    System.out.println("telefone " + t.getNumero());
                }

            }
        }
    }
    private void incluirCliente1(Profissao profissao){
        if(!clienteRepository.existsById(1)){
            Cliente cliente = new Cliente();
            cliente.setNome("gleyson");
            cliente.setSexo(Sexo.M);
            cliente.setDataNascimento(LocalDate.now());
            cliente.setProfissao(profissao);
            cliente.setEmails(Collections.singleton("gleyson@hotmail.com"));
            cliente.setTelefones(Collections.singleton(new Telefone(TelefoneTipo.COM,11965479876L)));
            Endereco endereco = new Endereco();
            endereco.setCep("45763567");
            endereco.setLogradouro("RUA DAS FLORES");
            endereco.setNumero("45A");
            cliente.setEndereco(endereco);
            clienteRepository.save(cliente);
            System.out.println("primeiro cliente adicionado");
        }else
            System.out.println("Já existe um cliente com o id 1");


    }
    private void incluirCliente2(Profissao profissao){
        if(!clienteRepository.existsById(2)){
            Cliente cliente = new Cliente();
            cliente.setNome("izabelly");
            cliente.setSexo(Sexo.F);
            cliente.setDataNascimento(LocalDate.now());
            cliente.setProfissao(profissao);
            cliente.setEmails(Collections.singleton("izabelly@gmail.com"));
            cliente.setTelefones(Collections.singleton(new Telefone(TelefoneTipo.RES,9823870934L)));
            Endereco endereco = new Endereco();
            endereco.setCep("87685586");
            endereco.setLogradouro("AVENIDA MARTE");
            endereco.setNumero("SN");
            cliente.setEndereco(endereco);
            clienteRepository.save(cliente);
            System.out.println("segundo cliente adicionado");
        }else
            System.out.println("Já existe um cliente com o id 2");

    }
    private  Profissao incluirProfissao(){
        Profissao profissao  = profissaoCrud.findById(1).orElse(null);
        if(profissao==null) {
            profissao = new Profissao();
            profissao.setNome("PROGRAMADOR(a)");
            profissaoCrud.save(profissao);
            System.out.println("profissao adicionada com sucesso");
        }
        return profissao;
    }
    private  void alterarProfissao(){
        Profissao profissao  = profissaoCrud.findById(1).orElse(null);
        if(profissao!=null){
            profissao.setNome("PROGRAMADOR(a) / INSTRUTOR(a)");
            profissaoCrud.save(profissao);
            System.out.println("profissao adicionado com sucesso");
        }else {
            System.out.println("Não existe a profissão com o id informado");
        }
    }
    private  void listarProfissoes(){
        List<Profissao> profissoes = profissaoCrud.findAll();
        for(Profissao p:profissoes){
            System.out.println(p.getId() + "--" + p.getNome());
        }
    }
    private  void excluirProfissao(){
       profissaoCrud.deleteById(1);
        System.out.println("profissao excluida com sucesso");
    }
}
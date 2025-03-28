package Atividade_01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Conta> contas = new ArrayList<>();
        Banco banco = new Banco();

        System.out.println("Bem-vindo ao Sistema Bancário!");

        System.out.print("Digite o nome do banco: ");
        banco.setNome(scanner.nextLine());

        boolean continuar = true;
        while (continuar) {
            Cliente cliente = cadastrarCliente(scanner);
            Conta contaCorrente = criarContaCorrente(scanner, cliente);
            Conta contaPoupanca = criarContaPoupanca(scanner, cliente);

            operacoesConta(scanner, contaCorrente, contaPoupanca);

            contas.add(contaCorrente);
            contas.add(contaPoupanca);

            System.out.print("Deseja cadastrar outro cliente? (S/N): ");
            String sair = scanner.nextLine();
            if(sair.equalsIgnoreCase("S")){
                continuar = false;
            }
        }

        banco.setContas(contas);

        exibirResumo(banco);

        scanner.close();
    }

    private static Cliente cadastrarCliente(Scanner scanner) {
        System.out.println("\n--- Cadastro de Cliente ---");
        Cliente cliente = new Cliente();
        System.out.print("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        return cliente;
    }

    private static Conta criarContaCorrente(Scanner scanner, Cliente cliente) {
        System.out.println("\n--- Conta Corrente ---");
        Conta cc = new ContaCorrente(cliente);
        System.out.print("Digite o valor do depósito inicial: ");
        double depositoInicial = Double.parseDouble(scanner.nextLine());
        cc.depositar(depositoInicial);
        return cc;
    }

    private static Conta criarContaPoupanca(Scanner scanner, Cliente cliente) {
        System.out.println("\n--- Conta Poupança ---");
        Conta poupanca = new ContaPoupanca(cliente);
        System.out.print("Digite o valor do depósito inicial: ");
        double depositoInicial = Double.parseDouble(scanner.nextLine());
        poupanca.depositar(depositoInicial);
        return poupanca;
    }

    private static void operacoesConta(Scanner scanner, Conta cc, Conta poupanca) {
        boolean operando = true;
        while (operando) {
            System.out.println("\n--- Operações Bancárias ---");
            System.out.println("1 - Depositar na Conta Corrente");
            System.out.println("2 - Sacar da Conta Corrente");
            System.out.println("3 - Transferir para Poupança");
            System.out.println("4 - Ver Extratos");
            System.out.println("5 - Finalizar Operações");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Valor para depósito na CC: ");
                    double valorDeposito = Double.parseDouble(scanner.nextLine());
                    cc.depositar(valorDeposito);
                    break;
                case 2:
                    System.out.print("Valor para saque da CC: ");
                    double valorSaque = Double.parseDouble(scanner.nextLine());
                    if(cc.saldo > 0 && cc.saldo > valorSaque){
                        cc.sacar(valorSaque);
                    }else{
                        System.out.println("Saldo insuficiente");
                    }
                    
                    break;
                case 3:
                    System.out.print("Valor para transferência: ");
                    double valorTransferencia = Double.parseDouble(scanner.nextLine());
                    cc.transferir(valorTransferencia, poupanca);
                    break;
                case 4:
                    System.out.println("\n--- Extratos ---");
                    cc.imprimirExtrato();
                    System.out.println();
                    poupanca.imprimirExtrato();
                    break;
                case 5:
                    operando = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void exibirResumo(Banco banco) {
        System.out.println("\n=== RESUMO FINAL ===");
        System.out.println("Banco: " + banco.getNome());
        System.out.println("Total de contas: " + banco.getContas().size());

        System.out.println("\n--- Detalhes das Contas ---");
        for (Conta conta : banco.getContas()) {
            conta.imprimirExtrato();
            System.out.println();
        }
    }
}

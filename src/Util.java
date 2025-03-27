import java.text.DecimalFormat;

import static javax.swing.JOptionPane.*;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Double.parseDouble;

public class Util {
    private BilheteUnico[] bilhetes = new BilheteUnico[3];
    private int index = 0;

    public void menuPrincipal() {
        int opcao = 1;
        String menu = """
                1 - Administrador
                2 - Usuário
                3 - Finalizar
                """;
        do {
            try {
                opcao = parseInt(showInputDialog(menu));
                switch (opcao) {
                    case 1:
                        menuAdmin();
                        break;
                    case 2:
                        menuUsuario();
                        break;
                    case 3:
                        showMessageDialog(null, "Encerrando o programa");
                        break;
                    default:
                        showMessageDialog(null, "Opção inválida!");
                        break;
                }
            } catch (NumberFormatException e) {
            }
        } while (opcao != 3);
    }

    public void menuAdmin() {
        int opcao = 1;
        String menuAdmin = """
                1 - Adicionar bilhete
                2 - Remover bilhete
                3 - Listar bilhetes registrados
                4 - Sair
                """;
        do {
            try {
                opcao = parseInt(showInputDialog(menuAdmin));
                switch (opcao) {
                    case 1:
                        adicionarBilhete();
                        break;
                    case 2:
                        removerBilhete();
                        break;
                    case 3:
                        listarBilhetes();
                        break;
                    case 4:
                        break;
                    default:
                        showMessageDialog(null, "Opção inválida!");
                        break;
                }
            } catch (NumberFormatException n) {}
        } while (opcao != 4);
    }

    private void menuUsuario() {
        int opcao = 1;
        String menu = """
                1 - Consultar saldo
                2 - Carregar Bilhete
                3 - Passar na catraca
                4 - Sair
                """;
        do {
            try {
                opcao = parseInt(showInputDialog(menu));
                switch (opcao) {
                    case 1:
                        consultarSaldo();
                        break;
                    case 2:
                        carregarBilhete();
                        break;
                    case 3:
                        passarCatraca();
                        break;
                    case 4:
                        break;
                    default:
                        showMessageDialog(null, "Opção inválida!");
                        break;
                }
            } catch (NumberFormatException e) {}
        } while (opcao != 4);
    }

    private void passarCatraca() {
        int posicao = pesquisar();
        if (posicao != -1) {
            bilhetes[posicao].passarNaCatraca();
        }
    }

    private void carregarBilhete() {
        int posicao = pesquisar();
        double valor;
        if (posicao != -1) {
            valor = parseDouble(showInputDialog("Digite o valor da recarga: "));
            bilhetes[posicao].carregar(valor);
        }
    }

    private void consultarSaldo() {
        int posicao = pesquisar();
        if (posicao != -1) {
            showMessageDialog(null, "Saldo: R$" + bilhetes[posicao].consultarSaldo());
        }
    }

    private int pesquisar() {
        long cpf = parseLong(showInputDialog("CPF: "));
        for (int i = 0; i < index; i++) {
            if (bilhetes[i].usuario.cpf == cpf) {
                return i;
            }
        }
        showMessageDialog(null, "CPF: " + cpf + " não encontrado!");
        return -1;
    }

    private void listarBilhetes() {
        DecimalFormat df = new DecimalFormat("0.00");
        String aux = "";
        for (int i = 0; i < index; i++) {
            aux += "Número do bilhete: " + bilhetes[i].numero + "\n";
            aux += "Nome do usuário: " + bilhetes[i].usuario.nome + "\n";
            aux += "Saldo: " + df.format(bilhetes[i].saldo) + "\n";
            aux += "Tipo de tarifa: " + bilhetes[i].usuario.tipoTarifa + "\n";
            aux += "----------------------------------------------\n";
        }
        showMessageDialog(null, aux);
    }

    private void removerBilhete() {
        int posicao = pesquisar();
        int resposta;
        if (posicao != -1) {
            resposta = showConfirmDialog(null, "Quer remover este usuário?");
            if (resposta == YES_OPTION) {
                bilhetes[posicao] = bilhetes[index - 1];
                index--;
            }
        }
    }

    private void adicionarBilhete() {
        if (index < bilhetes.length) {
            String nome = showInputDialog("Digite o nome do usuário");
            long cpf = Long.parseLong(showInputDialog("Digite o CPF do usuário"));
            String tipoTarifa = showInputDialog("Digite o tipo de usuário");
            Usuario usuario = new Usuario(nome, cpf, tipoTarifa);
            bilhetes[index] = new BilheteUnico(usuario);
            index++;
            showMessageDialog(null, "Usuário cadastrado");
        } else {
            showMessageDialog(null, "Limite de bilhetes excedido");
        }
    }
}

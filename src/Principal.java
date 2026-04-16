import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        System.out.println("-> 3.3: Lista de Funcionários:");
        for (Funcionario f : funcionarios) {
            imprimirFuncionario(f);
        }

        for (Funcionario f : funcionarios) {
            BigDecimal aumento = f.getSalario().multiply(PERCENTUAL_AUMENTO);
            f.setSalario(f.getSalario().add(aumento));
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n-> 3.6: Funcionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                System.out.println("   - " + f.getNome());
            }
        }

        System.out.println("\n-> 3.8: Aniversariantes do Mês 10 e 12:");
        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.println(f.getNome() + " (" + f.getDataNascimento().format(FORMATADOR_DATA) + ")");
            }
        }

        System.out.println("\n-> 3.9: Funcionário com Maior Idade:");
        Funcionario maisVelho = funcionarios.get(0);
        for (Funcionario f : funcionarios) {
            if (f.getDataNascimento().isBefore(maisVelho.getDataNascimento())) {
                maisVelho = f;
            }
        }
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");

        System.out.println("\n-> 3.10: Funcionários em Ordem Alfabética:");
        List<Funcionario> ordemAlfabetica = new ArrayList<>(funcionarios);
        ordemAlfabetica.sort(Comparator.comparing(Funcionario::getNome));
        for (Funcionario f : ordemAlfabetica) {
            System.out.println(f.getNome());
        }

        System.out.println("\n-> 3.11: Total dos Salários:");
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario f : funcionarios) {
            totalSalarios = totalSalarios.add(f.getSalario());
        }
        System.out.println("Total: " + formatarValorBR(totalSalarios));

        System.out.println("\n-> 3.12: Salários Mínimos por Funcionário:");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalarios = f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha: " + qtdSalarios + " salários mínimos.");
        }
    }

    private static String formatarValorBR(BigDecimal valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat formatador = new DecimalFormat("#,##0.00", symbols);
        return formatador.format(valor);
    }

    private static void imprimirFuncionario(Funcionario f) {
        System.out.println("Nome: " + f.getNome() +
                " | Data Nasc: " + f.getDataNascimento().format(FORMATADOR_DATA) +
                " | Salário: " + formatarValorBR(f.getSalario()) +
                " | Função: " + f.getFuncao());
    }
}
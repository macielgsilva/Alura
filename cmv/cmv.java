import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class cmv {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione a conversão desejada:");
            System.out.println("1 - Conversão de Dólar para Real");
            System.out.println("2 - Conversão de Real para Dólar");
            System.out.println("3 - Conversão de Real para Euro");
            System.out.println("4 - Conversão de Euro para Real");
            System.out.println("5 - Conversão de Libra para Real");
            System.out.println("6 - Conversão de Real para Libra");
            System.out.println("7 - Deseja sair do programa");

            int option = 0;
            boolean validOption = false;

            while (!validOption) {
                System.out.print("Escolha uma opção (1-7): ");
                if (scanner.hasNextInt()) {
                    option = scanner.nextInt();
                    if (option >= 1 && option <= 7) {
                        validOption = true;
                    } else {
                        System.out.println("Opção Inválida, informe somente as opções da tela entre 1 a 7");
                    }
                } else {
                    System.out.println("Opção Inválida, informe somente as opções da tela entre 1 a 7");
                    scanner.next(); // Limpa a entrada inválida
                }
            }

            String fromCurrency = "";
            String toCurrency = "";

            switch (option) {
                case 1:
                    fromCurrency = "USD";
                    toCurrency = "BRL";
                    break;
                case 2:
                    fromCurrency = "BRL";
                    toCurrency = "USD";
                    break;
                case 3:
                    fromCurrency = "BRL";
                    toCurrency = "EUR";
                    break;
                case 4:
                    fromCurrency = "EUR";
                    toCurrency = "BRL";
                    break;
                case 5:
                    fromCurrency = "GBP";
                    toCurrency = "BRL";
                    break;
                case 6:
                    fromCurrency = "BRL";
                    toCurrency = "GBP";
                    break;
                case 7:
                    System.out.print("Tem certeza que deseja sair do programa? (s/n): ");
                    String exitResponse = scanner.next();
                    if (exitResponse.equalsIgnoreCase("s")) {
                        System.out.println("Saindo do programa...");
                        scanner.close();
                        System.exit(0);
                    } else {
                        continue;
                    }
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    continue;
            }

            double value = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Digite o valor a ser convertido: ");
                if (scanner.hasNextDouble()) {
                    value = scanner.nextDouble();
                    validInput = true;
                } else {
                    System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
                    scanner.next(); // Limpa a entrada inválida
                }
            }

            try {
                double result = convertCurrency(fromCurrency, toCurrency, value);
                System.out.printf("Resultado: %.2f %s%n", result, toCurrency);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Erro ao realizar a conversão: " + ex.getMessage());
            }

            // Pergunta ao usuário se deseja realizar outra conversão
            System.out.print("Deseja realizar outra conversão? (s/n): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("s")) {
                break;
            }
        }

        scanner.close();
    }
/////////// Consultando a API da ExchangeRate-API \\\\\\\\\\
    private static double convertCurrency(String from, String to, double amount) throws Exception {
        String apiKey = "INFORMAR A API-KEY"; // Nesse local deve ser substituido o valor pela chave de API da ExchangeRate-API
        String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        double exchangeRate = jsonResponse.getDouble("conversion_rate");

        return amount * exchangeRate;
    }
}

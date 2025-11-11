import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим:");
        System.out.println("1) Рассчитать итоговую сумму по сложному проценту");
        System.out.println("2) Найти процент за период, чтобы дойти от Числа1 до Числа2 за N периодов");
        System.out.print("Ваш выбор (1/2): ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                runForwardCalc(scanner);
                break;
            case "2":
                runInverseCalc(scanner);
                break;
            default:
                System.out.println("Неизвестный выбор. Запустите программу снова и выберите 1 или 2.");
        }
    }

    private static void runForwardCalc(Scanner scanner) {
        double principal = readDouble(scanner, "Введите начальную сумму: ");
        double ratePercent = readDouble(scanner, "Введите процент за период (в %): ");
        int periods = readInt(scanner, "Введите количество периодов (целое >= 0): ", 0);

        double factor = Math.pow(1.0 + ratePercent / 100.0, periods);
        double amount = principal * factor;

        System.out.printf(Locale.US, "Итоговая сумма: %.2f%n", amount);
    }

    private static void runInverseCalc(Scanner scanner) {
        double amountStart = readDouble(scanner, "Введите начальную сумму (Число1): ");
        double amountEnd = readDouble(scanner, "Введите конечную сумму (Число2): ");
        int periods = readInt(scanner, "Введите количество периодов (целое > 0): ", 1);

        if (amountStart <= 0.0) {
            System.out.println("Начальная сумма должна быть больше 0.");
            return;
        }
        if (amountEnd <= 0.0) {
            System.out.println("Конечная сумма должна быть больше 0.");
            return;
        }

        double ratio = amountEnd / amountStart;
        double perPeriodFactor = Math.pow(ratio, 1.0 / periods);
        double ratePercent = (perPeriodFactor - 1.0) * 100.0;

        System.out.printf(Locale.US, "Необходимый процент за период: %.6f%%%n", ratePercent);
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String token = scanner.nextLine().trim();
            if (token.isEmpty()) {
                System.out.println("Пустой ввод. Повторите попытку.");
                continue;
            }
            token = token.replace(',', '.');
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException ex) {
                System.out.println("Некорректное число. Пример: 1234.56");
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt, int minInclusive) {
        while (true) {
            System.out.print(prompt);
            String token = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(token);
                if (v < minInclusive) {
                    System.out.println("Число должно быть не меньше " + minInclusive + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Некорректное целое число. Пример: 12");
            }
        }
    }
}

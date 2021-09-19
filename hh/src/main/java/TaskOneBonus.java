import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskOneBonus {

    /**
     * Условие задачи
     *
     * Ограничение времени, с	1
     * Ограничение памяти, МБ	64
     * Общее число попыток отправки	15
     *
     * Петр Васильевич, директор ОАО "Рога и рога", собирается раздать премию всем менеджерам компании, он добрый и
     * честный человек, поэтому хочет соблюсти следующие условия:
     *
     * * премия должна быть равной для всех менеджеров
     * * должна быть максимально возможной и целой
     * * должна быть выдана одной транзакцией с одного счета для каждого менеджера, без использования нескольких счетов
     * * для отправки одной премии
     *
     * У Петра Васильевича открыто N корпоративных счетов, на которых лежат разные суммы денег Cn, а в компании
     * работает M менеджеров. Необходимо выяснить максимальный размер премии, которую можно отправить с учетом условий.
     * Если денег на счетах компании нехватит на то, чтобы выдать премию хотя бы по 1 у.е. - значит премии не будет,
     * и нужно вывести 0.
     *
     *
     * Входные данные (поступают в стандартный поток ввода)
     * Первая строка - целые числа N и M через пробел (1≤N≤100 000, 1≤M≤100 000)
     *
     * Далее N строк, на каждой из которых одно целое число Cn (0≤Cn≤100 000 000)
     *
     * Проверка входных данных и обработка неправильных данных на входе не нужна, тестовые данные для проверки
     * гарантированно подходят под описание выше
     *
     *
     * Выходные данные (ожидаются в стандартном потоке вывода)
     * Одно целое число, максимально возможная премия
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Пример 1
     * Ввод:
     *
     * 4 6
     * 199
     * 453
     * 220
     * 601
     * Вывод:
     *
     * 200
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Пример 2
     * Ввод:
     *
     * 2 100
     * 99
     * 1
     * Вывод:
     *
     * 1
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * *
     * Пример 3
     * Ввод:
     *
     * 2 100
     * 98
     * 1
     * Вывод:
     *
     * 0
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Примечания по оформлению решения
     * Возможно использование только стандартных библиотек языков, установки и использование дополнительных библиотек невозможны.
     *
     * При отправке решений на Java необходимо назвать исполняемый класс Main. В решении не нужно указывать пакет.
     */

    public static void main(String[] args) {
        Integer accountNumbers = 0;
        Integer managers = 0;
        List<Integer> listAccountValues = new ArrayList<>();

        Scanner reader = new Scanner(System.in);

        accountNumbers = Integer.parseInt(reader.nextLine());
        managers = Integer.parseInt(reader.nextLine());

        while (listAccountValues.size() < accountNumbers) {
            String input = reader.nextLine();
            listAccountValues.add(Integer.parseInt(input));
        }

        try {
            int maxPossibleBonus = getMaxPossibleBonus(listAccountValues, managers);
            getBonus(listAccountValues, maxPossibleBonus, managers);
        } catch (ArithmeticException e) {
            System.out.println(0);
        }
    }

    /**
     *
     * @param listAccountValues
     * @param managers
     * @return Максимально возможная премия
     */
    private static Integer getMaxPossibleBonus(List<Integer> listAccountValues, Integer managers){
        Integer sum = listAccountValues.stream().mapToInt(Integer::intValue).sum();
        return sum / managers;
    }

    /**
     *
     * @param listAccountValues
     * @param maxPossibleBonus
     * @return Количество транзакций со счетов при максимально возможной премии
     */
    private static Integer getTransactionsQty(List<Integer> listAccountValues, Integer maxPossibleBonus){
        return listAccountValues.stream().map(e -> (e / maxPossibleBonus)).mapToInt(Integer::intValue).sum();
    }

    private static void getBonus(List<Integer> listAccountValues, Integer maxPossibleBonus, Integer managers){
        for (int i = 0; i < managers; maxPossibleBonus--){
            Integer bonus = getTransactionsQty(listAccountValues, maxPossibleBonus);
            if (bonus.equals(managers)) {
                System.out.println(maxPossibleBonus);
                break;
            }
        }
    }
}

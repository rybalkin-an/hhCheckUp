import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {

    /**
     * Условие задачи
     *
     * Ограничение времени, с	1
     * Ограничение памяти, МБ
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
        Long accountNumbers = 100000L;
        Long managers = 100000L;
        List<Long> listAccountValues = new ArrayList<>();

        listAccountValues = getList(accountNumbers);


//        Scanner reader = new Scanner(System.in);
//
//        accountNumbers = reader.nextInt();
//        managers = reader.nextInt();
//
//        while (listAccountValues.size() < accountNumbers) {
//            listAccountValues.add(reader.nextInt());
//        }

        try {
            System.out.println(binSearch(listAccountValues, managers));
        } catch (ArithmeticException e) {
            System.out.println("Ebobo");
        }
    }

    /**
     *
     * @param listAccountValues
     * @param maxPossibleBonus
     * @return Количество транзакций со счетов при максимально возможной премии
     */
    private static Long getTransactionsQty(List<Long> listAccountValues, Long maxPossibleBonus){
        return listAccountValues.stream()
                .map(e -> (e / maxPossibleBonus))
                .mapToLong(Long::longValue)
                .sum();
    }

    private static Long getSumAccountValue(List<Long> listAccountValues){
        return listAccountValues.stream().reduce(0L, Long::sum);
    }

    private static Long binSearch(List<Long> listAccountValues, Long managers){
        long low = 2;
        Long high = getSumAccountValue(listAccountValues) / managers;
        while (low < high - 1) {
            Long mid = (low + high) / 2;
            Long midResult = getTransactionsQty(listAccountValues, mid);
            if (midResult < managers) {
                high = mid;
            } else if (midResult > managers) {
                low = mid;
            } else {
                if (getTransactionsQty(listAccountValues, mid + 1).equals(managers)){
                    low = mid;
                } else return mid;
            }
        }
        return null;
    }

    private static List <Long> getList(Long numOfElements){
        return LongStream.range(1, numOfElements + 1)
                .mapToObj(x -> (long) randInt(1, 100000000))
                .collect(Collectors.toList());
    }

    private static int randInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private static Long noBonusCase(List<Long> listAccountValues, Long managers){
        Long sum = getSumAccountValue(listAccountValues);
        if (sum < managers) {
            return 0L;
        }
    }
}

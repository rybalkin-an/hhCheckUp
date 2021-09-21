import org.junit.jupiter.api.Test;
import task1.Main;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    void test1(){
        Long managers = 100L;
        List<Long> list = Arrays.asList(0L, 0L, 1L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals( 0L, bonus);
    }

    @Test
    void test2(){
        Long managers = 99L;
        List<Long> list = Arrays.asList(99L, 99L, 99L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals(3L, bonus);
    }

    @Test
    void test3(){
        Long managers = 99L;
        List<Long> list = Arrays.asList(999L, 999L, 999L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals(30L, bonus);
    }

    @Test
    void test4(){
        Long managers = 100L;
        List<Long> list = Arrays.asList(1L, 51L, 51L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals(1L, bonus);
    }

    @Test
    void test5(){
        Long managers = 1L;
        List<Long> list = Arrays.asList(1L, 5551L, 51L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals(5551L, bonus);
    }

    @Test
    void test6(){
        Long managers = 2000L;
        List<Long> list = Arrays.asList(0L);

        Long bonus = Main.getBonus(list, managers);
        assertEquals(0L, bonus);
    }
}

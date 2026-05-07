// Dziedziczymy po klasie Zwierze, a nie Organizm
public class Wilk extends Zwierze {

    // W Javie zamiast #define używamy stałych (public static final)
    private static final char ZNAK_WILKA = 'W';

    public Wilk(int polozenieX, int polozenieY, Swiat swiat) {
        // super() to odpowiednik listy inicjalizacyjnej z C++
        // Przekazujemy: siła (9), inicjatywa (5), x, y, swiat, znak
        super(9, 5, polozenieX, polozenieY, swiat, ZNAK_WILKA);
    }

    // Adnotacja @Override to dobra praktyka w Javie, 
    // mówi kompilatorowi, że nadpisujemy metodę z klasy bazowej
    @Override
    protected Organizm Rozmnazaj(int x, int y) {
        return new Wilk(x, y, swiat);
    }

    @Override
    public boolean CzyDrapieznik() {
        return true;
    }
}
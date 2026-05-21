public class Wilk extends Zwierze {

    private static final char ZNAK_WILKA = 'W';

    public Wilk(int polozenieX, int polozenieY, Swiat swiat) {
        super(9, 5, polozenieX, polozenieY, swiat, ZNAK_WILKA);
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y) {
        return new Wilk(x, y, swiat);
    }

    @Override
    public boolean CzyDrapieznik() {
        return true;
    }
}
public class Owca extends Zwierze{
    private static final char ZNAK_OWCY = 'O';

    public Owca(int polozenieX, int polozenieY, Swiat swiat) {
        super(4, 4, polozenieX, polozenieY, swiat, ZNAK_OWCY);
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y) {
        return new Owca(x, y, swiat);
    }
}

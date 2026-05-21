public class WilczeJagody extends Roslina {
    private static final char ZNAK_WILCZYCH_JAGOD = 'J';

    public WilczeJagody(int PolozenieX, int PolozenieY, Swiat swiat) {
        super(99, 0, PolozenieX, PolozenieY, swiat, ZNAK_WILCZYCH_JAGOD);
    }

    @Override
    public boolean CzyObronil(Organizm napastnik){
        this.Zabij();
        String tekst = "Wilcze jagody zabijaja organizm ";
        tekst += napastnik.WezZnak();
        swiat.DodajKomunikat(tekst);
        return true;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new WilczeJagody(x,y,swiat);
    }
}
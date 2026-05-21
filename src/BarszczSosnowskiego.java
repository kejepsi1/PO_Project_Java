public class BarszczSosnowskiego extends Roslina{
    private static final char ZNAK_BARSZCZU = 'B';
    public BarszczSosnowskiego(int PolozenieX, int PolozenieY, Swiat swiat){
        super(10,0,PolozenieX,PolozenieY,swiat,ZNAK_BARSZCZU);
    }

    @Override
    public void Akcja(int klawisz) {
        if (this.wiek == 0) {
            return;
        }

        int mozliweX[] = {-1, 1, 0, 0, -1, -1, 1, 1};
        int mozliweY[] = {0, 0, -1, 1, -1, 1, -1, 1};

        for (int j = 0; j < 8; j++) {
            int potencjalneX = PolozenieX + mozliweX[j];
            int potencjalneY = PolozenieY + mozliweY[j];

            for (int i = 0; i < swiat.WezOrganizmy().size(); i++) {
                Organizm sasiad = swiat.WezOrganizmy().get(i);

                if (sasiad.WezPolozenieX() == potencjalneX && sasiad.WezPolozenieY() == potencjalneY) {
                    if (sasiad.CzyZyje() && sasiad instanceof Zwierze) {
                        if (!sasiad.UniknijSmierci(this)) {
                            String tekst = "Barszcz Sosnowskiego zabija: ";
                            tekst += sasiad.WezZnak();
                            swiat.DodajKomunikat(tekst);
                            sasiad.Zabij();
                        }
                    }
                }
            }
        }
        super.Akcja(klawisz);
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new BarszczSosnowskiego(x,y,swiat);
    }

    @Override
    public boolean CzyObronil(Organizm napastnik){
        this.Zabij();
        String tekst = "Barszcz Sosnowskiego zabija organizm ";
        tekst += napastnik.WezZnak();
        swiat.DodajKomunikat(tekst);
        return true;
    }
}

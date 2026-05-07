public class Roslina extends Organizm {
    public Roslina(int sila, int inicjatywa, int polozenieX, int polozenieY, Swiat swiat, char znak) {
        super(sila, inicjatywa, polozenieX, polozenieY, swiat, znak);
    }

    @Override
    public void Kolizja() {
        //Tutaj nie implementujemy kolizji, żeby rośliny nie atakowały zwierząt, bo to jest niemożliwe
    }

    public boolean SprawdzajSasiadow(int x, int y) {
        for (int i = 0; i < swiat.GetOrganizmy().size(); i++) {
            if (swiat.GetOrganizmy().get(i).CzyZyje() && x == swiat.GetOrganizmy().get(i).GetPolozenieX() && y == swiat.GetOrganizmy().get(i).GetPolozenieY()) {
                return false;
            }
        }
        return true;
    }
}

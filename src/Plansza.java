import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Plansza extends JPanel {
    private int szerokosc;
    private int wysokosc;
    private JButton[][] pola;

    private Swiat swiat;

    public Plansza(Swiat swiat, int wysokosc, int szerokosc) {
        this.swiat = swiat;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;

        this.pola = new JButton[wysokosc][szerokosc];

        this.setLayout(new GridLayout(wysokosc, szerokosc, 0, 0));

        zainicjalizujPola();
    }

    private void zainicjalizujPola() {
        for (int y = 0; y < wysokosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                JButton przycisk = new JButton();

                przycisk.setBackground(Color.WHITE);
                przycisk.setOpaque(true);
                przycisk.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                final int finalX = x;
                final int finalY = y;

                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean zajete = false;
                        for (Organizm org : swiat.WezOrganizmy()) {
                            if (org.CzyZyje() && org.WezPolozenieX() == finalX && org.WezPolozenieY() == finalY) {
                                zajete = true;
                                break;
                            }
                        }

                        if (zajete) {
                            JOptionPane.showMessageDialog(null, "To pole jest już zajęte!", "Błąd", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        String[] opcje = {
                                "Wilk", "Owca", "Lis", "Zółw", "Antylopa",
                                "Trawa", "Mlecz", "Guarana", "Wilcze Jagody", "Barszcz Sosnowskiego"
                        };

                        String wybor = (String) JOptionPane.showInputDialog(
                                null,
                                "Wybierz organizm do dodania na polu (" + finalX + ", " + finalY + "):",
                                "Kreator organizmów",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                opcje,
                                opcje[0]
                        );

                        if (wybor != null) {
                            Organizm nowy = null;

                            switch (wybor) {
                                case "Wilk": nowy = new Wilk(finalX, finalY, swiat); break;
                                case "Owca": nowy = new Owca(finalX, finalY, swiat); break;
                                case "Lis": nowy = new Lis(finalX, finalY, swiat); break;
                                case "Zółw": nowy = new Zolw(finalX, finalY, swiat); break;
                                case "Antylopa": nowy = new Antylopa(finalX, finalY, swiat); break;
                                case "Trawa": nowy = new Trawa(finalX, finalY, swiat); break;
                                case "Mlecz": nowy = new Mlecz(finalX, finalY, swiat); break;
                                case "Guarana": nowy = new Guarana(finalX, finalY, swiat); break;
                                case "Wilcze Jagody": nowy = new WilczeJagody(finalX, finalY, swiat); break;
                                case "Barszcz Sosnowskiego": nowy = new BarszczSosnowskiego(finalX, finalY, swiat); break;
                            }

                            if (nowy != null) {
                                swiat.DodajOrganizm(nowy);
                                odswiezPlansze();
                            }
                        }

                        SwingUtilities.getWindowAncestor(Plansza.this).requestFocusInWindow();
                    }
                });

                pola[y][x] = przycisk;
                this.add(przycisk);
            }
        }
    }

    public void odswiezPlansze() {
        for (int y = 0; y < wysokosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                pola[y][x].setBackground(Color.WHITE);
                pola[y][x].setText("");
            }
        }

        List<Organizm> organizmy = swiat.WezOrganizmy();

        for (int i = organizmy.size() - 1; i >= 0; i--) {
            Organizm org = organizmy.get(i);
            if (org.CzyZyje()) {
                int orgX = org.WezPolozenieX();
                int orgY = org.WezPolozenieY();

                if (org instanceof Wilk) {
                    pola[orgY][orgX].setBackground(Color.RED);
                    pola[orgY][orgX].setText("W");
                }

                else if (org instanceof Trawa){
                    pola[orgY][orgX].setBackground(Color.GREEN);
                    pola[orgY][orgX].setText("T");
                }

                else if (org instanceof Czlowiek){
                    pola[orgY][orgX].setBackground(Color.BLUE);
                    pola[orgY][orgX].setText("C");
                }

                else if (org instanceof Owca){
                    pola[orgY][orgX].setBackground(Color.LIGHT_GRAY);
                    pola[orgY][orgX].setText("O");
                }
                else if (org instanceof Mlecz){
                    pola[orgY][orgX].setBackground(Color.YELLOW);
                    pola[orgY][orgX].setText("M");
                }
                else if (org instanceof Lis){
                    pola[orgY][orgX].setBackground(Color.ORANGE);
                    pola[orgY][orgX].setText("L");
                }
                else if (org instanceof Zolw){
                    pola[orgY][orgX].setBackground(Color.MAGENTA);
                    pola[orgY][orgX].setText("Z");
                }
                else if (org instanceof Antylopa){
                    pola[orgY][orgX].setBackground(Color.PINK);
                    pola[orgY][orgX].setText("A");
                }
                else if (org instanceof Guarana){
                    pola[orgY][orgX].setBackground(Color.CYAN);
                    pola[orgY][orgX].setText("G");
                }
                else if (org instanceof WilczeJagody){
                    pola[orgY][orgX].setBackground(Color.GRAY);
                    pola[orgY][orgX].setText("J");
                }
                else if (org instanceof BarszczSosnowskiego){
                    pola[orgY][orgX].setBackground(new Color(100,20,20));
                    pola[orgY][orgX].setText("B");
                }
            }
        }
    }
}

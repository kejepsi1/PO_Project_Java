import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GlowneOkno extends JFrame implements KeyListener {

    private Swiat swiat;
    private Plansza plansza;
    private JTextArea dziennikZdarzen;
    private int wcisnietyKlawisz = 0;

    public GlowneOkno(int x, int y) {
        setTitle("Wirtualny Świat - Mikołaj Tchorek, s208435");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());


        swiat = new Swiat(y,x);

        plansza = new Plansza(swiat, x, y);

        add(plansza, BorderLayout.CENTER);

        JPanel panelSterowania = new JPanel();
        panelSterowania.setLayout(new BorderLayout());

        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnNastepnaTura = new JButton("Następna tura");

        btnNastepnaTura.setFocusable(false);

        btnNastepnaTura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.WykonajTure(wcisnietyKlawisz);

                wcisnietyKlawisz = 0;

                plansza.odswiezPlansze();

                odswiezDziennik();
            }
        });

        JButton btnZapisz = new JButton("Zapisz");
        btnZapisz.setFocusable(false);
        btnZapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.ZapiszDoPliku();
                odswiezDziennik();
            }
        });

        JButton btnWczytaj = new JButton("Wczytaj");
        btnWczytaj.setFocusable(false);
        btnWczytaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.WczytajZPliku();
                plansza.odswiezPlansze();
                odswiezDziennik();
            }
        });
        panelPrzyciskow.add(btnNastepnaTura);
        panelPrzyciskow.add(btnZapisz);
        panelPrzyciskow.add(btnWczytaj);

        panelSterowania.add(panelPrzyciskow, BorderLayout.NORTH);

        dziennikZdarzen = new JTextArea(10, 20);
        dziennikZdarzen.setEditable(false);
        dziennikZdarzen.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(dziennikZdarzen);
        panelSterowania.add(scrollPane, BorderLayout.CENTER);

        add(panelSterowania, BorderLayout.SOUTH);

        plansza.odswiezPlansze();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        wcisnietyKlawisz = e.getKeyCode();

    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    private void odswiezDziennik() {
        dziennikZdarzen.setText("");
        dziennikZdarzen.append("--- RAPORT Z TURY ---\n");
        for (String komunikat : swiat.WezKomunikaty()) {
            dziennikZdarzen.append(komunikat + "\n");
       }
    }
}
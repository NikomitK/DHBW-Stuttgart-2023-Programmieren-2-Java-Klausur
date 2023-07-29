package tk.nikomi.klausur.zeiterfassung;


public class Einsatz {


    private final Mitarbeiter mitarbeiter;
    private final int minuten;

    public Einsatz(Mitarbeiter mitarbeiter, int minuten) {
        this.mitarbeiter = mitarbeiter;
        this.minuten = minuten;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public int getMinuten() {
        return minuten;
    }

}

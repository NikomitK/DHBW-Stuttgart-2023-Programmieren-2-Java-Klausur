package tk.nikomi.klausur.zeiterfassung;

public class Mitarbeiter {
    private final String vorname;
    private final String nachname;
    private final int id;

    public Mitarbeiter(String vorname, String nachname, int id) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getId() {
        return id;
    }

}

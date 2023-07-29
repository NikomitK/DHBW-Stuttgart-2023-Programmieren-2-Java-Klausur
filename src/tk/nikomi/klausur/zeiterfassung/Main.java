package tk.nikomi.klausur.zeiterfassung;

public class Main {
    public static void main(String[] args) {
        Auftrag auftrag = new Auftrag();
        auftrag.leseAuftrag("zeiten.txt");

        gibEinsaetzeAus(auftrag);
        System.out.println("====");
        gibGesamtzeitenAus(auftrag);
    }

    private static void gibGesamtzeitenAus(Auftrag auftrag) {
        System.out.println("Mitarbeiter mit gesamter Auftragszeit \n");
        auftrag.mitarbeiterMitGesamtauftragszeit()
                .forEach((mitarbeiter, zeit) ->
                        System.out.printf("%s %s : %d min %n",mitarbeiter.getVorname(), mitarbeiter.getNachname(), zeit));
    }

    private static void gibEinsaetzeAus(Auftrag auftrag){
        System.out.println("Einsätze nach Auftragszeit");
        System.out.println();

        for (Einsatz einsatz : auftrag.einsaetzeNachAuftragszeit()){
            Mitarbeiter mitarbeiter = einsatz.getMitarbeiter();
            System.out.printf("%s %s : %d min%n", mitarbeiter.getVorname(), mitarbeiter.getNachname(), einsatz.getMinuten());
        }
    }
}
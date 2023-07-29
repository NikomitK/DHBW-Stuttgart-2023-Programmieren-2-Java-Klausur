package tk.nikomi.klausur.zeiterfassung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Auftrag {
    private List<Einsatz> einsatzListe;

    public void leseAuftrag(String dateiname) {
        Path mitarbeiterDatei = Paths.get("mitarbeiter.txt");
        Path zeitenDatei = Paths.get(dateiname);
        List<Mitarbeiter> mitarbeiterListe;
        try {

            mitarbeiterListe = Files.readAllLines(mitarbeiterDatei).stream()
                    .filter(this::mitarbeiterFehlerFilter)
                    .map(zeile -> zeile.split(";"))
                    .map(this::mitarbeiterVonZeile)
                    .collect(Collectors.toList());

            einsatzListe = Files.readAllLines(zeitenDatei).stream()
                    .filter(this::einsatzFehlerFilter)
                    .map(zeile -> zeile.split(";"))
                    .map(zeile -> einsatzVonZeile(zeile, mitarbeiterListe))
                    .filter(einsatz -> einsatz.getMitarbeiter().getId() != -1) //falls mitarbeiter für eintrag nicht gefunden wurde
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Einsatz> einsaetzeNachAuftragszeit() {
        einsatzListe.sort(Comparator.comparingInt(Einsatz::getMinuten));
        return einsatzListe;
    }

    public Map<Mitarbeiter, Integer> mitarbeiterMitGesamtauftragszeit() {
        Map<Mitarbeiter, Integer> gesamtZeitMap = new HashMap<>();
        einsatzListe.forEach(einsatz ->
                gesamtZeitMap.merge(einsatz.getMitarbeiter(), einsatz.getMinuten(), Integer::sum));
        return gesamtZeitMap;
    }

    private boolean mitarbeiterFehlerFilter(String zeile) {
        if (zeile.matches("\\d+;\\S+,\\S+")) {
            return true;
        } //regex für "zahl;<non-whitespace>,<non-whitespace>"
        System.out.println(zeile);
        return false;
    }

    private boolean einsatzFehlerFilter(String zeile) {
        if (zeile.matches("\\d+;\\d+")) {
            return true;
        }
        System.out.println(zeile);
        return false;
    }

    private Mitarbeiter mitarbeiterVonZeile(String[] zeile) {
        String[] namen = zeile[1].split(",");
        return new Mitarbeiter(namen[1], namen[0], Integer.parseInt(zeile[0]));
    }

    private Einsatz einsatzVonZeile(String[] zeile, List<Mitarbeiter> mitarbeiterListe) {
        return new Einsatz(mitarbeiterListe.stream()
                .filter(mitarbeiter -> mitarbeiter.getId() == Integer.parseInt(zeile[0])).findFirst()
                .orElse(new Mitarbeiter(
                        "Dieser Mitarbeiter",
                        "existiert nicht ;-P",
                        -1)),
                Integer.parseInt(zeile[1]));
    }
}

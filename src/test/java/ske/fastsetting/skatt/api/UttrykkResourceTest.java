package ske.fastsetting.skatt.api;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

import static ske.fastsetting.skatt.api.UttrykkResource.lagGrunnlag;
import static ske.fastsetting.skatt.api.UttrykkResource.lagUttrykk;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregneOgBeskrive;

public class UttrykkResourceTest {

    @Test
    public void testUttrykk() {
        final UttrykkResultat<Belop> resultat = beregneOgBeskrive(lagUttrykk(), lagGrunnlag());

        ConsoleUttrykkBeskriver.print(resultat);
    }
}
package ske.fastsetting.skatt.api;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

import static ske.fastsetting.skatt.api.UttrykkResource.lagGrunnlag;
import static ske.fastsetting.skatt.api.UttrykkResource.lagUttrykk;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregneOgBeskrive;

public class UttrykkResourceTest {

    @Test
    public void testUttrykk() {
        final BelopUttrykk uttrykk = lagUttrykk();

        final UttrykkResultat<Belop> resultat = beregneOgBeskrive(uttrykk, lagGrunnlag());

        /*System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());
        System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());
        System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());
        System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());
        System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());
        System.out.println(beregneOgBeskrive(uttrykk, lagGrunnlag()).verdi());*/

        ConsoleUttrykkBeskriver.print(resultat);
    }
}
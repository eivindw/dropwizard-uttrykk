package ske.fastsetting.skatt.api;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.PostUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.MapUttrykkBeskriver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.*;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.KonstantUttrykk.tall;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

@Path("uttrykk")
@Produces("application/json")
public class UttrykkResource {

    private static final MapUttrykkBeskriver BESKRIVER = new MapUttrykkBeskriver();

    private static final String TAG_SATS = "sats";

    @GET
    @Path("beregneOgBeskrive")
    public Object beregneOgBeskriveTest() {
        return BESKRIVER.beskriv(beregneOgBeskrive(lagUttrykk(), lagGrunnlag()));
    }

    @GET
    @Path("beregne")
    public Object beregneTest() {
        return BESKRIVER.beskriv(beregne(lagUttrykk(), lagGrunnlag()));
    }

    @GET
    @Path("beskrive")
    public Object beskriveTest() {
        return BESKRIVER.beskriv(beskrive(lagUttrykk()));
    }

    protected static Map<String, Integer> lagGrunnlag() {
        Map<String, Integer> map = new HashMap<>();
        map.put("lonn", new Random().nextInt(2_000));
        return map;
    }

    protected static BelopUttrykk lagUttrykk() {
        final ProsentUttrykk satsTrygd = prosent(8.2).navn("sats trygd").tags(TAG_SATS);
        final ProsentUttrykk satsInntektsskatt = prosent(27).navn("sats inntektsskatt").tags(TAG_SATS);

        final KroneUttrykk fradrag = kr(100).navn("fradrag").regler(Regel.skatteloven("5-1"));
        final PostUttrykk lonn = new PostUttrykk("lonn");
        final BelopDiffUttrykk lonnEtterFradrag = lonn.minus(fradrag);

        final BelopUttrykk trygdeavgift = lonnEtterFradrag.multiplisertMed(satsTrygd).navn("trygdeavgift");
        final BelopUttrykk inntektsskatt = lonnEtterFradrag.multiplisertMed(satsInntektsskatt).navn("inntektsskatt");

        final BelopDivisjonsUttrykk skatt = trygdeavgift.pluss(inntektsskatt).dividertMed(tall(2)).navn("sum skatt");

        return hvis(skatt.erMellom(kr(50), kr(100)))
            .brukDa(kr(50))
            .ellersBruk(skatt);
    }
}

package ske.fastsetting.skatt.api;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.PostUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.MapUttrykkBeskriver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.HashMap;
import java.util.Map;

import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.*;

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
        return BESKRIVER.beskriv(beskrive(lagUttrykk(), null));
    }

    protected static Map<String, Integer> lagGrunnlag() {
        return new HashMap<String, Integer>() {{
            put("lonn", 200);
        }};
    }

    protected static BelopUttrykk<Map<String, Integer>> lagUttrykk() {
        final ProsentUttrykk<Map<String, Integer>> satsTrygd = prosent(8.2).navn("sats trygd").tags(TAG_SATS);
        final ProsentUttrykk<Map<String, Integer>> satsInntektsskatt = prosent(27).navn("sats inntektsskatt").tags(TAG_SATS);

        final KroneUttrykk<Map<String, Integer>> fradrag = kr(100).navn("fradrag").regler(Regel.skatteloven("5-1"));
        final PostUttrykk lonn = new PostUttrykk("lonn");
        final BelopDiffUttrykk<Map<String, Integer>> lonnEtterFradrag = lonn.minus(fradrag);

        final BelopUttrykk<Map<String, Integer>> trygdeavgift = lonnEtterFradrag.multiplisertMed(satsTrygd).navn("trygdeavgift");
        final BelopUttrykk<Map<String, Integer>> inntektsskatt = lonnEtterFradrag.multiplisertMed(satsInntektsskatt).navn("inntektsskatt");

        return trygdeavgift.pluss(inntektsskatt).navn("sum skatt");
    }

    private static ProsentUttrykk<Map<String, Integer>> prosent(double prosent) {
        return ProsentUttrykk.<Map<String, Integer>>prosent(prosent);
    }

    private static KroneUttrykk<Map<String, Integer>> kr(int belop) {
        return KroneUttrykk.<Map<String, Integer>>kr(belop);
    }
}

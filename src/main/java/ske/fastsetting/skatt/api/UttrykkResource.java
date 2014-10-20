package ske.fastsetting.skatt.api;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.MapUttrykkBeskriver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.*;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

@Path("uttrykk")
@Produces("application/json")
public class UttrykkResource {

    private static final MapUttrykkBeskriver BESKRIVER = new MapUttrykkBeskriver();

    private static final String TAG_SATS = "sats";

    @GET
    @Path("beregneOgBeskrive")
    public Object beregneOgBeskriveTest() {
        return BESKRIVER.beskriv(beregneOgBeskrive(lagUttrykk(), null));
    }

    @GET
    @Path("beregne")
    public Object beregneTest() {
        return BESKRIVER.beskriv(beregne(lagUttrykk(), null));
    }

    @GET
    @Path("beskrive")
    public Object beskriveTest() {
        return BESKRIVER.beskriv(beskrive(lagUttrykk(), null));
    }

    private BelopUttrykk<Object> lagUttrykk() {
        final ProsentUttrykk<Object> satsTrygd = prosent(8.2).navn("sats trygd").tags(TAG_SATS);
        final ProsentUttrykk<Object> satsInntektsskatt = prosent(27).navn("sats inntektsskatt").tags(TAG_SATS);

        final KroneUttrykk<Object> lonn = kr(100).navn("lønn").regler(Regel.skatteloven("5-1"));

        final BelopUttrykk<Object> trygdeavgift = lonn.multiplisertMed(satsTrygd).navn("trygdeavgift");
        final BelopUttrykk<Object> inntektsskatt = lonn.multiplisertMed(satsInntektsskatt).navn("inntektsskatt");

        return trygdeavgift.pluss(inntektsskatt).navn("sum skatt");
    }
}

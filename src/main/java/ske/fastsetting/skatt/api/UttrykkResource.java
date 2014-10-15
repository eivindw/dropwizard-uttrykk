package ske.fastsetting.skatt.api;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
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

    public static final MapUttrykkBeskriver BESKRIVER = new MapUttrykkBeskriver();

    @GET
    @Path("beregneOgBeskrive")
    public Object beregneOgBeskriveTest() {
        return BESKRIVER.beskriv(beregneOgBeskrive(lagUttrykk()));
    }

    @GET
    @Path("beregne")
    public Object beregneTest() {
        return BESKRIVER.beskriv(beregne(lagUttrykk()));
    }

    @GET
    @Path("beskrive")
    public Object beskriveTest() {
        return BESKRIVER.beskriv(beskrive(lagUttrykk()));
    }

    private BelopUttrykk<?> lagUttrykk() {
        return kr(100).navn("lønn").multiplisertMed(prosent(8.2).navn("sats trygd")).navn("skatt");
    }
}

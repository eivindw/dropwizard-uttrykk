package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.Map;

public class PostUttrykk extends AbstractUttrykk<Belop, PostUttrykk> implements BelopUttrykk
{

    private final String postId;

    public PostUttrykk(String postId) {
        this.postId = postId;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return new Belop((Integer) ctx.input(Map.class).getOrDefault(postId, 0));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "Post: " + postId;
    }
}

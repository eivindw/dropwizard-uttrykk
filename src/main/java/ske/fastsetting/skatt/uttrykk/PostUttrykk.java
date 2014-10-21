package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.Map;

public class PostUttrykk
    extends AbstractUttrykk<Belop, PostUttrykk, Map<String, Integer>>
    implements BelopUttrykk<Map<String, Integer>>
{

    private final String postId;

    public PostUttrykk(String postId) {
        this.postId = postId;
    }

    @Override
    public Belop eval(UttrykkContext<Map<String, Integer>> ctx) {
        return new Belop(ctx.input().getOrDefault(postId, 0));
    }

    @Override
    public String beskriv(UttrykkContext<Map<String, Integer>> ctx) {
        return "Post: " + postId;
    }
}

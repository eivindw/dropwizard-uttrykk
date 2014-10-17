package ske.fastsetting.skatt;

import com.bazaarvoice.dropwizard.webjars.WebJarBundle;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ske.fastsetting.skatt.api.UttrykkResource;
import ske.fastsetting.skatt.module.KalkulerbarVerdiSerializer;
import ske.fastsetting.skatt.module.RegelSerializer;

public class UttrykkApp extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            args = new String[]{"server", "src/main/config/server.yml"};
        }
        new UttrykkApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.getObjectMapper().registerModule(
            new SimpleModule("Uttrykk")
                .addSerializer(new KalkulerbarVerdiSerializer())
                .addSerializer(new RegelSerializer())
        );
        bootstrap.addBundle(new WebJarBundle());
        bootstrap.addBundle(new AssetsBundle("/web/", "/", "index.html", "web"));
    }

    @Override
    public void run(Configuration conf, Environment env) throws Exception {
        env.jersey().setUrlPattern("/api/*");
        env.jersey().register(UttrykkResource.class);
    }
}

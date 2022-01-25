package cn.toutatis.vertx;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class VoidLauncher extends Launcher {

    Logger logger = LoggerFactory.getLogger(VoidLauncher.class);

    public VoidLauncher() {
//        logger.info();
    }

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }


    @Override
    public void beforeStartingVertx(VertxOptions options) {
        super.beforeStartingVertx(options);
    }


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions();
        ConfigStoreOptions config = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "config.json"));
        retrieverOptions.addStore(config);
        ConfigRetriever retriever = ConfigRetriever.create(vertx,retrieverOptions);
        retriever.getConfig(ar -> {
            if (ar.failed()) {
                // Failed to retrieve the configuration
            } else {
                String verticleName = VoidVerticle.class.getName();
                vertx.deployVerticle(verticleName);
                JsonObject result = ar.result();
                System.err.println(result);
            }
        });

//        VoidLauncher voidLauncher = new VoidLauncher();
//        voidLauncher.dispatch(
//                new String[]{"run",verticleName}
//        );
    }

}

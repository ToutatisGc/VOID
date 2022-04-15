package cn.toutatis.vertx;

import cn.toutatis.VoidContext;
import cn.toutatis.annotations.VoidVertxApplication;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Toutatis_Gc
 */
@VoidVertxApplication
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
        VoidContext voidContext = new VoidContext(vertx,VoidVerticle.class);
        voidContext.run();
//        VoidContextRuntime.INSTANCE.support(vertx);


//        VoidLauncher voidLauncher = new VoidLauncher();
//        voidLauncher.dispatch(
//                new String[]{"run",verticleName}
//        );
    }

}

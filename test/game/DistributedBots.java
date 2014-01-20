package game;

import sps.core.Logger;
import sps.util.Commander;

import java.util.ArrayList;
import java.util.List;

public class DistributedBots {
    private static final boolean captureBotIO = false;
    private static final int numBots = 5;

    private static final String simpleGameCmd = "/usr/lib/jvm/jdk1.7.0_25/bin/java -Xmx2G -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/jdk1.7.0_25/jre/lib/deploy.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/plugin.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/jfr.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/jsse.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/jfxrt.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/javaws.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/management-agent.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/sunec.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/localedata.jar:/usr/lib/jvm/jdk1.7.0_25/jre/lib/ext/sunjce_provider.jar:/home/kretst/dev/nnue/target/classes:/home/kretst/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/home/kretst/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/home/kretst/.m2/repository/org/apache/commons/commons-math3/3.2/commons-math3-3.2.jar:/home/kretst/.m2/repository/com/esotericsoftware/kryo/kryo/2.21/kryo-2.21.jar:/home/kretst/.m2/repository/com/esotericsoftware/reflectasm/reflectasm/1.07/reflectasm-1.07-shaded.jar:/home/kretst/.m2/repository/org/ow2/asm/asm/4.0/asm-4.0.jar:/home/kretst/.m2/repository/com/esotericsoftware/minlog/minlog/1.2/minlog-1.2.jar:/home/kretst/.m2/repository/org/objenesis/objenesis/1.2/objenesis-1.2.jar:/home/kretst/.m2/repository/com/badlogic/gdx/2013.06.20/gdx-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-openal/2013.06.20/gdx-openal-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-natives/2013.06.20/gdx-natives-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-backend-lwjgl/2013.06.20/gdx-backend-lwjgl-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-backend-lwjgl-natives/2013.06.20/gdx-backend-lwjgl-natives-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-controllers/2013.06.20/gdx-controllers-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-controllers-desktop/2013.06.20/gdx-controllers-desktop-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-controllers-desktop-natives/2013.06.20/gdx-controllers-desktop-natives-2013.06.20.jar:/home/kretst/.m2/repository/com/badlogic/gdx-freetype-natives/2013.07.25/gdx-freetype-natives-2013.07.25.jar:/home/kretst/.m2/repository/com/badlogic/gdx-freetype/2013.07.25/gdx-freetype-2013.07.25.jar:/home/kretst/bin/idea-IC-129.354/lib/idea_rt.jar targets.DesktopGame --play-as-bot --debug-bot-launch";
    private static int total = 0;

    public static void main(String[] args) {
        Logger.info("Bot initialization.");
        List<Commander> ps = new ArrayList<>();
        for (int ii = 0; ii < numBots; ii++) {
            ps.add(launch());
        }
        Logger.info("Created " + ps.size() + " bots ");
        while (true) {
            for (int ii = 0; ii < ps.size(); ii++) {
                if (ps.get(ii).isFinished()) {
                    //This should only happen once the process finished
                    Logger.info("A bot has finished. Launching a new one");
                    ps.remove(ii);
                    ii--;
                    ps.add(launch());
                    Logger.info("New bot launched, " + total + " bots since launch");
                }
            }
            while (ps.size() < numBots) {
                ps.add(launch());
            }
        }
    }


    private static Commander launch() {
        total++;
        return new Commander(simpleGameCmd, ".", captureBotIO);
    }
}

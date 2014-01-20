package game;

import com.badlogic.gdx.ApplicationListener;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Assets;
import sps.display.Screen;
import sps.display.Window;
import sps.io.Input;
import sps.text.Text;
import sps.text.TextEffects;
import sps.text.TextPool;

public class FontChecker implements ApplicationListener {
    private static DummyApp _context;

    public static void main(String[] args) {
        _context = new DummyApp(new FontChecker());
    }

    public FontChecker() {
    }

    /*
    A-Z0->9 -> [A]->[Z],[0]->[9]
    a,alt
    b,ctrl
    c,del
    d,end
    e,enter
    f,return
    g,home
    h,insert
    i,prtscr
    j,shift
    k,space
    l->u,f1->f10
    v,tab
    w,up
    x,right
    y,down
    z,left
     */

    @Override
    public void create() {
        _context.create();

        int defaultSize = 40;
        Assets.get().fontPack().setDefault("Economica-Regular.ttf", defaultSize);
        Assets.get().fontPack().cacheFont("keys", "Keycaps Regular.ttf", defaultSize);

        Point2 pos = Screen.pos(5, 50);
        String ids = "ABCDEFGHIJKL\nMNOPQRSTUVWXYZ\nabcdefghijkl\nmnopqrstuvwxyz\n1234567890";
        TextPool.get().write(ids, pos, Text.NotTimed, "default", defaultSize, TextEffects.None, Color.WHITE, 1);
        TextPool.get().write(ids + "", pos.add(Screen.pos(0, 30)), Text.NotTimed, "keys", defaultSize, TextEffects.None, Color.WHITE, 1);
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void render() {
        Input.get().update();

        TextPool.get().draw();

        Window.get().processScheduledApiCalls();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}

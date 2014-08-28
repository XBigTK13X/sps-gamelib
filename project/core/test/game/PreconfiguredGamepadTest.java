package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.input.PlayerIndex;
import com.simplepathstudios.gamelib.input.Players;
import com.simplepathstudios.gamelib.input.gamepad.GamepadInput;
import com.simplepathstudios.gamelib.input.gamepad.PreconfiguredGamepadInputs;

import java.util.Map;

public class PreconfiguredGamepadTest extends InputAdapter implements ApplicationListener {
    private static DummyApp _context;

    public static void main(String[] args) {
        _context = new DummyApp(new PreconfiguredGamepadTest());
    }

    @Override
    public void create() {
        _context.create();
        // print the currently connected controllers to the console
        print("Controllers: " + Controllers.getControllers().size);
        int i = 0;
        for (Controller controller : Controllers.getControllers()) {
            print("#" + i++ + ": " + controller.getName());
        }
        if (Controllers.getControllers().size == 0) {
            print("No controllers attached");
        }
        for(PlayerIndex player:Players.getAll()){
            if(player.KeyboardIndex != null){
                Logger.info("Player " +player.PlayerIndex+" registered with keyboard #"+player.KeyboardIndex);
            }
            else{
                Logger.info("Player " +player.PlayerIndex+" registered with "+player.GamepadType +" gamepad number #"+player.GamepadIndex);
            }
        }
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void render() {
        //Macair was running loud and hot without this throttle
        try {
            Thread.sleep(50L);
        }
        catch (Exception e) {

        }

        for(PlayerIndex player:Players.getAll()){
            Map<String, Map<String, GamepadInput>> inputs = PreconfiguredGamepadInputs.getAll();
            for (String gamepadType : inputs.keySet()) {
                if (gamepadType.equalsIgnoreCase(player.GamepadType)) {
                    for (GamepadInput input : inputs.get(gamepadType).values()) {
                        if (input.isActive(player)) {
                            Logger.info(input.getName() + " is active for player "+player.PlayerIndex);
                        }
                    }
                }
            }
        }

        //Macair screen was garbled without this clearing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    private void print(String message) {
        System.out.println(message);
    }
}
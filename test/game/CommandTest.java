package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL10;
import sps.bridge.Command;
import sps.bridge.Commands;
import sps.core.Logger;
import sps.input.Input;
import sps.input.PlayerIndex;
import sps.input.Players;
import sps.input.gamepad.GamepadInput;
import sps.input.gamepad.PreconfiguredGamepadInputs;

import java.util.Map;

public class CommandTest extends InputAdapter implements ApplicationListener {
    private static DummyApp _context;

    public static void main(String[] args) {
        _context = new DummyApp(new CommandTest());
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

        Logger.info("Number of players: " + Players.getAll().size());
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void render() {
        Input.get().update();
        //Macair was running loud and hot without this throttle
        try {
            Thread.sleep(50L);
        }
        catch (Exception e) {

        }

        for (PlayerIndex player : Players.getAll()) {
            for (Command command : Commands.values()) {
                if (Input.get().isActive(command, player)) {
                    Logger.info(command.name() + " is active for player " + player.PlayerIndex);
                }
            }
        }

        //Macair screen was garbled without this clearing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
package bagel.util;

import com.badlogic.gdx.Gdx;

public class Inputer {

    public static boolean pressedKey(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    public static boolean tappedKey(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }

}

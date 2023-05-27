package GameMechanics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.HashMap;

public class KeyHandler implements KeyListener {
    private Map<Integer, Boolean> keyMap;

    public KeyHandler() {
        keyMap = new HashMap();
    }

    public boolean getKeyStatus(int code) {
        return keyMap.getOrDefault(code, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        keyMap.put(code, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keyMap.put(code, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

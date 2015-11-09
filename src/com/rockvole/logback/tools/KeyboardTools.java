package com.rockvole.logback.tools;


import jline.console.ConsoleReader;
import jline.console.KeyMap;
import jline.console.Operation;

import java.io.IOException;

public class KeyboardTools {
    public enum KeyCode {
        KEY_UNKNOWN(1),
        KEY_UP(2),
        KEY_DOWN(3),
        KEY_RIGHT(4),
        KEY_LEFT(5),
        KEY_SPACE(6),
        KEY_PAGE_UP(7),
        KEY_PAGE_DOWN(8),
        KEY_ESC(9);
        private int value;
        private KeyCode(int value) {
            this.value = value;
        }
        public int intValue() { return value; }
    }

    public static KeyCode readKey() {

        try {
            ConsoleReader reader = new ConsoleReader();

            Object bindingObject = null, lastBindingObject = null;
            Character bindingChar, lastBindingChar = null;
            Operation bindingOp = null, lastBindingOp = null;

            bindingOp = null;
            lastBindingOp = null;
            bindingChar = null;
            lastBindingChar = null;
            bindingObject = reader.readBinding(KeyMap.emacs());
            if (bindingObject instanceof Operation) {
                bindingOp = (Operation) bindingObject;
            } else {
                bindingChar = ((String) bindingObject).charAt(0);
            }
            if(bindingOp==Operation.COMPLETE) return KeyCode.KEY_ESC;

            lastBindingObject = reader.getLastBinding();
            if (lastBindingObject instanceof Operation) {
                lastBindingOp = (Operation) lastBindingObject;
            } else {
                lastBindingChar = ((String) lastBindingObject).charAt(0);
            }
            switch (bindingOp) {
                case SELF_INSERT:
                    switch (lastBindingChar) {
                        case 'A':
                            return KeyCode.KEY_UP;
                        case 'C':
                            return KeyCode.KEY_RIGHT;
                        case 'D':
                            return KeyCode.KEY_LEFT;
                        case 'B':
                            return KeyCode.KEY_DOWN;
                        case ' ':
                            return KeyCode.KEY_SPACE;
                        case '5':
                            return KeyCode.KEY_PAGE_UP;
                        case '6':
                            return KeyCode.KEY_PAGE_DOWN;
                        default:
                    }
                    break;
                default:
                    System.err.println("Unknown binding");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return KeyCode.KEY_UNKNOWN;
    }

}

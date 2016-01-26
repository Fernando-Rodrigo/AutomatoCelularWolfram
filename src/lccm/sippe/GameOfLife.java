package lccm.sippe;

import lccm.sippe.controller.MainController;

import lccm.sippe.model.Automata;
import lccm.sippe.view.GUIFrame;
import javax.swing.*;

/**
 * @author: Luis Carlos Castillo Martinez on 18/01/16.
 * Université Blaise Pascal
 * lcarlos.asimov@gmail.com
 * github.com/luisccastillo
 */

public class GameOfLife {
	public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIFrame guiFrame = new GUIFrame(80, 80);
                Automata automata = new Automata(80, 80);
                MainController mainController = new MainController(guiFrame, automata);
            }
        });
	}
}

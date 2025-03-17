package game.server;

import game.GameBoot;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.wrapper.StaleProxyException;

import javax.swing.*;

public class ServerAg extends GuiAgent {

    public static String SERVER_CONTAINER_NAME = "Server";
    public static String SERVER_AGENT_NAME = "Server";

    private ServerGui gui;

    public ServerAg() {
    }

    @Override
    protected void onGuiEvent(GuiEvent ev) {
        var cmd = ev.getType();
        switch (cmd) {
            case ServerGui.GUI_ON_CLOSE -> doDelete();
            default -> showMessage("Event " + cmd + " not recognized");
        }
    }

    @Override
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        initAndShowGui();
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> gui.showMessage(message));
    }

    private void initAndShowGui() {
        gui = new ServerGui(this);
        SwingUtilities.invokeLater(() -> gui.initGui());
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + " finished.");
        System.exit(0);
    }

    public static void main(String[] arg) throws StaleProxyException {
        var server = new ServerAg();
        GameBoot.newAgentInNewContainer(SERVER_CONTAINER_NAME, SERVER_AGENT_NAME, server);
    }
}

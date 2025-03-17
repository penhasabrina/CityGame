package game;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public final class GameBoot {

    public final static String GAME_NAME = "CityGame";

    public static ImageIcon loadIcon(String path, String description) {
        ImageIcon icon = null;
        URL url = GameBoot.class.getResource(path);
        if(url != null) {
            try {
                icon = new ImageIcon(ImageIO.read(url), description);
            } catch (IOException ignored) {
            }
        }
        return icon;
    }

    /**
     * Cria um novo container na plataforma e inicia um novo agente neste container
     * @param containerName o nome do novo conatiner
     * @param agentName o nome do agente
     * @param agent instância do novo agente
     * @throws StaleProxyException
     */
    public static void newAgentInNewContainer(String containerName, String agentName, Agent agent) throws StaleProxyException {
        Profile p = new ProfileImpl(false);
        p.setParameter(Profile.PLATFORM_ID, GAME_NAME);
        p.setParameter(Profile.CONTAINER_NAME, containerName);
        var cc = Runtime.instance().createAgentContainer(p);
        var ac = cc.acceptNewAgent(agentName, agent);
        ac.start();
    }

    /**
     * Inicia um novo agente na plataforma a partir de um container existente, isto é, no mesmo container de um agent pai
     * @param parent o agente de qual container o novo agente será inserido
     * @param agentName o nome do agente
     * @param agent a instância do novo agente
     * @throws StaleProxyException
     */
    public static void newAgentInExistingContainer(Agent parent, String agentName, Agent agent) throws StaleProxyException {
        var cc = parent.getContainerController();
        var ac = cc.acceptNewAgent(agentName, agent);
        ac.start();
    }


    /**
     * Ponto de entrada da aplicação
     * @param args argumentos de linha de comando do sistema operacional
     */
    public static void main(String[] args) {
        Profile p = new ProfileImpl(true);
        p.setParameter(Profile.PLATFORM_ID, GAME_NAME);
        p.setParameter(Profile.GUI, "true");
        Runtime.instance().createMainContainer(p);
    }

}

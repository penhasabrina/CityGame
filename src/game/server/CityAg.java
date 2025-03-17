package game.server;

import jade.gui.GuiAgent;

import java.util.List;

public class CityAg extends GuiAgent {
    private ServerGui gui;

    private String cityName;
    private String cityOwner;
    private String forestAID;
    private String mineAID;
    private String squareAID;
    private List<String> workersAIDs;
    private List<String> soldiersAIDs;
    private List<String> barracksAIDs;
    private List<String> cottagesAIDs;
    private List<String> farmsAIDs;

    public CityAg(ServerGui gui) {
        this.gui = gui;
    }


}

package game.server;

public final class Lobby {
    private final String cityName;
    private final String cityOwner;

    public Lobby(String cityName, String cityOwner) {
        this.cityName = cityName;
        this.cityOwner = cityOwner;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityOwner() {
        return cityOwner;
    }

    @Override
    public String toString() {
        return "(lobby: " + cityName + "," + cityOwner + ")";
    }
}

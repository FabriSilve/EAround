package lpaa.earound;

/**
 * Created by Fabrizio on 12/06/2017.
 */

public class Search {
    private String position;
    private int distance;
    private int days;
    private boolean party;
    private boolean cultural;
    private boolean music;
    private boolean sport;

    public Search(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public int getDistance() {
        return distance;
    }

    public int getDays() {
        return days;
    }

    public boolean isParty() {
        return party;
    }

    public boolean isCultural() {
        return cultural;
    }

    public boolean isMusic() {
        return music;
    }

    public boolean isSport() {
        return sport;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setParty(boolean party) {
        this.party = party;
    }

    public void setCultural(boolean cultural) {
        this.cultural = cultural;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }
}

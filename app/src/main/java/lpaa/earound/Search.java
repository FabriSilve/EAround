package lpaa.earound;


import android.util.Log;

class Search {

    private final String TAG = "Search";
    private String position;
    private int distance;
    private int days;
    /*private boolean party;
    private boolean cultural;
    private boolean sport;
    private boolean music;*/

    Search(String position, int distance, int days/*, boolean party, boolean cultural, boolean sport, boolean music*/) {
        Log.d(TAG, "Search: costructor");
        this.position = position;
        this.distance = distance;
        this.days = days;
        /*this.party = party;
        this.cultural = cultural;
        this.sport = sport;
        this.music = music;*/
    }

    String getPosition() {
        return position;
    }

    String getDistance() {
        return String.valueOf(distance);
    }

    String getDays() {
        return String.valueOf(days);
    }

    /*String getParty() {
        return String.valueOf(party);
    }

    String getCultural() {
        return String.valueOf(cultural);
    }

    public String getSport() {
        return String.valueOf(sport);
    }

    public String getMusic() {
        return String.valueOf(music);
    }*/


}

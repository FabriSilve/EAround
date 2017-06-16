package lpaa.earound;


public enum EventType {
    PARTY,
    CULTURAL,
    MUSIC,
    SPORT,
    UNKNOW;

    public static EventType valueFromString(String type) {
        switch (type) {
            case "party":
                return EventType.PARTY;
            case "cultural":
                return EventType.CULTURAL;
            case "music":
                return EventType.MUSIC;
            case "sport":
                return EventType.SPORT;
            default:
                return EventType.UNKNOW;
        }
    }
}

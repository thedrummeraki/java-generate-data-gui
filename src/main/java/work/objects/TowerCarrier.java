package work.objects;

public class TowerCarrier {

    private String licensee;
    private int mnc;
    private int mcc;
    private boolean undefined;

    private TowerCarrier(String licensee) {
        undefined = true;
        this.licensee = licensee;
    }

    private TowerCarrier(String licensee, int mnc, int mcc) {
        this.licensee = licensee;
        this.mnc = mnc;
        this.mcc = mcc;
    }

    public int getMCC() {
        return mcc;
    }

    public int getMNC() {
        return mnc;
    }

    public String getLicensee() {
        return licensee;
    }

    public boolean isUndefined() {
        return undefined;
    }

    @Override
    public String toString() {
        return licensee;
    }

    public static final TowerCarrier ROGERS = new TowerCarrier("Rogers", 302, 322);
    public static final TowerCarrier FREEDOM_MOBILE = new TowerCarrier("Freedom Mobile", 490, 322);
    public static final TowerCarrier BELL = new TowerCarrier("Bell", 614, 322);
    public static final TowerCarrier TELUS = new TowerCarrier("TELUS", 302, 322);
    public static final TowerCarrier FIDO = new TowerCarrier("Fido", 370, 322);
    public static final TowerCarrier VIDEOTRON = new TowerCarrier("Rogers", 302, 322);
    public static final TowerCarrier ALL_CARRIERS = new TowerCarrier("* All carriers *");

    public static final TowerCarrier[] TOWER_CARRIERS = {
            ALL_CARRIERS,
            ROGERS,
            BELL,
            TELUS,
            FIDO,
            VIDEOTRON,
            FREEDOM_MOBILE
    };

    public static TowerCarrier chooseRandomCarrier() {
        TowerCarrier carrier;
        do {
           carrier = TOWER_CARRIERS[(int)(Math.random() * TOWER_CARRIERS.length)];
        } while (carrier.isUndefined());
        return carrier;
    }
}

package discord.discord;

public class CategoryManagerStatus {
    private String ServerName;
    private String category;
    private String vObserwator;
    private String vMfo;
    private String vCi;
    private String vNaukowiec;
    private String vKlasaD;
    private String vScp;


    public CategoryManagerStatus(String ServerName, String category, String vObserwator, String vMfo, String vCi, String vNaukowiec, String vKlasaD, String vScp) {
        this.ServerName = ServerName;
        this.category = category;
        this.vObserwator = vObserwator;
        this.vMfo = vMfo;
        this.vCi = vCi;
        this.vNaukowiec = vNaukowiec;
        this.vKlasaD = vKlasaD;
        this.vScp = vScp;
    }

    public String getServerName() {
        return ServerName;
    }
    public void setServerName(String serverName) {
        this.ServerName = serverName;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getvObserwator() {
        return vObserwator;
    }
    public void setvObserwator(String obserwator) {
        this.vObserwator = obserwator;
    }

    public String getvMfo() {
        return vMfo;
    }
    public void setvMfo(String mfo) { this.vMfo = mfo; }

    public String getvCi() {
        return vCi;
    }
    public void setvCi(String ci) { this.vCi = ci; }

    public String getvNaukowiec() {
        return vNaukowiec;
    }
    public void setvNaukowiec(String naukowiec) { this.vNaukowiec = naukowiec; }

    public String getvKlasaD() {
        return vKlasaD;
    }
    public void setvKlasaD(String klasaD) { this.vKlasaD = klasaD; }

    public String getvScp() {
        return vScp;
    }
    public void setvScp(String scp) { this.vScp = scp; }


}

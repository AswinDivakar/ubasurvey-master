package in.nullify.survey;

/**
 * Created by Abhishekpalodath on 04-08-2018.
 */

public class SurveyHome {
    private String id;
    private String houseno;
    private String owner;
    private String wardno;
    private String village;
    private String grampanch;
    private String district;
    private String state;

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWardno() {
        return wardno;
    }

    public void setWardno(String wardno) {
        this.wardno = wardno;
    }

    public String getGrampanch() {
        return grampanch;
    }

    public void setGrampanch(String grampanch) {
        this.grampanch = grampanch;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package beans;

import java.util.List;

public class VeloToFix extends Velo {

    private List<Dommage> listeDommages;
    private Reparateur reparateur;
    private String type; //use in the list to display to reparateur

    public List<Dommage> getListeDommages() {
        return listeDommages;
    }

    public void setListeDommages(List<Dommage> dommages) {
        this.listeDommages = dommages;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

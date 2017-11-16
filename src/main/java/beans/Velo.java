package beans;

import java.util.List;

public class Velo {

    private int id;
    private Position position;
    private List<Dommage> listeDommages;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dommage> getListeDommages() {
        return listeDommages;
    }

    public void setListeDommages(List<Dommage> dommages) {
        this.listeDommages = dommages;
    }
}

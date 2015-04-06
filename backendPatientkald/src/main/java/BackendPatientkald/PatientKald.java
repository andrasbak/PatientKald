package BackendPatientkald;

/**
 * Created by Mathias Lyngman on 01-04-2015.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class PatientKald {

    @Id
    Long id;



    @Index
    private String kaldId;

    private String kald;
    private String beacon;
    private String navn;
    private String stue;


    public PatientKald() {
    }

    public String getkald() {
        return kald;
    }

    public void setkald(String kald) {
        this.kald = kald;
    }

    public String getkaldId() {
        return kaldId;
    }

    public void setkaldId(String kaldId) {
        this.kaldId = kaldId;
    }

    public String getbeacon() {
        return beacon;
    }

    public void setbeacon(String beacon) {
        this.beacon = beacon;
    }

    public String getnavn() {
        return navn;
    }

    public void setnavn(String navn) {
        this.navn = navn;
    }

    public String getStue() {
        return stue;
    }

    public void setStue(String stue) {
        this.stue = stue;
    }
}



package BackendPatientkald;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId;
    private String brugertype;

    public RegistrationRecord() {
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = brugertype;
    }

    public String getBrugertype() {
        return brugertype;
    }

    public void setBrugertype(String brugertype) {
        this.brugertype = brugertype;
    }



}
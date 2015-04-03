package BackendPatientkald;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Mathias Lyngman on 03-04-2015.
 */
@Entity
public class SygeplejerKald {

    @Id
    private String kaldId;

    @Index
    private String kald;
    private String data;


    public SygeplejerKald() {
    }

    public String getKaldId() {
        return kaldId;
    }

    public void setKaldId(String kaldId) {
        this.kaldId = kaldId;
    }

    public String getKald() {
        return kald;
    }

    public void setKald(String kald) {
        this.kald = kald;
    }

    public String getData() {
        return data;
    }

    public void setData(String beacon) {
        this.data = beacon;
    }

}


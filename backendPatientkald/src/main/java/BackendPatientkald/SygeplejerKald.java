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
    Long id;

    @Index
    private String kald, kaldId, data, regId;


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

    public void setData(String data) {
        this.data = data;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }





}


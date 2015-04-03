package BackendPatientkald;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myBeanApi",
        version = "v1",
        resource = "myBean",
        namespace = @ApiNamespace(
                ownerDomain = "BackendPatientkald",
                ownerName = "BackendPatientkald",
                packagePath = ""
        )
)
public class MyBeanEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

}

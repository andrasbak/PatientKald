package BackendPatientkald;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static BackendPatientkald.OfyService.ofy;


@Api(
        name = "kaldApi",
        version = "v1",
        resource = "patientKald",
        namespace = @ApiNamespace(
                ownerDomain = "BackendPatientkald",
                ownerName = "BackendPatientkald",
                packagePath = ""
        )
)
public class KaldEndpoint {

    private static final Logger logger = Logger.getLogger(KaldEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    /**
     * Registrer et patientkald til backend
     *
     * @param kaldString The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "kald", path = "kald")
    public void kald(@Named("kaldString") String kaldString) throws IOException {

        String[] parts = kaldString.split(":");
        String kald = parts[0];
        System.out.println(kald);

        //Hvis kaldet kommer fra sygeplejeren
        if(kald.equals("sygeplejerAcceptDeny")){
            String kaldId = parts[1];
            String data = parts[2];
            if(data.equals("accept")  || data.equals("deny")){
                //Send confirmation if accept and find next in list of caretakers closest if deny unless call is taken"
                System.out.println(data);
            }
            else if(kald.equals("sygeplejerBeacon")){

                //Store beacondistance until all caretakers have answered
                SygeplejerKald sk = new SygeplejerKald();
                sk.setKaldId(kaldId);
                sk.setData(data);
                System.out.println(data);
            }
            else{

            }

        }

        //Hvis kaldet kommer fra patienten
        else {
            String kaldId = parts[1];
            String beacon = parts[2];
            String navn = parts[3];
            String stue = parts[4];
            System.out.println(kald + kaldId + beacon + navn + stue);

            PatientKald pk  = new PatientKald();
            pk.setkaldId(kald);
            pk.setkald(kaldId);
            pk.setbeacon(beacon);
            pk.setnavn(navn);
            pk.setStue(stue);
            ofy().save().entity(pk).now();
            MessagingEndpoint.sendMessage(kaldId + "hvorerdu");
        }

    }



    @ApiMethod(name = "findkald", path = "findkald")
    public PatientKald findRecord(@Named("kaldid") String kaldId) {
        return ofy().load().type(PatientKald.class).filter("kaldId", kaldId).first().now();
    }



    //AUTOGENERERET KODE!!!!!!!
    /**
     * Returns the {@link PatientKald} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code PatientKald} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "patientKald/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public PatientKald get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting PatientKald with ID: " + id);
        PatientKald patientKald = ofy().load().type(PatientKald.class).id(id).now();
        if (patientKald == null) {
            throw new NotFoundException("Could not find PatientKald with ID: " + id);
        }
        return patientKald;
    }

    /**
     * Inserts a new {@code PatientKald}.
     */
    @ApiMethod(
            name = "insert",
            path = "patientKald",
            httpMethod = ApiMethod.HttpMethod.POST)
    public PatientKald insert(PatientKald patientKald) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that patientKald.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(patientKald).now();
        logger.info("Created PatientKald.");

        return ofy().load().entity(patientKald).now();
    }

    /**
     * Updates an existing {@code PatientKald}.
     *
     * @param id          the ID of the entity to be updated
     * @param patientKald the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code PatientKald}
     */
    @ApiMethod(
            name = "update",
            path = "patientKald/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public PatientKald update(@Named("id") Long id, PatientKald patientKald) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(patientKald).now();
        logger.info("Updated PatientKald: " + patientKald);
        return ofy().load().entity(patientKald).now();
    }

    /**
     * Deletes the specified {@code PatientKald}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code PatientKald}
     */
    @ApiMethod(
            name = "remove",
            path = "patientKald/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(PatientKald.class).id(id).now();
        logger.info("Deleted PatientKald with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "patientKald",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<PatientKald> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<PatientKald> query = ofy().load().type(PatientKald.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<PatientKald> queryIterator = query.iterator();
        List<PatientKald> patientKaldList = new ArrayList<PatientKald>(limit);
        while (queryIterator.hasNext()) {
            patientKaldList.add(queryIterator.next());
        }
        return CollectionResponse.<PatientKald>builder().setItems(patientKaldList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(PatientKald.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find PatientKald with ID: " + id);
        }
    }
}
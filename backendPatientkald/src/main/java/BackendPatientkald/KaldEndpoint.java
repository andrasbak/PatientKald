package BackendPatientkald;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;

import java.io.IOException;
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
     * Registrer et kald til backend
     *
     * @param kaldString pakke med data fra patient eller sygeplejer
     */
    @ApiMethod(name = "kald", path = "kald")
    public void kald(@Named("kaldString") String kaldString) throws IOException {

        String[] parts = kaldString.split("!");
        String kald = parts[0];
        System.out.println(kald+"-----------------------------------------------");

        //Hvis kaldet kommer fra sygeplejeren
        if(kald.equals("sygeplejerAcceptDeny")) {
            //Send confirmation if accept and find next in list of caretakers closest if deny unless call is taken"
            String kaldId = parts[1];
            String data = parts[2];
            String regId = parts[3];

            PatientKald erKaldTaget = ofy().load().type(PatientKald.class).filter("kaldId", kaldId).first().now();
            if (data.equals("accept")) {
                System.out.println("accept");
                if(erKaldTaget.getErKaldTaget().equals("false")){
                    System.out.println("erkaldtaget = false");
                    MessagingEndpoint.sendMessageToOne("Confirmation"+"!"+"Patientkald accepteret",regId);
                    erKaldTaget.setErKaldTaget("true");
                    ofy().save().entity(erKaldTaget).now();

                }
                else{
                    System.out.println("erkaldtaget = true");
                    MessagingEndpoint.sendMessageToOne("Confirmation"+"!"+"Patientkald ikke accepteret",regId);
                }

            }
            else{
                System.out.println("hej");

                askIfYouCanTakeAssignment(kaldId, "deny");
            }

        }
        else if(kald.equals("sygeplejerBeacon")){
            String kaldId = parts[1];
            String data = parts[2];
            String regid = parts[3];

            //Store beacondistance until all caretakers have answered
            SygeplejerKald sk = new SygeplejerKald();
            sk.setKald(kald);
            sk.setKaldId(kaldId);
            sk.setData(data);
            sk.setRegId(regid);
            ofy().save().entity(sk).now();
            System.out.println("data: " + data);
            System.out.println("kaldid: "+ kaldId);

            int listeAfSygeplejere = ofy().load().type(RegistrationRecord.class).filter("brugertype", "plejer").list().size();
            System.out.println("listeafsygeplejesker: "+listeAfSygeplejere);
            List<SygeplejerKald> beaconliste = ofy().load().type(SygeplejerKald.class).filter("kaldId", kaldId).list();
            System.out.println(beaconliste.size());

            if(listeAfSygeplejere == beaconliste.size() /*hvad nu hvis der er en der ikke svare...*/){

                askIfYouCanTakeAssignment(kaldId, "beacon");
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
            pk.setkaldId(kaldId);
            pk.setkald(kald);
            pk.setbeacon(beacon);
            pk.setnavn(navn);
            pk.setStue(stue);
            pk.setErKaldTaget("false");
            ofy().save().entity(pk).now();

            MessagingEndpoint.sendMessage("hvorerdu" + "!" + kaldId +"!"+ beacon);
            System.out.println("efter sendt hvorerdu");
        }

    }


    private void askIfYouCanTakeAssignment(String kaldId, String beaconEllerDeny) throws IOException {
        System.out.println("AskMetode");
        List<SygeplejerKald> sorteredeBeacons = ofy().load().type(SygeplejerKald.class).filter("kaldId",kaldId).order("data").list();
        for(SygeplejerKald hej: sorteredeBeacons){
            System.out.println("Sorteret liste: " + hej.getData());
        }

        SygeplejerKald tettestPaa = sorteredeBeacons.get(0);
        SygeplejerKald andentaettestpå = sorteredeBeacons.get(1);
        System.out.println("Taettest paa: " + tettestPaa.getRegId());
        System.out.println("andentaettest paa: "+ andentaettestpå.getRegId());

        PatientKald kald = ofy().load().type(PatientKald.class).filter("kaldId", kaldId).first().now();
        String message = "KanDuTageDen"+"!"+kaldId+"!"+kald.getnavn()+"!"+kald.getStue()+"!"+kald.getkald();
        System.out.println(message);
        MessagingEndpoint.sendMessageToOne(message,tettestPaa.getRegId());
        MessagingEndpoint.sendMessageToOne(message,andentaettestpå.getRegId());
    }





    @ApiMethod(name = "sorterpatientkald", path = "sorterkald")
    public CollectionResponse<PatientKald> sorterPatientKald() {
        List<PatientKald> records = ofy().load().type(PatientKald.class).filter("kaldId", "hej").order("kald").list();
        return CollectionResponse.<PatientKald>builder().setItems(records).build();
    }

    @ApiMethod(name = "sortersygeplejerkald", path = "sorterSygeplejerkald")
    public CollectionResponse<SygeplejerKald> sorterSygeplejerKald() {
        List<SygeplejerKald> records = ofy().load().type(SygeplejerKald.class).filter("kaldId", "hej").order("kald").list();
        return CollectionResponse.<SygeplejerKald>builder().setItems(records).build();
    }


    @ApiMethod(name = "findkald", path = "findkald")
    public PatientKald findRecord(@Named("kaldid") String kaldId) {
        return ofy().load().type(PatientKald.class).filter("kaldId", kaldId).first().now();
    }

    @ApiMethod(name = "listPatientKald")
    public CollectionResponse<PatientKald> listpatientkald(@Named("count") int count) {
        List<PatientKald> records = ofy().load().type(PatientKald.class).limit(count).list();
        return CollectionResponse.<PatientKald>builder().setItems(records).build();
    }

    @ApiMethod(name = "listSygeplejerKald", path = "sygeplejerkald")
    public CollectionResponse<SygeplejerKald> listsygeplejekald(@Named("count") int count) {
        List<SygeplejerKald> records = ofy().load().type(SygeplejerKald.class).limit(count).list();
        return CollectionResponse.<SygeplejerKald>builder().setItems(records).build();
    }








    //AUTOGENERERET KODE!!!!!!!
    /**
     * Returns the {@link PatientKald} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code PatientKald} with the provided ID.
     */
    /*@ApiMethod(
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
    }*/

    /**
     * Inserts a new {@code PatientKald}.
     */
    /*@ApiMethod(
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
    }*/

    /**
     * Updates an existing {@code PatientKald}.
     *
     * @param id          the ID of the entity to be updated
     * @param patientKald the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code PatientKald}
     */
    /*@ApiMethod(
            name = "update",
            path = "patientKald/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public PatientKald update(@Named("id") Long id, PatientKald patientKald) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(patientKald).now();
        logger.info("Updated PatientKald: " + patientKald);
        return ofy().load().entity(patientKald).now();
    }*/

    /**
     * Deletes the specified {@code PatientKald}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code PatientKald}
     */
    /*@ApiMethod(
            name = "remove",
            path = "patientKald/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(PatientKald.class).id(id).now();
        logger.info("Deleted PatientKald with ID: " + id);
    }*/

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    /*@ApiMethod(
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
    }*/
}
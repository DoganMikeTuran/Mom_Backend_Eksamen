package rest;

import com.google.gson.Gson;
import entity.History;
import entity.HistoryFacade;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javassist.bytecode.analysis.Executor;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.ApiCallable;
import utils.PuSelector;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        try {
            List<User> users = em.createQuery("select user from User user").getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("availablecars/{week}/{address}")
    public String availableCars(@PathParam("week") String week, @PathParam("address") String address) {
        new HistoryFacade().SaveHistory(week, address);
        // Create executor service.
        ExecutorService executorService = Executors.newFixedThreadPool(5); // husk antal brug af tråd = antal links.
        // Create collection to hold futures.
        List<Future<String>> futures = new ArrayList();
        // Create collection to hold results.
        List<String> results = new ArrayList();
        // Q&D create collection of urls here.
        List<String> urls = new ArrayList();

        urls.add("http://localhost:3333/availableCars?week=$1&comp=avis&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=hertz&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=europcar&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=budget&addr=$2");
        urls.add("http://localhost:3333/availableCars?week=$1&comp=alamo&addr=$2");
        // loop through urls to replace in week.
        for (String url : urls) {
            url = url.replace("$1", week).replace("$2", address);
            // Add callables to executorservice.
            // Since executorService.submit returns a future, adding the future
            // to the collection is trivial.
            futures.add(executorService.submit(new ApiCallable(url)));

        }
        try {
            // futures.get is blocking, this is ok, since we are awaiting completion.
            for (Future<String> future : futures) {
                // Add result of future to collection of results.                
                results.add(future.get());
            }
        } catch (Exception e) {
            // Q&D Add error to the results collection. 
            results.add(e.getMessage());
        } finally {
            // close down executorservice.
            executorService.shutdown();

        }
        return results.toString();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("history")
    public String History() {
        return new Gson().toJson(new HistoryFacade().getAllHistory());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postHistory(String history) {
        History h = new Gson().fromJson(history, History.class);
        new HistoryFacade().PostHistory(h);
        return Response.ok().entity(new Gson().toJson(h)).build();
    }

}
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.EmployeeFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("employee")
public class EmployeeResource {

    //NOTE: Change Persistence unit name according to your setup
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private EmployeeFacade employeeFacade = EmployeeFacade.getEmployeeFacade(emf);

    

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
         return Response.ok().entity(gson.toJson(employeeFacade.getAllEmployees())).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeId(@PathParam("id")Long id) {
         return Response.ok().entity(gson.toJson(employeeFacade.getEmployeeById(id))).build();
    }
    
    @Path("/name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmplyeesByName(@PathParam("name")String name) {
         return Response.ok().entity(gson.toJson(employeeFacade.getEmployeesByName(name))).build();
    }
    
    @Path("/highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response highestpaid() {
         return Response.ok().entity(gson.toJson(employeeFacade.getEmployeesWithHighestSalary())).build();
    }
    
    
}

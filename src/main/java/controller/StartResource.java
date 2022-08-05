package controller;

import Entity.Client;
import Entity.Dealer;
import Entity.OrderDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import service.Service;
import service.Service;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


@Path("/hello-world")
public class StartResource {
    @EJB
    private Service ss;


    @PermitAll
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response hello() {
        return Response.ok().entity("Hello").build();
    }

//    ADMIN DOSTUP

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin")
    public Response getClients() throws SQLException {
        return Response.ok().entity(ss.getClients()).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/{id}")
    public Response getClientByID(@PathParam("id") int id) throws SQLException {
        try {
            ss.getClientByID(id);
        } catch (Exception e) {
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(new Error("No client with id: " + id,
                            500,
                            "find"))
                    .build();
        }
        return Response.ok().entity(ss.getClientByID(id)).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin")
    public Response createClient(Client client) throws SQLException {
        try {
            ss.createClient(client);
        } catch (Exception e) {
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(new Error("incorrect customer data",
                            500,
                            "add"))
                    .build();
        }
        return Response.ok().entity(client).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/{id}")
    public Response deleteClient(@PathParam("id") Integer id) throws SQLException {
        try {
            ss.deleteClient(id);
        } catch (Exception e) {
            String s = "data cannot be deleted";
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(new Error(s,
                            500,
                            "delete")).
                    build();
        }
        return Response.ok().entity("Client with id:" + id + " Deleted").build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin")
    public Response updateClient(Client client) throws SQLException {
        try {
            ss.updateClient(client);
        } catch (Exception e) {
            String s = "incorrect customer update";
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(new Error(s,
                            500,
                            "update")).
                    build();
        }
        return Response.ok().entity(client).build();
    }


    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/order/{id}")
    public Response getClientOrders(@PathParam("id") Integer id) throws SQLException {
        return Response.ok().entity(ss.getClientOrders(id)).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/order/{id}")
    public Response deleteClientOrders(@PathParam("id") Integer id) throws SQLException {
        return Response.ok().entity(ss.deleteClientOrders(id)).build();
    }


    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/container")
    public Response getContainer() throws SQLException {
        return Response.ok().entity(ss.getContainer()).build();
    }


    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/transaction")
    public Response transaction(
            @FormParam("name") String detail_name,
            @FormParam("quantity") int quantity
    ) throws SQLException {
        return Response.ok().entity(ss.buyFromDealer(detail_name, quantity)).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/transaction2")
    public Response transaction(
            @FormParam("id") Integer id,
            @FormParam("quantity") Integer quantity
    ) throws SQLException {
        return Response.ok().entity(ss.buyFromDilerWithId(id, quantity)).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/dealer")
    public Response getDealerForAdmin() throws SQLException {
        return Response.ok().entity(ss.viewDealers()).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/money")
    public Response getNetWorth() throws SQLException {
        return Response.ok().entity(ss.getMoneyOfCompany()).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/menu")
    public Response insertNewMenu(String s) throws SQLException {
        return Response.ok().entity(ss.InsetNewMenu(s)).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/admin/menu")
    public Response deleteMenu(String s) throws SQLException {
        return Response.ok().entity(ss.deleteMenu(s)).build();
    }


//      ADMIN DOSTUP


//  CLIENT DOSTUP

    @RolesAllowed("CLIENT")
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/menu")
    public Response getMenu() throws SQLException {
        return Response.ok().entity(ss.getMenu()).build();
    }

    @RolesAllowed("CLIENT")
    @GET
    @Produces("application/json")
    @Path("client/{id}")
    public Response toOrder(@PathParam("id") Integer id, String s) throws SQLException {
        return Response.ok().entity(ss.toOrder(id, s)).build();
    }


    @RolesAllowed("CLIENT")
    @GET
    @Produces("application/json")
    @Path("client/acc/{id}")
    @ApiOperation(value = "Selects client by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Response viewAccount(@PathParam("id") Integer id) throws SQLException {
        return Response.ok().entity(ss.getClientByID(id)).build();
    }


    @RolesAllowed("CLIENT")
    @GET
    @Produces("application/json")
    @Path("client/order/{id}")
    @ApiOperation(value = "Selects client order by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Response viewOrders(@PathParam("id") Integer id) throws SQLException {
        return Response.ok().entity(ss.getOrder(id)).build();
    }


    @RolesAllowed("CLIENT")
    @PUT
    @Produces("application/json")
    @Path("client/money/{id}")
    public Response addMoney(@PathParam("id") Integer id, double money) throws SQLException {
        return Response.ok().entity(ss.addClientMoney(id, money)).build();
    }


// CLIENT DOSTUP


//  Dealer dostup

    @RolesAllowed("DEALER")
    @GET
    @Produces("application/json")
    @Path("/dealer")
    public Response viewDealer() throws SQLException {
        return Response.ok().entity(ss.viewDealers()).build();
    }

    @RolesAllowed("DEALER")
    @GET
    @Produces("application/json")
    @Path("/dealer/{id}")
    @ApiOperation(value = "Selects dealer by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Response viewDealer(@PathParam("id") Integer id) throws SQLException {
        return Response.ok().entity(ss.viewDealerById(id)).build();
    }

    @RolesAllowed("DEALER")
    @POST
    @Produces("application/json")
    @Path("/dealer")
    public Response createDealer(Dealer dealer) throws SQLException {
        return Response.ok().entity(ss.createDealer(dealer)).build();
    }

    @RolesAllowed("DEALER")
    @PUT
    @Produces("application/json")
    @Path("/dealer/quantity/{id}")
    public Response addProductQuantity(@PathParam("id") Integer id, Integer quantity) throws SQLException {
        return Response.ok().entity(ss.addProductDealer(id, quantity)).build();
    }

    @RolesAllowed("DEALER")
    @PUT
    @Produces("application/json")
    @Path("/dealer/price/{id}")
    public Response editProductPrice(@PathParam("id") Integer id, double price) throws SQLException {
        System.out.println(id + " " + price);
        return Response.ok().entity(ss.editPriceDealer(id, price)).build();
    }


//  Dealer dostpu
}



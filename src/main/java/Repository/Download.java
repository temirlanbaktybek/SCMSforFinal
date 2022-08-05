//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Repository;

//import controller.Cache;
//import database.Student;
//import service.DataBaseService;

import Entity.*;
import com.thoughtworks.qdox.model.expression.Or;

import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateful;

import static java.util.Arrays.*;

@Stateful
public class Download {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;


    public Download() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException var2) {
            System.out.println("PostgreSQL JDBC Driver is not found.");
            var2.printStackTrace();
            return;
        }

        System.out.println("Connection to DataBase...");
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SCMS", "postgres", "1234");
        System.out.println("Success!");
        this.statement = this.connection.createStatement();
    }

    public ArrayList<Client> GetClients() throws SQLException {

        ArrayList<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client;";
        ResultSet resultSet = this.statement.executeQuery(sql);

        while (resultSet.next()) {
            Integer client_id = resultSet.getInt("client_id");
            String client_name = resultSet.getString("client_name");
            String client_surename = resultSet.getString("client_surename");
            Double client_money = resultSet.getDouble("client_money");

            Client client = new Client();

            client.setClientId(client_id);
            client.setName(client_name);
            client.setSurename(client_surename);
            client.setMoney(client_money);

            clients.add(client);
        }
        return clients;
    }

    public Client GetClientByID(int id) throws SQLException {
        Client client = new Client();
        String sql = "Select * from client where client_id = ?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int ID = resultSet.getInt("client_id");
            String name = resultSet.getString("client_name");
            String surename = resultSet.getString("client_surename");
            double money = resultSet.getDouble("client_money");

            client.setClientId(ID);
            client.setName(name);
            client.setSurename(surename);
            client.setMoney(money);
        }

        return client;

    }

    public String CreateClient(Client client) throws SQLException {
        String sql = "insert into client (client_id, client_name, client_surename, client_money) Values (?,?,?,?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, client.getClientId());
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getSurename());
        preparedStatement.setDouble(4, client.getMoney());
        preparedStatement.executeUpdate();
        return "New Client ADDed";
    }

    public String DeleteClient(Integer id) throws SQLException {
        String sql = "delete from client where client_id =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        String s = "Client with id:" + id + "Deleted";
        return s;
    }

    public Client UpdateClient(Client client) throws SQLException {
        String sql = "update client set client_name = ?, client_surename =?, client_money=? where client_id = ?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurename());
        preparedStatement.setDouble(3, client.getMoney());
        preparedStatement.setInt(4, client.getClientId());
        preparedStatement.executeUpdate();
        return client;
    }

    public ArrayList<Client_Order> GetClientOrdersId(Integer id) throws SQLException {
        String sql = "select * from client_order where client_id =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Client_Order> client_orders = new ArrayList<>();
        while (resultSet.next()) {
            Client_Order cl = new Client_Order();

            int Client_id = resultSet.getInt("client_id");
            int oreder_id = resultSet.getInt("client_order_id");


            cl.setClient_id(Client_id);
            cl.setOrder_id(oreder_id);
            client_orders.add(cl);
        }
        return client_orders;
    }

    public void UpdateContainerProduct(Container con) throws SQLException {
        String sql = "update container set quantity =? where detail_name =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, con.getQuantityProduct());
        preparedStatement.setString(2, con.getProduct());
        preparedStatement.executeUpdate();
    }

    public void UpdateDealerProduct(Dealer dealer) throws SQLException {
        String sql = "update dealer set quntity =? where product_name = ?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, dealer.getQuantity());
        preparedStatement.setString(2, dealer.getProductName());
        preparedStatement.executeUpdate();
    }


    public List<Menu> GetMenu() throws SQLException {

        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM menu;";
        ResultSet resultSet = this.statement.executeQuery(sql);
        while (resultSet.next()) {
            String productName = resultSet.getString("product_name");
            Menu m = new Menu();
            m.setProductName(productName);
            menus.add(m);
        }
        return menus;
    }

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "select * from orders;";
        ResultSet resultSet = this.statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("order_id");
            String order_name = resultSet.getString("order_name");
            double price = resultSet.getDouble("order_price");

            Order or = new Order();

            or.setOrderId(id);
            or.setOrderName(order_name);
            or.setOrderPrice(price);

            orders.add(or);
        }
        return orders;
    }


    public ArrayList<OrderDetails> getOrderDetils() throws SQLException {

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        String sql = "select * from order_details";
        ResultSet resultSet = this.statement.executeQuery(sql);


        while (resultSet.next()) {
            String s = resultSet.getString("order_name");
            Array array = resultSet.getArray("details");
            double price = resultSet.getDouble("price");

            String[] sas = (String[]) array.getArray();
            OrderDetails orderDetails1 = new OrderDetails();
            orderDetails1.setOrder_name(s);
            orderDetails1.setDetailsName(asList(sas));
            orderDetails1.setPrice(price);
            orderDetails.add(orderDetails1);
        }
        return orderDetails;
    }


    public ArrayList<Container> getContainers() throws SQLException {

        ArrayList<Container> containers = new ArrayList<>();
        String sql = "select * from container";
        ResultSet resultSet = this.statement.executeQuery(sql);

        while (resultSet.next()) {
            String detailname = resultSet.getString("detail_name");
            int quantity = resultSet.getInt("quantity");


            Container container = new Container();
            container.setProduct(detailname);
            container.setQuantityProduct(quantity);


            containers.add(container);
        }
        return containers;
    }

    public void CreateOrder(Integer c_id, String p_name, ArrayList<Order> orderArrayList, double price) throws SQLException {

        String sqlCreateOrder = "INSERT INTO orders (order_id, order_name, order_price) Values(?,?,?);";
        preparedStatement = connection.prepareStatement(sqlCreateOrder);
        preparedStatement.setInt(1, orderArrayList.size() + 1);
        preparedStatement.setString(2, p_name);
        preparedStatement.setDouble(3, price);
        preparedStatement.executeUpdate();


        String sqlClient_Order = "INSERT INTO client_order (client_id, client_order_id) Values(?,?);";
        preparedStatement = connection.prepareStatement(sqlClient_Order);
        preparedStatement.setInt(1, c_id);
        preparedStatement.setInt(2, orderArrayList.size() + 1);
        preparedStatement.executeUpdate();
    }

    public String DeleteClientOrders(Integer id) throws SQLException {
        String sql = "delete from client_order where client_id =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return "Client orders deleted ";
    }

    public ArrayList<Dealer> getDealer() throws SQLException {
        ArrayList<Dealer> dealers = new ArrayList<>();
        String sql = "select * from dealer;";
        ResultSet resultSet = this.statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("dealer_id");
            String prduct_name = resultSet.getString("product_name");
            int quantity = resultSet.getInt("quntity");
            double price = resultSet.getDouble("price");

            Dealer de = new Dealer();

            de.setDealerId(id);
            de.setProductName(prduct_name);
            de.setQuantity(quantity);
            de.setPrice(price);
            dealers.add(de);

        }
        return dealers;

    }


    public void minusProduct(String item, Integer quantity) throws SQLException {
        String sqlMinnus = "update container set quantity =? where detail_name =?;";
        preparedStatement = connection.prepareStatement(sqlMinnus);
        preparedStatement.setInt(1, quantity);
        preparedStatement.setString(2, item);
        preparedStatement.executeUpdate();
    }


    public Dealer CreateDealer(Dealer dealer) throws SQLException {
        String sql = "insert into dealer (dealer_id, product_name, quntity, price) Values(?,?,?,?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, dealer.getDealerId());
        preparedStatement.setString(2, dealer.getProductName());
        preparedStatement.setInt(3, dealer.getQuantity());
        preparedStatement.setDouble(4, dealer.getPrice());
        preparedStatement.executeUpdate();

        return dealer;
    }

    public String AddProductForDealer(Integer id, Integer quantity, Dealer dealer) throws SQLException {
        String sql = "update dealer set quntity = ? where dealer_id =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, dealer.getQuantity() + quantity);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        return "Dealer with Id: " + id + " ADDED " + quantity + "th.";
    }

    public String EditPriceDealer(Integer id, double price) throws SQLException {
        String sql = "update dealer set price = ? where dealer_id =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, price);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        return "Dealer with Id: " + id + " EDITED price: " + price;
    }


    public String InsetNewMenu(String s) throws SQLException {
        String sql = "insert into menu (product_name) values (?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, s);
        preparedStatement.executeUpdate();
        return "Created new menu " + s;
    }

    public String DeleteMenu(String s) throws SQLException {
        String sql = "delete from menu where product_name =?;";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, s);
        preparedStatement.executeUpdate();
        return "Menu was deleted";
    }

//    public String InsertMenuDetail (OrderDetails orderDetails) throws SQLException {
//        String sql = "insert into order_details (order_name, details, price) Values (?,?,?);";
//        String[] sas = orderDetails.getDetailsName().toArray(new String[orderDetails.getDetailsName().size()]);
//        Object[] values = stream(sas).toArray();
//        preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, orderDetails.getOrder_name());
//        preparedStatement.setArray(2, );
//    }
}

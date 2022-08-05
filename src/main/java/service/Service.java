package service;

import Entity.*;
import Repository.Download;
import com.thoughtworks.qdox.model.expression.Or;
import controller.LRUCacheImp;
import org.apache.openejb.config.CleanEnvEntries;
import org.apache.openjpa.jdbc.sql.StoredProcedure;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class Service {
    //    @EJB
//    LRUCacheImp lru = new LRUCacheImp();
    @EJB
    private Download download;

    @EJB
    private LogicOfSCMS logicOfSCMS;

    @EJB
    private LRUCacheImp lruCacheImp;


    public List<Client> getClients() throws SQLException {
        return this.download.GetClients();
    }

    public Client getClientByID(int id) throws SQLException {
        return this.lruCacheImp.getFromCache(id);
    }

    public String createClient(Client client) throws SQLException {
        return this.download.CreateClient(client);
    }

    public String deleteClient(Integer id) throws SQLException {
        return this.download.DeleteClient(id);
    }

    public Client updateClient(Client client) throws SQLException {
        return this.download.UpdateClient(client);
    }

    public ArrayList<Client_Order> getClientOrders(Integer id) throws SQLException {
        return this.download.GetClientOrdersId(id);
    }

    public String deleteClientOrders(Integer id) throws SQLException {
        return this.download.DeleteClientOrders(id);
    }

    public ArrayList<Container> getContainer() throws SQLException {
        return this.download.getContainers();
    }

    public String buyFromDealer(String detailname, int quantity) throws SQLException {
        return this.logicOfSCMS.buyFromDealer(detailname, quantity);
    }

    public String buyFromDilerWithId(Integer id, Integer quantity) throws SQLException {
        return logicOfSCMS.buyFromDealerWId(id, quantity);
    }

    public String getMoneyOfCompany() throws SQLException {
        ArrayList<Order> orders = this.download.getOrders();
        double money = 0;
        for (int i = 0; i < orders.size(); i++) {
            money = money + orders.get(i).getOrderPrice();
        }
        return "You have earned - " + money + "tg.";
    }

    public String InsetNewMenu(String s) throws SQLException {
        return this.download.InsetNewMenu(s);
    }

    public String deleteMenu(String s) throws SQLException {
        return this.download.DeleteMenu(s);
    }


    public List<Menu> getMenu() throws SQLException {
        return this.download.GetMenu();
    }

    public String toOrder(Integer id, String s) throws SQLException {
        return this.logicOfSCMS.ToOrder(id, s);
    }

    public ArrayList<Order> getOrder(Integer id) throws SQLException {
        ArrayList<Order> orders = this.download.getOrders();
        ArrayList<Order> toReturn = new ArrayList<>();
        ArrayList<Client_Order> client_orders = this.download.GetClientOrdersId(id);
        for (int i = 0; i < client_orders.size(); i++) {
            for (int j = 0; j < orders.size(); j++) {
                if (client_orders.get(i).getOrder_id() == orders.get(j).getOrderId()) {
                    toReturn.add(orders.get(j));
                } else continue;
            }
        }
        return toReturn;
    }

    public String addClientMoney(Integer id, double money) throws SQLException {
        ArrayList<Client> clients = this.download.GetClients();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientId() == id) {
                Client cl = new Client();
                cl.setMoney(money + clients.get(i).getMoney());
                cl.setClientId(clients.get(i).getClientId());
                cl.setName(clients.get(i).getName());
                cl.setSurename(clients.get(i).getSurename());

                this.download.UpdateClient(cl);
                return "you added " + money + "tg. to your balance";
            }
        }
        return "Not complete";
    }

    public ArrayList<Dealer> viewDealers() throws SQLException {
        ArrayList<Dealer> dealers = this.download.getDealer();
        return dealers;
    }

    public Dealer viewDealerById(Integer id) throws SQLException {
        ArrayList<Dealer> dealers = this.download.getDealer();
        Dealer dealer = new Dealer();
        for (int i = 0; i < dealers.size(); i++) {
            if (id == dealers.get(i).getDealerId()) {
                dealer = dealers.get(i);
            }
        }
        return dealer;
    }

    public Dealer createDealer(Dealer dealer) throws SQLException {
        return this.download.CreateDealer(dealer);
    }

    public String addProductDealer(Integer id, Integer quantity) throws SQLException {
        ArrayList<Dealer> dealers = this.download.getDealer();
        Dealer dealer = new Dealer();
        for (int i = 0; i < dealers.size(); i++) {
            if (dealers.get(i).getDealerId() == id) {
                dealer.setDealerId(dealers.get(i).getDealerId());
                dealer.setProductName(dealers.get(i).getProductName());
                dealer.setPrice(dealers.get(i).getPrice());
                dealer.setQuantity(dealers.get(i).getQuantity());
            }
        }
        return this.download.AddProductForDealer(id, quantity, dealer);
    }

    public String editPriceDealer(Integer id, double price) throws SQLException {
        return this.download.EditPriceDealer(id, price);
    }
}

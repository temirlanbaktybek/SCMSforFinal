package service;


import Entity.*;
import Repository.Download;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class LogicOfSCMS {
    @EJB
    private Download download;

    ArrayList<Client> clients = new ArrayList<>();

    public String ToOrder(Integer c_id, String p_name) throws SQLException {
        clients = this.download.GetClients();
        ArrayList<Order> orders = this.download.getOrders();
        ArrayList<OrderDetails> orderDetails = this.download.getOrderDetils();
        ArrayList<Container> containers = this.download.getContainers();
        ArrayList<Client> clients = this.download.GetClients();

        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getOrder_name().equals(p_name)) {
                int num = 0;
                List<String> detailname = new ArrayList<>();
                for (int l = 0; l < orderDetails.get(i).getDetailsName().size(); l++) {
                    for (int j = 0; j < containers.size(); j++) {
                        if (orderDetails.get(i).getDetailsName().get(l).equals(containers.get(j).getProduct()) && containers.get(j).getQuantityProduct() != 0) {
                            num++;
                            detailname.add(orderDetails.get(i).getDetailsName().get(l));
                            System.out.println(orderDetails.get(i).getDetailsName().get(l));
                            if (num == orderDetails.get(i).getDetailsName().size()) {
                                System.out.println("True");
                                for (int g = 0; g < detailname.size(); g++) {
                                    String ddd = detailname.get(g);
                                    switch (ddd) {
                                        case "wood":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 15);
                                            break;
                                        case "screw":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 20);
                                            break;
                                        case "glue":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 8);
                                            break;
                                        case "wheel":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 3);
                                            break;
                                        case "pillow":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 4);
                                            break;
                                        case "handle":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "iron":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "mirror":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "mattress":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "plastic":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "spring":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 1);
                                            break;
                                        case "chipboard":
                                            this.download.minusProduct(orderDetails.get(i).getDetailsName().get(l), containers.get(j).getQuantityProduct() - 5);
                                            break;
                                    }
                                }
                                String text = "You have ordered " + orderDetails.get(i).getOrder_name() + " price: " + orderDetails.get(i).getPrice();
                                for (int z = 0; z < clients.size(); z++) {
                                    if (c_id == clients.get(z).getClientId()) {
                                        System.out.println("MINUS MONEY");
                                        Client cl = new Client();
                                        cl.setName(clients.get(z).getName());
                                        cl.setSurename(clients.get(z).getSurename());
                                        cl.setClientId(clients.get(z).getClientId());
                                        double sum = clients.get(z).getMoney();
                                        sum = sum - orderDetails.get(z).getPrice();
                                        cl.setMoney(sum);
                                        if (sum <= 0) {
                                            return "You dont have enough money, on your account ";
                                        } else {
                                            this.download.UpdateClient(cl);
                                            this.download.CreateOrder(c_id, p_name, orders, orderDetails.get(i).getPrice());
                                            return text;
                                        }
                                    }
                                }

                            }

                        } else if (orderDetails.get(i).getDetailsName().get(l).equals(containers.get(j).getProduct()) && containers.get(j).getQuantityProduct() == 0) {
                            System.out.println("Not complete ");

                            return "Not enough material for Product come later";
                        }
                    }
                }


            } else continue;
        }

        return "in Process...";
    }


    public String buyFromDealer(String detail_name, Integer quantity) throws SQLException {
        ArrayList<Dealer> dealers = this.download.getDealer();
        ArrayList<Container> containers = this.download.getContainers();
        for (int i = 0; i < dealers.size(); i++) {
            System.out.println(dealers.get(i).getProductName());
            if (detail_name.equals(dealers.get(i).getProductName())) {
                System.out.println(detail_name);
                if (quantity <= dealers.get(i).getQuantity()) {
                    Dealer del = new Dealer();
                    del.setDealerId(dealers.get(i).getDealerId());
                    del.setProductName(dealers.get(i).getProductName());
                    del.setQuantity(dealers.get(i).getQuantity() - quantity);
                    del.setPrice(dealers.get(i).getPrice());
                    this.download.UpdateDealerProduct(del);

                    for (int j = 0; j < containers.size(); j++) {
                        if (detail_name.equals(containers.get(j).getProduct())) {
                            Container con = new Container();
                            con.setProduct(containers.get(j).getProduct());
                            con.setQuantityProduct(containers.get(j).getQuantityProduct() + quantity);
                            this.download.UpdateContainerProduct(con);
                        }
                    }

                    return "The transaction was successful cost:" + quantity * dealers.get(i).getPrice() + " for - " + quantity + " th. ";
                }
            }
        }
        return "NO such product";
    }


    public String buyFromDealerWId(Integer id, Integer quantity) throws SQLException {
        ArrayList<Dealer> dealers = this.download.getDealer();
        ArrayList<Container> containers = this.download.getContainers();
        int n = dealers.size();

        for (int i = 0; i < n; i++) {
            System.out.println(n);
            System.out.println(i);
            if (dealers.get(i).getDealerId() == id) {
                if (dealers.get(i).getQuantity() >= quantity) {

                    for (int j = 0; j < containers.size(); j++) {
                        if (containers.get(j).getProduct().equals(dealers.get(i).getProductName())) {

                            Dealer del = new Dealer();
                            del.setDealerId(dealers.get(i).getDealerId());
                            del.setProductName(dealers.get(i).getProductName());
                            del.setQuantity(dealers.get(i).getQuantity() - quantity);
                            del.setPrice(dealers.get(i).getPrice());
                            this.download.UpdateDealerProduct(del);

                            Container con = new Container();
                            con.setProduct(containers.get(j).getProduct());
                            con.setQuantityProduct(containers.get(j).getQuantityProduct() + quantity);
                            this.download.UpdateContainerProduct(con);

                            return "The transaction was successful cost:" + quantity * dealers.get(i).getPrice() + " for - " + quantity + " th. ";
                        }
                    }
                }
            }
        }
        return "Not successful transaction";
    }
}

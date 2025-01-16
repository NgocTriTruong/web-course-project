package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

public class UserStats {
    private String fullName;
    private String phone;
    private int totalOrders;
    private int totalProductsOrdered;
    private double totalAmountToPay;

    public UserStats(String fullName, String phone, int totalOrders, int totalProductsOrdered, double totalAmountToPay) {
        this.fullName = fullName;
        this.phone = phone;
        this.totalOrders = totalOrders;
        this.totalProductsOrdered = totalProductsOrdered;
        this.totalAmountToPay = totalAmountToPay;
    }

    public UserStats() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalProductsOrdered() {
        return totalProductsOrdered;
    }

    public void setTotalProductsOrdered(int totalProductsOrdered) {
        this.totalProductsOrdered = totalProductsOrdered;
    }

    public double getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public void setTotalAmountToPay(double totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
    }

    @Override
    public String toString() {
        return "UserStats{" +
                "fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", totalOrders=" + totalOrders +
                ", totalProductsOrdered=" + totalProductsOrdered +
                ", totalAmountToPay=" + totalAmountToPay +
                '}';
    }
}

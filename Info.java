public class Info {
    private int b_TicketQuantity;
    private int e_TicketQuantity;
    private String coupon;
    private double totalPrice;

    private final int ECONOMY_PRICE = 10000;
    private final int BUSINESS_PRICE = 25000;

    public Info(String b_TicketQuantity, String coupon, String e_TicketQuantity) {
        try {
            this.b_TicketQuantity = Integer.parseInt(b_TicketQuantity);
        } catch (NumberFormatException e) {
            this.b_TicketQuantity = 0;
        }

        try {
            this.e_TicketQuantity = Integer.parseInt(e_TicketQuantity);
        } catch (NumberFormatException e) {
            this.e_TicketQuantity = 0;
        }

        this.coupon = coupon;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double price = (e_TicketQuantity * ECONOMY_PRICE) + (b_TicketQuantity * BUSINESS_PRICE);


        if (coupon != null && !coupon.isEmpty()) {
            if (coupon.equalsIgnoreCase("Sabuj")) {
                price = price * 0.5; // 50% dis
            }
			 
			else if (coupon.equalsIgnoreCase("adnan")) {
					price = price * 0.2; // 80% dis
				}
		 else if (coupon.equalsIgnoreCase("anik")) {
					price = price * 0.9; // 10% dis
				}


        }

        this.totalPrice = price;
    }

    public int getB_TicketQuantity() {
        return b_TicketQuantity;
    }

    public int getE_TicketQuantity() {
        return e_TicketQuantity;
    }

    public String getCoupon() {
        return coupon;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

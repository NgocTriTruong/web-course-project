package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;


import com.vnpay.common.Config;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Payment;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PaymentService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

@WebServlet(name = "SuccessVnpay", value = "/success-vnpay")
public class SuccessVnpay extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            //Begin process return from VNPAY
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType"))
            {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash"))
            {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash))
            {

                Payment payment = new Payment();
                PaymentService paymentService = new PaymentService();

                Order order = new Order();
                OrderService orderService = new OrderService();
                String oderId = request.getParameter("vnp_TxnRef");
                order.setId(Integer.parseInt(oderId));


                boolean checkOrderId = true; // vnp_TxnRef exists in your database
                boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)


                if(checkOrderId)
                {
                    if(checkAmount)
                    {
                        if (checkOrderStatus)
                        {
                            boolean transSuccess = false;
                            if ("00".equals(request.getParameter("vnp_ResponseCode")))
                            {

                                //Here Code update PaymnentStatus = 1 into your Database
                                order.setStatus(2);
                                payment.setStatus(1);
                                transSuccess = true;
                            }
                            else
                            {

                                // Here Code update PaymnentStatus = 2 into your Database
                                order.setStatus(1);
                            }
                            orderService.updateOrderStatus(Integer.parseInt(oderId), order.getStatus());
                            paymentService.updatePaymentStatus(Integer.parseInt(oderId), payment.getStatus());
                            request.setAttribute("transResult", transSuccess);

                            System.out.println(transSuccess);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            out.print("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                            response.sendRedirect(request.getContextPath() + "/views/web/order/order-success-vnpay.jsp?transResult=" + transSuccess);
                        }
                        else
                        {

                            out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
                        }
                    }
                    else
                    {
                        out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
                    }
                }
                else
                {
                    out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
                }

                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
                }

            }

            else
            {
                out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
            }
        }
        catch(Exception e)
        {
            out.print("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
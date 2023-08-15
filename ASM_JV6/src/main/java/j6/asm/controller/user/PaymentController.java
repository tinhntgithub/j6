package j6.asm.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import j6.asm.config.VNPayConfig;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Address;
import j6.asm.entity.Cart;
import j6.asm.entity.OrderDetails;
import j6.asm.entity.Orders;
import j6.asm.entity.ProductColor;
import j6.asm.entity.Products;
import j6.asm.entity.Sale;
import j6.asm.entity.Status;
import j6.asm.model.Payment;
import j6.asm.service.AddressService;
import j6.asm.service.CartService;
import j6.asm.service.OrderDetailsService;
import j6.asm.service.OrdersService;
import j6.asm.service.ProductColorService;
import j6.asm.service.SaleService;
import j6.asm.service.SessionService;
import j6.asm.service.StatusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

@Controller
public class PaymentController {
	
	@Autowired  
	SessionService session;
	@Autowired
	StatusService statusService;
	@Autowired
	CartService cartService;
	@Autowired
	AddressService addressDAO;
	@Autowired
	SaleService saleService;
	@Autowired
	OrdersService orderService;
	@Autowired
	OrderDetailsService ordersService;
	@Autowired
	ProductsDAO productsDAO;
	@Autowired
	ProductColorService productColorService;

	VNPayConfig Config;

	@GetMapping("/vnpay_pay.html")
	protected String doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode node = mapper.createObjectNode();
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
//		req.getParameter("vnp_OrderInfo")
		String vnp_OrderInfo = "demo" ;
//		 req.getParameter("ordertype")
		String orderType ="other";
		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);
		String vnp_TmnCode = Config.vnp_TmnCode;
//        Integer.parseInt(req.getParameter("amount"))
		String total = req.getParameter("total");
		if(total==null) {
			return "redirect:/checkout.html?err=bug";
		}
		try {
			Integer price =  Integer.parseInt(total);
		} catch (NumberFormatException e) {
			return "redirect:/checkout.html?err=bug";
		}
		long amount =  Long.parseLong(req.getParameter("total")) * 100;
		Map vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		String bank_code = req.getParameter("bankcode");
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bank_code);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = req.getParameter("language");
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		// Add Params of 2.1.0 Version
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		// Billing
//		vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
//		vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
//        (req.getParameter("txt_billing_fullname")).trim()
		String fullName = (req.getParameter("txt_billing_fullname")).trim();
		if (fullName != null && !fullName.isEmpty()) {
			int idx = fullName.indexOf(' ');
			String firstName = fullName.substring(0, idx);
			String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
			vnp_Params.put("vnp_Bill_FirstName", firstName);
			vnp_Params.put("vnp_Bill_LastName", lastName);

		}
//		vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
//		vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
//		vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
//		if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//			vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//		}
		// Invoice
//		vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
//		vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
//		vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
//		vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
//		vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
//		vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
//		vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//		node.put("code", "00");
//		node.put("message", "success");
//		node.put("url", paymentUrl);
		System.out.println(paymentUrl);
		
		return "redirect:"+paymentUrl;
	}

	// vui lòng tham khảo thêm tại code demo

//    public String showPaymentForm(@RequestParam("total") String total, Model model) {
//        model.addAttribute("total", total);
//        // Xử lý logic khác nếu cần
//        return "/user/order/vnpay_pay";
//    }

	@GetMapping("/vnpay_return")
	public String returnPayment(HttpServletRequest req, HttpServletResponse resp) {
		String code = req.getParameter("vnp_ResponseCode");
		if(code.equals("24")) {
			return "redirect:/checkout.html?mess=orderCancel";
		}else if (code.equals("00")) {
			session.set("hashData", req.getParameter("vnp_SecureHash"));
			return "redirect:/checkout.html?mess=orderSuccess";
		}
		return "redirect:/checkout.html?err=bug";
	}

}
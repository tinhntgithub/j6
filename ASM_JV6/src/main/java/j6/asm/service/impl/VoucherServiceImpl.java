package j6.asm.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import j6.asm.dao.SaleDAO;
import j6.asm.entity.Accounts;
import j6.asm.entity.Sale;
import j6.asm.service.SessionService;
import j6.asm.service.VoucherServive;

@Service
public class VoucherServiceImpl implements VoucherServive {

	@Autowired
	SaleDAO dao;
	@Autowired
	SessionService session;

	@Override
	public ResponseEntity<?> apply(String code) {
		System.out.println("NGƯỜI DÙNG NHẬP MÃ: " + code);
		if (code.equals("null") || code == null) {
			System.err.println("CHƯA NHẬP CODE MÀ BẤM CHECK MÃ À, T MÃ VÔ CÁI ĐẦU MẦY");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		System.out.println("BÂY GIỜ LÀ: " + cld.getTime().getTime());
		Accounts account = session.get("account");
		String fullname = account.getFullname();
		Sale coupon = dao.findByCode(code);
		if (coupon != null) {
			System.out.println("MÃ KHUYẾN MÃI: " + coupon.getCode());
			if (coupon.getEndDate().getTime() < cld.getTime().getTime()) {
				System.out.println("MÃ KHUYẾN MÃI ĐÃ HẾT HẠN, NHẬP CÁI NỮA T KHOÁ TÀI KHOẢN................");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			if (coupon.getAmount() <= 0) {
				System.out.println("MÃ KHUYẾN MÃI ĐÃ HẾT SỐ LƯỢNG NHẬP, NHẬP CÁI NỮA T KHOÁ TÀI KHOẢN................");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			System.out.println("NHẬP ĐÚNG MÃ KHUYẾN MÃI................");
			System.out.println("Giảm giá: " + coupon.getValue());
			return ResponseEntity.ok(coupon);
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}

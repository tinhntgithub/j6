package j6.asm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    // Định nghĩa các thông tin cần thiết cho đơn hàng
    private String productName; // Tên sản phẩm
    private int productPrice; // Giá sản phẩm
    private int quantity; // Số lượng sản phẩm
}
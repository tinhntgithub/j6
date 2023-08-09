package j6.asm.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import j6.asm.dao.ProductImgDAO;
import j6.asm.dao.ProductsDAO;
import j6.asm.entity.ProductImg;
import j6.asm.entity.Products;

@CrossOrigin("*")
@RestController
public class PdImgRestController {
    @Autowired
    ProductsDAO productsdao;

    @Autowired
    ProductImgDAO pdimgdao;

    @GetMapping("/rest/pdimg/{productid}")
    public ResponseEntity<List<ProductImg>> getImgbyProduct (@PathVariable("productid") Integer pdid){
        return ResponseEntity.ok(pdimgdao.findByPdid(pdid));
    }

    @PostMapping("/rest/pdimg/{productid}")
    public ResponseEntity<List<String>> post(@PathVariable("productid") Integer pdid, @RequestBody List<String> data) {
        //xóa tất cả ảnh cũ
        List<ProductImg> list = pdimgdao.findByPdid(pdid);
        if(list != null){
            for (ProductImg pdimg : list) {
                pdimgdao.delete(pdimg);
            }
        }
        
        Products pr = productsdao.getById(pdid);
        for (String string : data) {
            ProductImg pdimg = new ProductImg();
            pdimg.setName(string);
            pdimg.setImgPro(pr);
            pdimgdao.save(pdimg);
        }

        return ResponseEntity.ok(data);
    }


}

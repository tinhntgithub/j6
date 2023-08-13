package j6.asm.controller.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import j6.asm.service.AccountsService;
import j6.asm.service.FavoritesService;
import j6.asm.service.ProductsService;

@CrossOrigin("*")
@RestController
public class ReportRestController {

    @Autowired
    AccountsService AccountsService;
    @Autowired
    ProductsService productsSer;
    @Autowired
    FavoritesService favService;

    @GetMapping("/rest/reportCustomer")
    public List<Object[]> reportCustomer(@RequestParam("start") Date s, @RequestParam("end") Date e) {

        return AccountsService.getPurchaseDataByYearRange(s, e);
    }

   @GetMapping("/rest/reportPrintCustomer/{username}")
public List<Object[]> reportPrintCustomer(@PathVariable String username) {
    // Chuyển đổi chuỗi ngày thành đối tượng java.sql.Date
    // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    // Date createDate = null;
    // try {
    //     createDate = new java.sql.Date(dateFormat.parse(date).getTime());
    // } catch (ParseException e) {
    //     e.printStackTrace();
    // }

    return AccountsService.getReportPrintCustomer(username);
}


    @GetMapping("/rest/reportCustomerAll")
    public List<Object[]> getResultList() {

        return AccountsService.getResultList();
    }

    @GetMapping("/rest/reportOrder")
    public List<Object[]> reportOrder() {

        return productsSer.findProductReport();
    }

    @GetMapping("/rest/reportFavorites")
    public List<Object[]> reportFavorites() {

        return favService.listReportFavorites();
    }
}

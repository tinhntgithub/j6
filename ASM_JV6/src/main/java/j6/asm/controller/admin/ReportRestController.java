package j6.asm.controller.admin;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/rest/reportCustomerAll")
    public List<Object[]> reportCustomerAll() {

        return AccountsService.getReportAllCustomer();
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

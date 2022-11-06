package com.example.moscow_price.cotrollers;

import com.example.moscow_price.DTO.ApartmentDTO;
import com.example.moscow_price.DTO.ApartmentWithPriceDTO;
import com.example.moscow_price.Entity.ApartmentEntity;
import com.example.moscow_price.excel.ExcelPOIHelper;
import com.example.moscow_price.excel.ExcelPOIHelperWithPrices;
import com.example.moscow_price.servis.ApartmentService;
import com.example.moscow_price.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController("/")
public class ExcelController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private ExcelPOIHelper excelPOIHelper;

    @Autowired
    private ExcelPOIHelperWithPrices excelPOIHelperWithPrices;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public String testPrint(){
        return "Hello!";
    }

    @PostMapping("excel")
    public List<ApartmentDTO> uploadExcel(@RequestParam("file") MultipartFile file,
                                          RedirectAttributes redirectAttributes) throws JAXBException, IOException {

        storageService.store(file);
        System.out.println(file);
        String originalFilename = file.getOriginalFilename();
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + originalFilename + "!");
//        unmarshall(originalFilename);
        List<ApartmentDTO> apartmentDTOS = excelPOIHelper.readExcel(originalFilename);
        return apartmentDTOS;
    }

    @PostMapping("excelToDB")
    public List<ApartmentEntity> uploadExcelToDB(@RequestParam("file") MultipartFile file,
                                                 RedirectAttributes redirectAttributes) throws JAXBException, IOException {

        storageService.store(file);
        System.out.println(file);
        String originalFilename = file.getOriginalFilename();
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + originalFilename + "!");
//        unmarshall(originalFilename);
        List<ApartmentWithPriceDTO> apartmentDTOS = excelPOIHelperWithPrices.readExcel(originalFilename);

        List<ApartmentEntity> apartmentEntities = apartmentService.storeToDB(apartmentDTOS);
        return apartmentEntities;
    }

    public ApartmentDTO unmarshall(String originalFilename) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(ApartmentDTO.class);
        return (ApartmentDTO) context.createUnmarshaller()
                .unmarshal(new FileReader("upload-dir/" + originalFilename));
    }
}

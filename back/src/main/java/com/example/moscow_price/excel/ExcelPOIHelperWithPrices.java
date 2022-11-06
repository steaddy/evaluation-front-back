package com.example.moscow_price.excel;

import com.example.moscow_price.DTO.ApartmentWithPriceDTO;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelPOIHelperWithPrices {

    public List<ApartmentWithPriceDTO> readExcel(String fileName){
        String data = "";
        if (fileName.endsWith(".xls")) {
            return readXls(fileName);
        } else if (fileName.endsWith(".xlsx")) {
            return readXlsx(fileName);
        }
        return new ArrayList<>();
    }

    public List<ApartmentWithPriceDTO> readXlsx(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File("upload-dir/" + fileName));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            return createList(sheet.iterator(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<ApartmentWithPriceDTO> readXls(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File("upload-dir/" + fileName));

            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            return createList(sheet.iterator(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<ApartmentWithPriceDTO> createList(Iterator<Row> sheet, FileInputStream file) throws IOException {

        List<ApartmentWithPriceDTO> ApartmentWithPriceDTOList = new ArrayList<>();

        Iterator<Row> rowIterator = sheet;
        int rowNumber = 0;
        int cellNumber = 0;
        while (rowIterator.hasNext()) {
            if (rowNumber == 0){
                rowNumber++;
                rowIterator.next();
                continue;
            }
            ApartmentWithPriceDTO ApartmentWithPriceDTO = new ApartmentWithPriceDTO();

            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                //Check the cell type and format accordingly
                switch (cellNumber){
                    case 0:
                        ApartmentWithPriceDTO.setLocation(cell.getStringCellValue());
                        break;
                    case 1:
                        if (cell.getCellType() == CellType.NUMERIC){
                            ApartmentWithPriceDTO.setNumberOfRooms(String.valueOf((int) cell.getNumericCellValue()));
                        } else if (cell.getCellType() == CellType.STRING) {
                            ApartmentWithPriceDTO.setNumberOfRooms(cell.getStringCellValue());
                        }

                        break;
                    case 2:
                        ApartmentWithPriceDTO.setSegment(cell.getStringCellValue());
                        break;
                    case 3:
                        ApartmentWithPriceDTO.setNumberOfFloors((int) cell.getNumericCellValue());
                        break;
                    case 4:
                        ApartmentWithPriceDTO.setWallMaterial(cell.getStringCellValue());
                        break;
                    case 5:
                        ApartmentWithPriceDTO.setLocationFloor((int) cell.getNumericCellValue());
                        break;
                    case 6:
                        ApartmentWithPriceDTO.setApartmentArea(cell.getNumericCellValue());
                        break;
                    case 7:
                        ApartmentWithPriceDTO.setKitchenArea(cell.getNumericCellValue());
                        break;
                    case 8:
                        ApartmentWithPriceDTO.setBalcony(cell.getStringCellValue());
                        break;
                    case 9:
                        ApartmentWithPriceDTO.setMetroDistance((int) cell.getNumericCellValue());
                        break;
                    case 10:
                    ApartmentWithPriceDTO.setCondition(cell.getStringCellValue());
                        break;
                    case 11:
                    ApartmentWithPriceDTO.setPrice((int) cell.getNumericCellValue());
                        break;
                }
                cellNumber++;
            }
            ApartmentWithPriceDTOList.add(ApartmentWithPriceDTO);

            rowNumber++;
            cellNumber = 0;
        }
        file.close();
        return ApartmentWithPriceDTOList;
    }

}
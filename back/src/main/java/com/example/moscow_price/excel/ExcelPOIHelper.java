package com.example.moscow_price.excel;

import com.example.moscow_price.DTO.ApartmentDTO;
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
public class ExcelPOIHelper {

    public List<ApartmentDTO> readExcel(String fileName){
        String data = "";
        if (fileName.endsWith(".xls")) {
            return readXls(fileName);
        } else if (fileName.endsWith(".xlsx")) {
            return readXlsx(fileName);
        }
        return new ArrayList<>();
    }

    public List<ApartmentDTO> readXlsx(String fileName) {
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

    public List<ApartmentDTO> readXls(String fileName) {
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

    private List<ApartmentDTO> createList(Iterator<Row> sheet, FileInputStream file) throws IOException {

        List<ApartmentDTO> apartmentDTOList = new ArrayList<>();

        Iterator<Row> rowIterator = sheet;
        int rowNumber = 0;
        int cellNumber = 0;
        while (rowIterator.hasNext()) {
            if (rowNumber == 0){
                rowNumber++;
                rowIterator.next();
                continue;
            }
            ApartmentDTO apartmentDTO = new ApartmentDTO();

            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                //Check the cell type and format accordingly
                switch (cellNumber){
                    case 0:
                        apartmentDTO.setLocation(cell.getStringCellValue());
                        break;
                    case 1:
                        if (cell.getCellType() == CellType.NUMERIC){
                            apartmentDTO.setNumberOfRooms(String.valueOf((int) cell.getNumericCellValue()));
                        } else if (cell.getCellType() == CellType.STRING) {
                            apartmentDTO.setNumberOfRooms(cell.getStringCellValue());
                        }

                        break;
                    case 2:
                        apartmentDTO.setSegment(cell.getStringCellValue());
                        break;
                    case 3:
                        apartmentDTO.setNumberOfFloors((int) cell.getNumericCellValue());
                        break;
                    case 4:
                        apartmentDTO.setWallMaterial(cell.getStringCellValue());
                        break;
                    case 5:
                        apartmentDTO.setLocationFloor((int) cell.getNumericCellValue());
                        break;
                    case 6:
                        apartmentDTO.setApartmentArea(cell.getNumericCellValue());
                        break;
                    case 7:
                        apartmentDTO.setKitchenArea(cell.getNumericCellValue());
                        break;
                    case 8:
                        apartmentDTO.setBalcony(cell.getStringCellValue());
                        break;
                    case 9:
                        apartmentDTO.setMetroDistance((int) cell.getNumericCellValue());
                        break;
                    case 10:
                    apartmentDTO.setCondition(cell.getStringCellValue());
                        break;
                }
                cellNumber++;
            }
            apartmentDTOList.add(apartmentDTO);

            rowNumber++;
            cellNumber = 0;
        }
        file.close();
        return apartmentDTOList;
    }

//    private void getValueByType(Cell cell) {
//        switch (cell.getCellType()) {
//            case NUMERIC:
//                System.out.print(cell.getNumericCellValue());
//                break;
//            case STRING:
//                System.out.print(cell.getStringCellValue());
//                break;
//        }
//    }

//    public Map<Integer, List<Apartment>> readExcel(String fileLocation) throws IOException {
//
//        Map<Integer, List<Apartment>> data = new HashMap<>();
//        FileInputStream fis = new FileInputStream(new File(fileLocation));
//
//        if (fileLocation.endsWith(".xls")) {
//            data = readHSSFWorkbook(fis);
//        } else if (fileLocation.endsWith(".xlsx")) {
//            data = readXSSFWorkbook(fis);
//        }
//
//        int maxNrCols = data.values().stream()
//                .mapToInt(List::size)
//                .max()
//                .orElse(0);
//
//        data.values().stream()
//                .filter(ls -> ls.size() < maxNrCols)
//                .forEach(ls -> {
//                    IntStream.range(ls.size(), maxNrCols)
//                            .forEach(i -> ls.add(new Apartment()));
//                });
//
//        return data;
//    }
//
//    private String readCellContent(Cell cell) {
//        String content;
//        switch (cell.getCellTypeEnum()) {
//            case STRING:
//                content = cell.getStringCellValue();
//                break;
//            case NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    content = cell.getDateCellValue() + "";
//                } else {
//                    content = cell.getNumericCellValue() + "";
//                }
//                break;
//            case BOOLEAN:
//                content = cell.getBooleanCellValue() + "";
//                break;
//            case FORMULA:
//                content = cell.getCellFormula() + "";
//                break;
//            default:
//                content = "";
//        }
//        return content;
//    }
//
//    private Map<Integer, List<Apartment>> readHSSFWorkbook(FileInputStream fis) throws IOException {
//        Map<Integer, List<Apartment>> data = new HashMap<>();
//        HSSFWorkbook workbook = null;
//        try {
//            workbook = new HSSFWorkbook(fis);
//
//            HSSFSheet sheet = workbook.getSheetAt(0);
//            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
//                HSSFRow row = sheet.getRow(i);
//                data.put(i, new ArrayList<>());
//                if (row != null) {
//                    for (int j = 0; j < row.getLastCellNum(); j++) {
//                        HSSFCell cell = row.getCell(j);
//                        if (cell != null) {
//                            HSSFCellStyle cellStyle = cell.getCellStyle();
//
//                            Apartment apartment = new Apartment();
//
//                            HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
//                            if (bgColor != null) {
//                                short[] rgbColor = bgColor.getTriplet();
//                                apartment.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
//                            }
//                            HSSFFont font = cell.getCellStyle()
//                                    .getFont(workbook);
//                            apartment.setTextSize(font.getFontHeightInPoints() + "");
//                            if (font.getBold()) {
//                                apartment.setTextWeight("bold");
//                            }
//                            HSSFColor textColor = font.getHSSFColor(workbook);
//                            if (textColor != null) {
//                                short[] rgbColor = textColor.getTriplet();
//                                apartment.setTextColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
//                            }
//                            apartment.setContent(readCellContent(cell));
//                            data.get(i)
//                                    .add(apartment);
//                        } else {
//                            data.get(i)
//                                    .add(new Apartment(""));
//                        }
//                    }
//                }
//            }
//        } finally {
//            if (workbook != null) {
//                workbook.close();
//            }
//        }
//        return data;
//    }
//
//    private Map<Integer, List<Apartment>> readXSSFWorkbook(FileInputStream fis) throws IOException {
//        XSSFWorkbook workbook = null;
//        Map<Integer, List<Apartment>> data = new HashMap<>();
//        try {
//
//            workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
//                XSSFRow row = sheet.getRow(i);
//                data.put(i, new ArrayList<>());
//                if (row != null) {
//                    for (int j = 0; j < row.getLastCellNum(); j++) {
//                        XSSFCell cell = row.getCell(j);
//                        if (cell != null) {
//                            XSSFCellStyle cellStyle = cell.getCellStyle();
//
//                            Apartment myCell = new Apartment();
//                            XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
//                            if (bgColor != null) {
//                                byte[] rgbColor = bgColor.getRGB();
//                                myCell.setBgColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
//                            }
//                            XSSFFont font = cellStyle.getFont();
//                            myCell.setTextSize(font.getFontHeightInPoints() + "");
//                            if (font.getBold()) {
//                                myCell.setTextWeight("bold");
//                            }
//                            XSSFColor textColor = font.getXSSFColor();
//                            if (textColor != null) {
//                                byte[] rgbColor = textColor.getRGB();
//                                myCell.setTextColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
//                            }
//                            myCell.setContent(readCellContent(cell));
//                            data.get(i)
//                                    .add(myCell);
//                        } else {
//                            data.get(i)
//                                    .add(new Apartment(""));
//                        }
//                    }
//                }
//            }
//        } finally {
//            if (workbook != null) {
//                workbook.close();
//            }
//        }
//        return data;
//    }

}
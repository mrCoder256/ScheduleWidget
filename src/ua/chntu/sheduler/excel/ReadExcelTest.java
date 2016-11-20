package ua.chntu.sheduler.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelTest {
    private FileInputStream excelFile;
    private HSSFWorkbook excelWBook;
	private int numOfCellsToSkip;
    
    public ReadExcelTest(String excelFileName) throws Exception {
    	try {
	        excelFile = new FileInputStream(new File(excelFileName));
	        excelWBook = new HSSFWorkbook(new POIFSFileSystem(excelFile));
	    } catch (Exception e) {
	        throw (e);
	    }
    }
    
    @Override
	protected void finalize() throws Throwable {
    	if (excelFile != null) {
    		excelFile.close();
    	}
    	
		super.finalize();
	}

    public String getCellData(int sheetNumber, int rowNum, int colNum) throws Exception {
        try {
        	HSSFCell cell = excelWBook.getSheetAt(sheetNumber).getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return "";
        }
    }
    
    private int getLessonsNumberInDay(HSSFSheet sheet, int rowNumWhereToStart) {
    	Iterator<Row> rowIt = sheet.rowIterator();
    	// skip all useless rows and start from the row with the second lesson
    	for (int i = 0; i <= rowNumWhereToStart; i++) rowIt.next();
    	int num = 1;
    	
    	while (true) {
    		Iterator<Cell> cellIt = rowIt.next().cellIterator();
    		skipUnusefulCells(cellIt, numOfCellsToSkip);
    		Cell cell1 = cellIt.next();
    		Cell cell2 = cellIt.next();
    		if ( (cell1.getCellType() == Cell.CELL_TYPE_STRING) ||
    				(cell1.getCellType() == cell2.getCellType()) ) {
    			// if "ВТ", "СР" ... found or cell1 and cell2 are blank (end of week)
    			break;
    		}
    		num++;
    	}
    	
    	return num;
    }
    
    private int getEndOfWeek(HSSFSheet sheet, int rowNumWhereToStart) {
    	Iterator<Row> rowIt = sheet.rowIterator();
    	// skip all useless rows
    	for (int i = 0; i < rowNumWhereToStart; i++) rowIt.next();
    	int i = 0;
    	
    	while (true) {
    		Iterator<Cell> cellIt = rowIt.next().cellIterator();
    		skipUnusefulCells(cellIt, numOfCellsToSkip);
    		// check neighbor cells
    		if (cellIt.next().getCellType() == cellIt.next().getCellType()) break;
    		else i++;
    	}
    	
    	return i + rowNumWhereToStart;
    }
    
    private ArrayList<ArrayList<LessonTest>> getLessonsInDay(HSSFSheet sheet,
			int curRowNum, int lessonsNumInDay, int numOfGroups) {
    	// buffer for lessons of groups on a day  
    	ArrayList<ArrayList<LessonTest>> lessons = new ArrayList<ArrayList<LessonTest>>(
//    			Collections.nCopies(numOfGroups, new ArrayList<LessonTest>()));
    			numOfGroups);
		for (int i = 0; i < numOfGroups; i++) {
			lessons.add(new ArrayList<LessonTest>());
		}
 		
		// iterate through lessons
    	for (int numOflesson = 0; numOflesson < lessonsNumInDay; numOflesson++) {
    		Iterator<Cell> cellIt = sheet.getRow(curRowNum + numOflesson).cellIterator();
    		skipUnusefulCells(cellIt, numOfCellsToSkip); // skip empty cells,
    		cellIt.next(); // a cell with a day name
    		cellIt.next(); // and a cell with a number of lesson
    		
    		// read lessons for every group from one row
    		for (int groupIndex = 0; groupIndex < numOfGroups; groupIndex++) {
    			if (cellIt.hasNext()) {
        			Cell cell = cellIt.next();
        			String dataFromCell = (cell.getCellType() == Cell.CELL_TYPE_STRING) ?
        					cell.getStringCellValue() : ""; 
        			// add a lesson for the corresponding group
        			lessons.get(groupIndex).add(new LessonTest(dataFromCell));
        			
        			if (cellIt.hasNext()) cellIt.next();
    			}
    			else lessons.get(groupIndex).add(new LessonTest()); // if a cell is empty
    		}            		
    	}
    	
    	return lessons;
	}

	private ArrayList<GroupTest> getScheduleOfGroups(HSSFSheet sheet, int firstWeekStart) {
    	ArrayList<GroupTest> groups = new ArrayList<GroupTest>();
    	
    	Iterator<Cell> firstWeekStartCellIt = sheet.getRow(firstWeekStart).cellIterator();
    	// skip useless
    	skipUnusefulCells(firstWeekStartCellIt, numOfCellsToSkip); // empty cells
    	firstWeekStartCellIt.next(); // cell with the value "День тижня" 
    	firstWeekStartCellIt.next(); // cell with the value "№ пари"
    	// get names of presented groups
    	while (firstWeekStartCellIt.hasNext()) {
    		Cell groupNameCell = firstWeekStartCellIt.next();
    		if (groupNameCell.getCellType() == Cell.CELL_TYPE_BLANK) continue;
    		
    		groups.add(new GroupTest(groupNameCell.getStringCellValue()));
    	}    	
    	
    	int curRowNum = firstWeekStart + 1;
    	// even and odd weeks
    	for (int week = 0; week < 2; week++) {    		
    		for (int weekEnd = getEndOfWeek(sheet, curRowNum),
    				lessonsNumInDay = getLessonsNumberInDay(sheet, curRowNum);
    			 curRowNum != weekEnd;
    			 curRowNum += lessonsNumInDay,
    				lessonsNumInDay = getLessonsNumberInDay(sheet, curRowNum))
    		{
    			// get the day name: ПН, ВТ etc.
    	    	Iterator<Cell> cellIt = sheet.getRow(curRowNum).cellIterator();
    	    	skipUnusefulCells(cellIt, numOfCellsToSkip);
            	String dayName = cellIt.next().getStringCellValue();
            	cellIt.next();

            	// get a schedule for a certain day
        		ArrayList<ArrayList<LessonTest>> lessonsInDay =
        				getLessonsInDay(sheet, curRowNum, lessonsNumInDay, groups.size());

        		// add a schedule for a day for all groups
        		for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {
        			groups.get(groupIndex).addDay(dayName, lessonsInDay.get(groupIndex));
        		}
    		}
    		
    		curRowNum += 3; // skip useless rows
    	}

    	return groups;
    }
    
    private void skipUnusefulCells(Iterator<Cell> iter, int num) {
    	for (int i = 0; i < num && iter.hasNext(); i++) iter.next();
    }
    
    private void printSchedule(ArrayList<GroupTest> groups, String textFileName) {
		PrintWriter log = null;
		try {
			log = new PrintWriter(textFileName);
		} catch (FileNotFoundException e) {
			System.out.println("Can't create a file!");
			e.printStackTrace();
		}

		for (int i = 0; i < groups.size(); i++) {
			groups.get(i).printTo(log);
		}	
		log.close();
    }
    
	public void printScheduleOfCourse(int sheetNumber, String textFileName) {
    	int curRowNum = 0;
    	HSSFSheet sheet = excelWBook.getSheetAt(sheetNumber);
    	Iterator<Row> rowIt = sheet.rowIterator();

rows_iteration:
		for (; rowIt.hasNext(); curRowNum++) {
            Iterator<Cell> cellIt = rowIt.next().cellIterator();
            while (cellIt.hasNext()){
            	Cell cell = cellIt.next();
            	if ((cell.getCellType() == Cell.CELL_TYPE_STRING) &&
            			(cell.getStringCellValue().equalsIgnoreCase("№ пари"))) {
                	System.out.println("Назва потоку: \"" +
                			cellIt.next().getStringCellValue() + "\"\n");
                	break rows_iteration;
                }
            }
        }
    	
    	// get number of columns at the left side of sheet with empty cells
    	numOfCellsToSkip = 0;
    	for (Iterator<Cell> cellIt = sheet.getRow(curRowNum).cellIterator();
    			cellIt.hasNext(); ) {
    		if (cellIt.next().getCellType() == Cell.CELL_TYPE_BLANK) numOfCellsToSkip++;
    		else break;
    	}
    	
    	curRowNum++; // number of the row where the schedule starts    	
		printSchedule(getScheduleOfGroups(sheet, curRowNum), textFileName);
		
        System.out.println("\nThe excel file was parsed successfully! "
        		+ "The content was saved to " + textFileName + "\n");
    }

}

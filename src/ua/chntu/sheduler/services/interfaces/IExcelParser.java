package ua.chntu.sheduler.services.interfaces;

import java.io.FileInputStream;

public interface IExcelParser {
	
	void parse(FileInputStream excelFile);

}

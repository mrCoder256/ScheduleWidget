package ua.chntu.sheduler.services;

import java.io.FileInputStream;

interface IExcelParser {
	
	void parse(FileInputStream excelFile);

}

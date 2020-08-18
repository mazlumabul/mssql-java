package mssql_java;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.io.*;


public class main {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException
			{
			Connection baglanti=DataAccess.dbBaglanti(); 
			Statement ifade=baglanti.createStatement(); 
			/*
			1.	En az 5 tablodan oluşan bir veritabanı tasarlanacaktır.

			2.	Tasarlanan veritabanı MSSQL Database Server üzerinde SQL kullanılarak java kod ile oluşturulacaktır.

			3.	MSSQL Database Server üzerinde oluşturulan veritabanında SQL kullanılarak java kod ile tablolar oluşturulacaktır.

			4.	MSSQL Database Server üzerindeki veritabanının tablolarına SQL kullanılarak java kod ile kayıtlar girilecektir.

			*/
			
			
			String SQLifade="Create database OGROTO_BM408_VIZE";
			int etkilenensatirsayisi=ifade.executeUpdate(SQLifade);
			if(etkilenensatirsayisi==0) 
				System.out.println("Veritabanı oluşturulmuştur.");
			
			
			
			/* FAKULTE TABLOSU */
			
			String SQLifade1 = "CREATE TABLE FAKULTE"+
								"(FNo INT IDENTITY (100, 100) PRIMARY KEY,"+
								"Ad NVARCHAR (250) NOT NULL,"+
								"Konum NVARCHAR (200));";
			
			int etkilenensatirsayisi1=ifade.executeUpdate(SQLifade1); 
			if(etkilenensatirsayisi1==0) 
				System.out.println("FAKÜLTE TABLOSU OLUŞTURULMUŞTUR.."); 
		
			
			/*BÖLUM TABLOSU */
		
			String SQLifade2 = "CREATE TABLE BOLUM"+
								"(BNo INT  IDENTITY (10, 10) PRIMARY KEY,"+
								"Ad  NVARCHAR (50) NOT NULL,"+
								"FNo INT   NOT NULL,"+
								"FOREIGN KEY (FNo) REFERENCES FAKULTE (FNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi2=ifade.executeUpdate(SQLifade2); 
			if(etkilenensatirsayisi2==0) 
				System.out.println("BÖLÜM TABLOSU OLUŞTURULMUŞTUR.."); 
		
			
			/* ÖĞRETİM ELEMANLARI-AKADEMİSYENLER*/
			
		
			String SQLifade3 = "CREATE TABLE AKADEMISYEN"+
								"(AkaNo NVARCHAR (11) PRIMARY KEY, "+
								"Ad NVARCHAR (25) NOT NULL,"+
								"Soyad NVARCHAR (20) NOT NULL,"+
								"Unvan NVARCHAR (20),"+
								"Maas  MONEY,"+
								"BNo   INT,"+
								"FOREIGN KEY (BNo) REFERENCES BOLUM (BNo) ON DELETE SET NULL ON UPDATE NO ACTION);";
																
			int etkilenensatirsayisi3=ifade.executeUpdate(SQLifade3); 
			if(etkilenensatirsayisi3==0) 
				System.out.println("AKADEMISYEN TABLOSU OLUŞTURULMUŞTUR.."); 
		
			
			/* ÖĞRENCİ TABLOSU*/
		
			String SQLifade4 = "CREATE TABLE OGRENCI"+
								"(OgrNo NVARCHAR (11) NOT NULL,"+
								"Ad NVARCHAR (20) NOT NULL,"+
								"Soyad NVARCHAR (20) NOT NULL,"+
								"DTar  DATE,"+
								"Cins  BIT,"+
								"Foto  IMAGE,"+
								"Harc  MONEY,"+
								"GNO   FLOAT,"+
								"BNo  INT NOT NULL,"+
								"PRIMARY KEY (OgrNo),"+
								"FOREIGN KEY (BNo) REFERENCES BOLUM (BNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi4=ifade.executeUpdate(SQLifade4); 
			if(etkilenensatirsayisi4==0) 
				System.out.println("ÖĞRENCİ TABLOSU OLUŞTURULMUŞTUR.."); 
		
			
			
			/* DANIŞMAN TABLOSU*/
		
			String SQLifade5 = "CREATE TABLE DANISMAN"+
								"(AkaNo NVARCHAR (11),"+
								"OgrNo NVARCHAR (11) PRIMARY KEY,"+
								"FOREIGN KEY (AkaNo) REFERENCES AKADEMISYEN (AkaNo) ON DELETE SET NULL ON UPDATE NO ACTION,"+
								"FOREIGN KEY (OgrNo) REFERENCES OGRENCI (OgrNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi5=ifade.executeUpdate(SQLifade5); 
			if(etkilenensatirsayisi5==0) 
				System.out.println("DANIŞMAN TABLOSU OLUŞTURULMUŞTUR.."); 
		
			
			
			/*DERSLER TABLOSU */
			
			String SQLifade6 = "CREATE TABLE DERS"+
								"(DersNo NVARCHAR (10) PRIMARY KEY,"+
								"Ad NVARCHAR (30) NOT NULL,"+
								"Teo INT ,"+
								"Uyg INT,"+
								"Krd FLOAT,"+
								"AKTS INT,"+
								"BNo INT,"+
								"FOREIGN KEY (BNo) REFERENCES BOLUM (BNo) ON DELETE SET NULL ON UPDATE NO ACTION);";
						
			int etkilenensatirsayisi6=ifade.executeUpdate(SQLifade6); 
			if(etkilenensatirsayisi6==0) 
				System.out.println("DERSLER TABLOSU OLUŞTURULMUŞTUR.."); 
			
			
			
			
			
			/* DÖNEM TABLOSU */
			
			String SQLifade7 = "CREATE TABLE DONEM"+
								"(DersNo  NVARCHAR (10),"+
								"DonemNo NVARCHAR (10),"+
								"Yil NUMERIC (4, 0) CHECK (yil > 2001 AND yil < 2100),"+
								"DonemAd NVARCHAR (10)  CHECK (DonemAd IN ('Güz', 'Bahar', 'Yaz Okulu')),"+
								"Sube NVARCHAR (4),"+
								"PRIMARY KEY (DersNo, DonemNo, Yil),"+
								"FOREIGN KEY (DersNo) REFERENCES DERS (DersNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi7=ifade.executeUpdate(SQLifade7); 
			if(etkilenensatirsayisi7==0) 
				System.out.println("DÖNEM TABLOSU OLUŞTURULMUŞTUR.."); 
			
			
			/*ÖĞRENCİ NOTLARI TABLOSU */
			
			String SQLifade8 = "CREATE TABLE OGRNOT"+
								"( OgrNo   NVARCHAR (11),"+
								"DersNo  NVARCHAR (10),"+
								"DonemNo NVARCHAR (10),"+
								"Yil NUMERIC (4, 0),"+
								"Vize INT,"+
								"Final INT,"+
								"But INT,"+
								"Harf CHAR (2),"+
								"PRIMARY KEY (OgrNo, DersNo, DonemNo, Yil),"+
								"FOREIGN KEY (DersNo, DonemNo, Yil) REFERENCES DONEM (DersNo, DonemNo, Yil) ON DELETE NO ACTION ON UPDATE NO ACTION,"+  
								"FOREIGN KEY (OgrNo) REFERENCES OGRENCI (OgrNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi8=ifade.executeUpdate(SQLifade8); 
			if(etkilenensatirsayisi8==0) 
				System.out.println("ÖĞRENCİ NOTLARI TABLOSU OLUŞTURULMUŞTUR..");
			
		
			
			/* ÖĞRETİM ELEMANLARI DERS ETKİNLİĞİ TABLOSU */
			
		
			String SQLifade9 = "CREATE TABLE AKADERS"+
								"(AkaDersNo NVARCHAR (11),"+
								"DersNo NVARCHAR (10),"+
								"DonemNo   NVARCHAR (10),"+
								"Yil NUMERIC (4, 0),"+
								"PRIMARY KEY (AkaDersNo, DersNo, DonemNo, Yil),"+
								"FOREIGN KEY (DersNo, DonemNo, Yil) REFERENCES DONEM (DersNo, DonemNo, Yil) ON DELETE CASCADE ON UPDATE NO ACTION,"+
								"FOREIGN KEY (AkaDersNo) REFERENCES AKADEMISYEN (AkaNo) ON DELETE CASCADE ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi9=ifade.executeUpdate(SQLifade9); 
			if(etkilenensatirsayisi9==0) 
				System.out.println("ÖĞRETİM ELEMANLARI DERS ETKİNLİĞİ TABLOSU OLUŞTURULMUŞTUR..");
			
		
			
			/* ÖN KOŞUL TABLOSU*/
		
			String SQLifade10 = "CREATE TABLE ONKOSUL"+
								"(OnKosulDersNo NVARCHAR (10),"+
								"DersNo  NVARCHAR (10),"+
								"PRIMARY KEY (DersNo, OnKosulDersNo),"+
								"FOREIGN KEY (DersNo) REFERENCES Ders (DersNo) ON DELETE NO ACTION ON UPDATE NO ACTION,"+
								"FOREIGN KEY (OnKosulDersNo) REFERENCES Ders (DersNo) ON DELETE NO ACTION ON UPDATE NO ACTION);";
			
			int etkilenensatirsayisi10=ifade.executeUpdate(SQLifade10); 
			if(etkilenensatirsayisi10==0) 
				System.out.println("ÖN KOŞUL TABLOSU OLUŞTURULMUŞTUR..");
				
		
			
			
			
			/* VERİ GİRİŞLERİ */
			
			/*FAKULTE TABLOSUNA VERİ GİRİŞİ */
		
			String SQLifade11="SET IDENTITY_INSERT FAKULTE ON;"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (100, 'Barbaros Hayrettin Gemi İnşaatı ve Denizcilik Fakültesi', 'MERKEZ');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (200, 'Deniz Bilimleri ve Teknolojisi Fakültesi','KAMPÜS');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (300, 'Havacılık ve Uzay Bilimleri Fakültesi', 'KAMPÜS');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (400, 'Mimarlık Fakültesi', 'MERKEZ');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (500, 'Mühendislik ve Doğa Bilimleri Fakültesi', 'KAMPÜS');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (500, 'Mühendislik ve Doğa Bilimleri Fakültesi', 'KAMPÜS');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (600, 'İşletme ve Yönetim Bilimleri Fakültesi', 'MERKEZ');"+
								"INSERT INTO FAKULTE (FNo, Ad, Konum)"+
								"VALUES (700, 'Turizm Fakültesi', 'MERKEZ');"+
							  "SET IDENTITY_INSERT FAKULTE OFF;";
								
			int etkilenensatirsayisi11=ifade.executeUpdate(SQLifade11); 
			if(etkilenensatirsayisi11>0) 
				System.out.println("FAKULTELER TABLOSUNA VERİ GİRİLMİŞTİR..");					
			
		
			/*BOLUM TABLOSUNA VERİ GİRİŞİ */
			
			String SQLifade12 = "SET IDENTITY_INSERT BOLUM ON;"+
								  "INSERT INTO BOLUM (BNo, Ad, FNo)"+
								  "VALUES (10, 'BİLGİSAYAR', 500);"+
								  "INSERT INTO BOLUM (BNo, Ad, FNo)"+
								  "VALUES (20, 'MAKİNE', 500);"+
								  "INSERT INTO BOLUM (BNo, Ad, FNo)"+
								  "VALUES (30, 'ELEKTRİK', 500);"+
								  "INSERT INTO BOLUM (BNo, Ad, FNo)"+
								  "VALUES (40, 'MİMARLIK', 400);"+
								  "INSERT INTO BOLUM (BNo, Ad, FNo)"+
								  "VALUES (50, 'TURİZM', 700);"+
								  "SET IDENTITY_INSERT BOLUM OFF;";
								  
			int etkilenensatirsayisi12=ifade.executeUpdate(SQLifade12); 
			if(etkilenensatirsayisi12>0) 
				System.out.println("BOLUM TABLOSUNA VERİ GİRİLMİŞTİR..");		
			
		
			
			/* OGRENCİ TABLOSUNA VERİ GİRİŞİ*/
			
			String SQLifade13 = "INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1020001', 'BUSE NUR', 'ÇETÝN', CAST ('2004-01-01' AS DATE), 0, NULL, 300, 2.46, 10);"+					
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1020002', 'BİRCAN', 'ÇETMEN', CAST ('2005-01-02' AS DATE), 0, NULL, 150, 2.25, 10);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1020003', 'SERHAN', 'NARLI', CAST ('2006-03-02' AS DATE), 1, NULL, NULL, 3.4, 10);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1020004', 'TUĞÇE', 'DEMİR', CAST ('2006-01-02' AS DATE), 0, NULL, 250, 2.32, 10);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('2010003', 'AHMET', 'KARA', CAST ('2001-01-01' AS DATE), 1, NULL, 550, 2.55, 20);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('3020004', 'FATİH', 'DEMİR', CAST ('2000-01-01' AS DATE), 1, NULL, 600, 3.06, 30);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('3020005', 'AHMET', 'OZDEMR', CAST ('1998-01-01' AS DATE), 1, NULL, 600, 2.2, 30);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('4010001', 'ALİ', 'OKURSA', CAST ('2005-03-05' AS DATE), 1, NULL, 0, 2.22, 40);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('5020001', 'VELİ', 'YAZAR', CAST ('2005-08-08' AS DATE), 1, NULL, 100, 1.98, 50);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1315032', 'MAZLUM', 'ABUL', CAST ('1978-09-07' AS DATE), 1, NULL, 100, 1.98, 10);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1415032', 'AYŞE', 'YILMAZ', CAST ('1998-10-07' AS DATE), 0, NULL, 100, 1.98, 10);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1310032', 'FATMA', 'ÖZLEM', CAST ('1990-01-01' AS DATE), 0, NULL, 100, 3.98, 20);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1014032', 'ATAHAN', 'ACAR', CAST ('1991-02-03' AS DATE), 1, NULL, 100, 0.98, 30);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1815032', 'MÜGE', 'SEVİNÇ', CAST ('1992-03-09' AS DATE), 0, NULL, 100, 2.28, 40);"+								
								"INSERT INTO  OGRENCI (OgrNo, Ad, Soyad, DTar, Cins, Foto, Harc, GNO, BNo)"+
								"VALUES ('1815032', 'RAFİ', 'AKAY', CAST ('1999-12-12' AS DATE), 1, NULL, 100, 2.28, 50);";
								
			int etkilenensatirsayisi13=ifade.executeUpdate(SQLifade13); 
			if(etkilenensatirsayisi13>0) 
				System.out.println("OGRENCİ TABLOSUNA VERİ GİRİLMİŞTİR..");		
			
			
			
			/* AKADEMİSYEN TABLOSUNA VERİ GİRİŞİ*/		
			
			String SQLifade14 = "INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10000', 'HAMZA', 'EROL', 'PROF.DR.', 9000, 10);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10001', 'YAŞAR', 'DAŞDEMİR', 'DR.', 3000, 10);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10002', 'ORHAN','ACIKIR','DR.', 6000, 50);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10003', 'YAKUP', 'KUTLU', 'DOÇ.DR.', 5000, 10);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10004', 'FATÝH', 'BALABAN', 'DR.', 3000, 30);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10005', 'KAAN', 'BALTA','DOÇ.DR', 4500, 20);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10006', 'SERTAN', 'ALTAN', 'DOÇ.DR', 4000, 40);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10007', 'MAZLUM', 'ABUL', 'PROF.DR.', 30000, 50);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10008', 'İLKER', 'SERASLAN', 'DR.', 3000, 20);"+
								"INSERT INTO AKADEMISYEN (AkaNo, Ad, Soyad, Unvan, Maas, BNo)"+
								"VALUES('10009', 'MUSTAFA', 'UYGUAR', 'DR.', 3000, 30);";
			
			int etkilenensatirsayisi14=ifade.executeUpdate(SQLifade14); 
			if(etkilenensatirsayisi14>0) 
				System.out.println("AKADEMİSYEN TABLOSUNA VERİ GİRİLMİŞTİR..");		
			
			
			/* DANIŞMAN TABLOSUNA VERİ GİRİŞİ*/		
			
			String SQLifade15 = "INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10000', '1315032');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10001', '5020001');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10002', '1815032');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10003', '1014032');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10004', '3020004');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10005', '1415032');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10006', '2010003');"+
								"INSERT INTO DANISMAN (AkaNo, OgrNo)"+
								"VALUES('10007', '1020001');";
								
			int etkilenensatirsayisi15=ifade.executeUpdate(SQLifade15); 
			if(etkilenensatirsayisi15>0) 
				System.out.println("DANISMAN TABLOSUNA VERİ GİRİLMİŞTİR..");
		
			
			
			/* DERS TABLOSUNA VERİ GİRİŞİ*/	
			
			String SQLifade16 = "INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('B001', 'DBMS', 3, 1, 4, 8, 10);"+
								"INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('B002', 'OTOMATLAR', 3, 0, 3, 6, 10);"+
								"INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('E001', 'DEVRELER', 3, 1, 4, 8, 30);"+
								"INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('M001', 'TERMODİNAMİK', 3, 0, 3, 5, 20);"+
								"INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('T001', 'GEZME', 3, 1, 5, 8, 50);"+
								"INSERT INTO DERS(DersNo, Ad, Teo, Uyg, Krd, AKTS, BNo)"+
								"VALUES('Mİ001', 'MUKAVEMET', 3, 1, 5, 8, 40);";
								
			int etkilenensatirsayisi16=ifade.executeUpdate(SQLifade16); 
			if(etkilenensatirsayisi16>0) 
				System.out.println("DERS TABLOSUNA VERİ GİRİLMİŞTİR..");
			
		
			
			/* DONEM TABLOSUNA VERİ GİRİŞİ*/	
		
			String SQLifade17 = "INSERT INTO  DONEM(DersNo, DonemNo, DonemAd, Yil, Sube)"+
								"VALUES('B001', '1', 'GÜZ', CAST (2016 AS NUMERIC (4, 0)), '3');"+
								"INSERT INTO  DONEM(DersNo, DonemNo, DonemAd, Yil, Sube)"+
								"VALUES('B002', '2', 'GÜZ', CAST (2017 AS NUMERIC (4, 0)), '3');"+
								"INSERT INTO  DONEM(DersNo, DonemNo, DonemAd, Yil, Sube)"+
								"VALUES('E001', '3', 'BAHAR', CAST (2016 AS NUMERIC (4, 0)), '2');"+
								"INSERT INTO  DONEM(DersNo, DonemNo, DonemAd, Yil, Sube)"+
								"VALUES('Mİ001', '3', 'GÜZ', CAST (2019 AS NUMERIC (4, 0)), '1');"+
								"INSERT INTO  DONEM(DersNo, DonemNo, DonemAd, Yil, Sube)"+
								"VALUES('B002', '2', 'BAHAR', CAST (2020 AS NUMERIC (4, 0)), '2');";
					
			int etkilenensatirsayisi17=ifade.executeUpdate(SQLifade17); 
			if(etkilenensatirsayisi17>0) 
				System.out.println("DONEM TABLOSUNA VERİ GİRİLMİŞTİR..");
			 
								
			/* AKADERS TABLOSUNA VERİ GİRİŞİ*/	
		
			String SQLifade18 = "INSERT INTO  AKADERS (AkaDersNo, DersNo, DonemNo, Yil)"+
								"VALUES ('10000', 'B001', '1', CAST (2016 AS NUMERIC (4, 0)));"+
								"INSERT INTO  AKADERS (AkaDersNo, DersNo, DonemNo, Yil)"+
								"VALUES ('10001', 'B001', '1', CAST (2016 AS NUMERIC (4, 0)));"+
								"INSERT INTO  AKADERS (AkaDersNo, DersNo, DonemNo, Yil)"+
								"VALUES ('10001', 'B001', '2', CAST (2017 AS NUMERIC (4, 0)));"+
								"INSERT INTO  AKADERS (AkaDersNo, DersNo, DonemNo, Yil)"+
								"VALUES ('10002', 'E001', '3', CAST (2016 AS NUMERIC (4, 0)));"+
								"INSERT INTO  AKADERS (AkaDersNo, DersNo, DonemNo, Yil)"+
								"VALUES ('10002', 'Mİ001', '3', CAST (2020 AS NUMERIC (4, 0)));";
			
			
			int etkilenensatirsayisi18=ifade.executeUpdate(SQLifade18); 
			if(etkilenensatirsayisi18>0) 
				System.out.println("AKADERS TABLOSUNA VERİ GİRİLMİŞTİR..");
			
			
			
			/* OGRNOT TABLOSUNA VERİ GİRİŞİ*/	
		
			String SQLifade19 = "INSERT INTO  OGRNOT (OgrNo, DersNo, DonemNo, Yil, Vize, Final, But, Harf)"+
								"VALUES('1020001', 'B001', '1', CAST (2016 AS NUMERIC (4, 0)), 66, 70, NULL,'BB');"+
								"INSERT INTO  OGRNOT (OgrNo, DersNo, DonemNo, Yil, Vize, Final, But, Harf)"+
								"VALUES('5020001', 'E001', '3', CAST (2016 AS NUMERIC (4, 0)), 50, 35, 80, 'CB');"+
								"INSERT INTO  OGRNOT (OgrNo, DersNo, DonemNo, Yil, Vize, Final, But, Harf)"+
								"VALUES('4010001', 'Mİ001', '3', CAST (2019 AS NUMERIC (4, 0)), 30, 55, 85, 'CC');"+
								"INSERT INTO  OGRNOT (OgrNo, DersNo, DonemNo, Yil, Vize, Final, But, Harf)"+
								"VALUES('1020001', 'B002', '2', CAST (2020 AS NUMERIC (4, 0)), 80, 90, NULL, 'BA');";
			
			int etkilenensatirsayisi19=ifade.executeUpdate(SQLifade19); 
			if(etkilenensatirsayisi19>0) 
				System.out.println("OGRNOT TABLOSUNA VERİ GİRİLMİŞTİR..");
			
		
			
			/* ONKOSUL TABLOSUNA VERİ GİRİŞİ*/	
		
			String SQLifade20 = "INSERT INTO  ONKOSUL (OnKosulDersNo, DersNo)"+
								"VALUES ('B001','B002');"+
								"INSERT INTO  ONKOSUL (OnKosulDersNo, DersNo)"+
								"VALUES ('B001','Mİ001');"+
								"INSERT INTO  ONKOSUL (OnKosulDersNo, DersNo)"+
								"VALUES ('B002','Mİ001');";
			
			
			int etkilenensatirsayisi20=ifade.executeUpdate(SQLifade20); 
			if(etkilenensatirsayisi20>0) 
				System.out.println("ONKOSUL TABLOSUNA VERİ GİRİLMİŞTİR..");
			
		
			
			
		
			
			
			/*
			  5. MSSQL Database Server üzerindeki veritabanının bir tablosunda 
			  bir kayıtın bir alanında yer alan değer SQL kullanılarak java kod ile değiştirilecektir.  
			*/
			
			
			ifade.execute("UPDATE AKADEMISYEN SET Ad='fatih' where AkaNo=10004");
			System.out.println("güncelleme yapıldı");
		
			
			/*
			   6. MSSQL Database Server üzerindeki veritabanının bir tablosunda 
		       SQL kullanılarak java kod ile bir kayıt silinecektir.  
		      
			*/
		
			
			ifade.execute("DELETE FROM OGRENCI  where OgrNo=5020001");
			
		
			
			/* 7. MSSQL Database Server üzerindeki veritabanında yer alan bir tabloda 
	           SQL kullanılarak java kod ile istenilen özellikteki kayıtlar listelenecektir.*/
			
	
			System.out.println("Konumu Kampüs Olan Fakulteler");
			String SQLifade25="Select * from FAKULTE where Konum='KAMPÜS'";
			
			ResultSet sonucKumesi=ifade.executeQuery(SQLifade25); 
			while(sonucKumesi.next())
			{
				System.out.println(sonucKumesi.getString(1)
						+"-"+sonucKumesi.getString(3));
			}
			
			
			/*
	           8. MSSQL Database Server üzerindeki veritabanında yer alan iki tabloda 
	           SQL kullanılarak java kod ile istenilen özellikteki kayıtlar listelenecektir. 
	           
	           
	           
	        */
		
			
			String query ="SELECT *  FROM  OGRNOT O INNER JOIN OGRENCI N ON O.OgrNo = N.OgrNo  ";
		      ResultSet rs = ifade.executeQuery(query);
		     
		      
		      while (rs.next()) {
		         String fname = rs.getString(1);
		         String lname = rs.getString(2);
		         System.out.println(rs.getString(1) + " "+rs.getString(2));  
		        
		      }
		 
		     
			/*   
			  9. MSSQL Database Server üzerindeki veritabanının bir tablo 
	           SQL kullanılarak java kod ile silinecektir. 
	        */
		
			ifade.execute("DROP TABLE deneme");
			
		
			
			}

}

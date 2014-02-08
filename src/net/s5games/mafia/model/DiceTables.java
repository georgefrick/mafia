package net.s5games.mafia.model;

public class DiceTables {
	
	public final static String[] parseDice(String dice) {
		int theD = dice.indexOf('d');
		int thePlus = dice.indexOf('+');
		String results[] = new String[3];
		results[0] = dice.substring(0, theD);
		results[1] = dice.substring(theD+1, thePlus);
		results[2] = dice.substring(thePlus+1);
		return results;
	}
	
	/* 
	 * HP , AC, ACmagic, DAMAGE, MANA
	 */
	public final static String  MOBstat_table[][] = {
	
		{ "2d6+10", "93", "108", "1d2+0", "1d10+100" }, //1
		{ "2d6+10", "93", "108", "1d2+0", "1d10+100" },
		{ "2d6+10", "86", "100", "1d2+1", "2d10+100" },
		{ "2d7+21", "79", "92", "1d2+2", "3d10+100" },
		{ "2d7+30", "72", "84", "1d3+5", "4d10+100" },
		{ "2d6+35", "65", "76", "2d3+2", "5d10+100" },
		{ "2d7+46", "58", "67", "2d3+5", "6d10+100" },	
		{ "2d7+50", "51", "59", "2d4+3", "7d10+100" },	
		{ "2d6+60", "44", "51", "2d4+5", "8d10+100" },	
		{ "2d6+70", "37", "43", "2d5+4", "9d10+100" }, // 10	
		{ "2d7+71", "30", "35", "2d5+5", "10d10+100" },	
		{ "2d6+85", "23", "26", "2d5+7", "11d10+100" },
		{ "2d7+85", "16", "18", "2d5+8", "12d10+100" },
		{ "2d7+96", "9", "10", "2d5+10", "13d10+100" },
		{ "2d7+100", "2", "2", "3d5+8", "14d10+100" },
		{ "2d6+110", "-5", "-5", "3d5+9", "15d10+100" },
		{ "2d7+121", "-12", "-10", "3d5+11", "16d10+100" },
		{ "2d7+130", "-19", "-16", "3d5+13", "17d10+100" },
		{ "2d8+134", "-26", "-22", "4d5+8", "18d10+100" },
		{ "2d10+140", "-33", "-28", "4d5+9", "19d10+100" }, // 20
		{ "2d10+150", "-40", "-34", "4d5+10", "20d10+100" },
		{ "2d10+170", "-47", "-40", "4d5+11", "21d10+100" },
		{ "2d10+180", "-54", "-45", "4d5+12", "22d10+100" },
		{ "2d10+190", "-61", "-51", "5d5+10", "23d10+100" },
		{ "2d10+200", "-68", "-57", "5d5+11", "24d10+100" },
		{ "3d9+208", "-75", "-63", "5d5+12", "25d10+100" },
		{ "3d9+233", "-82", "-69", "5d5+13", "26d10+100" },
		{ "3d9+244", "-89", "-74", "5d5+15", "27d10+100" },
		{ "3d9+258", "-96", "-80", "5d6+13", "28d10+100" },
		{ "3d9+260", "-103", "-86", "5d6+14", "29d10+100" }, // 30
		{ "3d9+283", "-110", "-92", "5d6+15", "30d10+100" },
		{ "3d9+308", "-117", "-98", "5d6+17", "31d10+100" },
		{ "3d9+228", "-124", "-103", "5d6+18", "32d10+100" },
		{ "3d9+333", "-131", "-109", "5d7+15", "33d10+100" },
		{ "3d9+350", "-138", "-115", "5d7+16", "34d10+100" },
		{ "4d10+360", "-145", "-121", "5d7+17", "35d10+100" },
		{ "5d10+400", "-152", "-127", "5d7+18", "36d10+100" },
		{ "5d10+420", "-159", "-132", "5d7+20", "37d10+100" },
		{ "5d10+450", "-166", "-138", "5d8+18", "38d10+100" },
		{ "5d10+470", "-173", "-144", "5d8+19", "39d10+100" }, // 40
		{ "5d10+500", "-180", "-150", "5d8+20", "40d10+100" },
		{ "5d10+525", "-187", "-156", "5d8+21", "41d10+100" },
		{ "5d10+550", "-194", "-162", "5d8+22", "42d10+100" },
		{ "5d10+600", "-201", "-167", "5d9+20", "43d10+100" },
		{ "5d10+625", "-208", "-173", "5d9+21", "44d10+100" },
		{ "5d10+650", "-215", "-179", "5d9+23", "45d10+100" },
		{ "6d12+703", "-222", "-185", "5d9+25", "46d10+100" },
		{ "6d12+750", "-229", "-191", "5d10+21", "47d10+100" },
		{ "6d12+778", "-236", "-196", "5d10+23", "48d10+100" },
		{ "6d12+853", "-243", "-202", "5d10+24", "49d10+100" }, // 50
		{ "6d12+900", "-250", "-208", "5d10+25", "50d10+100" },
		{ "6d12+928", "-257", "-214", "5d10+27", "51d10+100" },
		{ "6d12+960", "-264", "-220", "5d10+28", "52d10+100" },
		{ "10d10+1000", "-271", "-225", "5d11+25", "53d10+100" },
		{ "10d10+1050", "-278", "-231", "5d10+27", "54d10+100" },
		{ "10d10+1100", "-285", "-237", "5d11+27", "55d10+100" },
		{ "10d10+1200", "-292", "-243", "5d11+28", "56d10+100" },
		{ "10d10+1250", "-299", "-249", "5d11+30", "57d10+100" },
		{ "10d10+1300", "-306", "-254", "5d12+28", "58d10+100" },
		{ "10d10+1350", "-313", "-260", "5d12+29", "59d10+100" }, // 60
		{ "10d10+1400", "-320", "-266", "5d12+30", "60d10+100" },
		{ "10d10+1500", "-327", "-272", "5d12+31", "61d10+100" },
		{ "10d10+1550", "-334", "-278", "5d12+33", "62d10+100" },
		{ "10d10+1600", "-341", "-284", "5d13+30", "63d10+100" },
		{ "10d10+1650", "-348", "-289", "5d13+32", "64d10+100" },
		{ "15d10+1700", "-355", "-295", "5d13+33", "65d10+100" },
		{ "15d10+1700", "-362", "-301", "5d13+34", "66d10+100" },
		{ "15d10+1850", "-369", "-307", "5d13+35", "67d10+100" },
		{ "25d10+2000", "-376", "-313", "5d14+33", "68d10+100" },
		{ "25d10+2175", "-383", "-318", "5d14+34", "69d10+100" }, // 70
		{ "25d10+2250", "-390", "-324", "5d14+35", "70d10+100" },
		{ "25d10+2500", "-397", "-330", "5d14+36", "71d10+100" },
		{ "25d10+2675", "-404", "-336", "5d14+37", "72d10+100" },
		{ "25d10+2750", "-411", "-342", "5d15+35", "73d10+100" },
		{ "25d10+2875", "-418", "-347", "5d15+36", "74d10+100" },
		{ "25d10+3000", "-425", "-353", "5d15+37", "75d10+100" },
		{ "25d10+3250", "-432", "-359", "5d15+39", "76d10+100" },
		{ "25d10+3375", "-439", "-365", "5d15+40", "77d10+100" },
		{ "25d10+3500", "-446", "-371", "5d16+38", "78d10+100" },
		{ "25d10+3675", "-453", "-376", "5d16+39", "79d10+100" }, // 80
		{ "25d10+3750", "-460", "-382", "5d16+40", "80d10+100" },
		{ "25d10+3875", "-467", "-388", "5d16+42", "81d10+100" },
		{ "50d10+4000", "-474", "-394", "5d16+43", "82d10+100" },
		{ "50d10+4250", "-481", "-400", "5d17+41", "83d10+100" },
		{ "50d10+4500", "-488", "-406", "5d17+42", "84d10+100" },
		{ "50d10+5000", "-495", "-411", "5d17+43", "85d10+100" },
		{ "50d10+5250", "-502", "-417", "5d17+44", "86d10+100" },
		{ "50d10+5500", "-509", "-423", "5d17+45", "87d10+100" },
		{ "50d10+5750", "-516", "-429", "5d18+43", "88d10+100" },
		{ "50d10+6000", "-523", "-435", "5d18+44", "89d10+100" }, // 90
		{ "50d10+6500", "-530", "-440", "5d18+45", "90d10+100" },
		{ "50d10+6750", "-537", "-446", "5d18+46", "91d10+100" },
		{ "50d10+7000", "-544", "-452", "5d18+47", "92d10+100" },
		{ "50d10+7250", "-551", "-458", "5d19+45", "93d10+100" },
		{ "50d10+7500", "-558", "-464", "5d19+46", "94d10+100" },
		{ "50d10+8000", "-565", "-469", "5d19+47", "95d10+100" },
		{ "50d10+8250", "-572", "-475", "5d19+49", "96d10+100" },
		{ "50d10+8500", "-579", "-481", "5d19+50", "97d10+100" },
		{ "50d10+8750", "-586", "-487", "5d20+47", "98d10+100" },
		{ "50d10+8500", "-593", "-493", "5d20+49", "99d10+100" }, // 100
		{ "50d10+9500", "-600", "-498", "5d20+50", "100d10+100" },
		{ "50d10+10000", "-607", "-504", "5d21+48", "101d10+100" },
		{ "50d10+11000", "-614", "-510", "5d21+49", "102d10+100" },
		{ "50d10+12000", "-621", "-516", "5d21+50", "103d10+100" },
		{ "50d10+13000", "-628", "-522", "5d22+49", "104d10+100" },
		{ "50d10+14000", "-635", "-528", "5d22+50", "105d10+100" },
		{ "50d10+15000", "-642", "-533", "5d22+51", "106d10+100" },
		{ "50d10+16000", "-649", "-539", "5d23+50", "107d10+100" },
		{ "50d10+17000", "-656", "-545", "5d23+51", "108d10+100" },
		{ "50d10+18000", "-663", "-551", "5d23+52", "109d10+100" }, // 110
		{ "50d10+19000", "-670", "-557", "5d24+51", "110d10+100" },
		{ "50d10+20000", "-677", "-562", "5d24+52", "111d10+100" },
		{ "50d10+21000", "-684", "-568", "5d25+53", "112d10+100" },
		{ "50d10+22000", "-691", "-574", "6d25+53", "113d10+100" },
		{ "50d10+23000", "-698", "-580", "6d25+55", "114d10+100" },
		{ "50d10+24000", "-705", "-586", "6d25+57", "115d10+100" },
		{ "50d10+25000", "-712", "-591", "6d25+59", "116d10+100" },
		{ "50d10+26000", "-719", "-597", "6d25+61", "117d10+100" },
		{ "50d10+27000", "-726", "-603", "6d25+63", "118d10+100" },
		{ "50d10+28000", "-733", "-609", "6d25+65", "119d10+100" }, // 120 
		{ "50d10+29000", "-740", "-615", "7d25+70", "120d10+100" },
		{ "50d10+30000", "-747", "-621", "7d25+75", "121d10+100" },
		{ "50d10+31000", "-754", "-626", "7d25+80", "122d10+100" },
		{ "50d10+32000", "-761", "-632", "7d26+80", "123d10+100" },
		{ "50d10+33000", "-768", "-638", "7d26+82", "124d10+100" },
		{ "75d10+34000", "-775", "-644", "7d26+85", "125d10+100" },
		{ "75d10+35000", "-782", "-650", "7d26+87", "126d10+100" },
		{ "75d10+36000", "-789", "-655", "7d26+90", "127d10+100" },
		{ "75d10+37000", "-796", "-661", "7d27+80", "128d10+100" },
		{ "75d10+38000", "-803", "-667", "7d27+82", "129d10+100" }, // 130
		{ "75d10+39000", "-810", "-673", "7d27+85", "130d10+100" },
		{ "75d10+40000", "-817", "-679", "7d27+87", "131d10+100" },
		{ "75d10+41000", "-824", "-684", "7d27+90", "132d10+100" },
		{ "75d10+42000", "-831", "-690", "7d28+80", "133d10+100" },
		{ "75d10+43000", "-838", "-696", "7d28+82", "134d10+100" },
		{ "75d10+44000", "-845", "-702", "7d28+85", "135d10+100" },
		{ "75d10+45000", "-852", "-708", "7d28+87", "136d10+100" },
		{ "75d10+46000", "-859", "-713", "7d28+90", "137d10+100" },
		{ "75d10+47000", "-866", "-719", "7d29+80", "138d10+100" },
		{ "75d10+48000", "-873", "-725", "7d29+82", "139d10+100" }, // 140
		{ "75d10+49000", "-880", "-731", "7d29+85", "140d10+100" },
		{ "75d10+50000", "-887", "-737", "7d29+87", "141d10+100" },
		{ "75d10+51000", "-894", "-743", "7d29+90", "142d10+100" },
		{ "75d10+52000", "-901", "-748", "7d30+80", "143d10+100" },
		{ "75d10+53000", "-908", "-754", "7d30+82", "144d10+100" },
		{ "75d10+54000", "-915", "-760", "7d30+85", "145d10+100" },
		{ "75d10+55000", "-922", "-766", "7d30+87", "146d10+100" },
		{ "75d10+56000", "-929", "-772", "7d30+90", "147d10+100" },
		{ "75d10+57000", "-936", "-777", "7d31+80", "148d10+100" },
		{ "75d10+58000", "-943", "-783", "7d31+82", "149d10+100" }, // 150
		{ "75d10+59000", "-950", "-789", "7d31+85", "150d10+100" },
		{ "75d10+60000", "-957", "-795", "7d31+87", "151d10+100" },
		{ "75d10+61000", "-964", "-801", "7d31+90", "152d10+100" },
		{ "75d10+62000", "-971", "-806", "7d32+80", "153d10+100" },
		{ "75d10+63000", "-978", "-812", "7d32+82", "154d10+100" },
		{ "75d10+64000", "-985", "-818", "7d32+85", "155d10+100" },
		{ "75d10+65000", "-992", "-824", "7d32+87", "156d10+100" },
		{ "75d10+66000", "-999", "-830", "7d32+90", "157d10+100" },
		{ "75d10+67000", "-1006", "-835", "7d33+80", "158d10+100" },
		{ "75d10+68000", "-1013", "-841", "7d33+82", "159d10+100" }, // 160
		{ "75d10+69000", "-1020", "-847", "7d33+85", "160d10+100" },
		{ "75d10+70000", "-1027", "-853", "7d33+90", "161d10+100" },
		{ "75d10+71000", "-1034", "-859", "7d34+80", "162d10+100" },
		{ "75d10+72000", "-1041", "-865", "7d34+82", "163d10+100" },
		{ "75d10+73000", "-1048", "-870", "7d34+85", "164d10+100" },
		{ "75d10+74000", "-1055", "-876", "7d34+87", "165d10+100" },
		{ "75d10+75000", "-1062", "-882", "7d34+90", "166d10+100" },
		{ "75d10+76000", "-1069", "-888", "7d35+80", "167d10+100" },
		{ "75d10+77000", "-1076", "-894", "7d35+82", "168d10+100" },
		{ "75d10+78000", "-1083", "-899", "7d35+84", "169d10+100" }, // 170
		{ "75d10+79000", "-1090", "-905", "7d35+87", "170d10+100" },
		{ "75d10+80000", "-1097", "-911", "7d35+90", "171d10+100" },
		{ "75d10+81000", "-1104", "-917", "7d36+80", "172d10+100" },
		{ "75d10+82000", "-1111", "-923", "7d36+82", "173d10+100" },
		{ "75d10+83000", "-1118", "-928", "7d36+85", "174d10+100" },
		{ "75d10+84000", "-1125", "-934", "7d36+87", "175d10+100" },
		{ "75d10+85000", "-1132", "-940", "7d36+90", "176d10+100" },
		{ "75d10+86000", "-1139", "-946", "7d37+80", "177d10+100" },
		{ "75d10+87000", "-1146", "-952", "7d37+82", "178d10+100" },
		{ "75d10+88000", "-1153", "-957", "7d37+85", "179d10+100" }, // 180
		{ "75d10+89000", "-1160", "-963", "7d37+87", "180d10+100" },
		{ "75d10+90000", "-1167", "-969", "7d37+90", "181d10+100" },
		{ "75d10+91000", "-1174", "-975", "7d38+80", "182d10+100" },
		{ "75d10+92000", "-1181", "-981", "7d38+82", "183d10+100" },
		{ "75d10+93000", "-1188", "-987", "7d38+85", "184d10+100" },
		{ "75d10+94000", "-1195", "-992", "7d38+87", "185d10+100" },
		{ "75d10+95000", "-1202", "-998", "7d38+90", "186d10+100" },
		{ "75d10+96000", "-1209", "-1004", "7d39+80", "187d10+100" },
		{ "75d10+97000", "-1216", "-1010", "7d39+82", "188d10+100" },
		{ "75d10+98000", "-1223", "-1016", "7d39+85", "189d10+100" }, // 190
		{ "75d10+99000", "-1230", "-1021", "7d39+87", "190d10+100" },
		{ "75d10+100000", "-1237", "-1027", "7d39+90", "191d10+100" },
		{ "75d10+101000", "-1244", "-1033", "7d40+80", "192d10+100" },
		{ "75d10+102000", "-1251", "-1039", "7d40+82", "193d10+100" },
		{ "75d10+103000", "-1258", "-1045", "7d40+85", "194d10+100" },
		{ "75d10+104000", "-1265", "-1050", "7d40+87", "195d10+100" },
		{ "75d10+105000", "-1272", "-1056", "7d40+90", "196d10+100" },
		{ "75d10+106000", "-1279", "-1062", "8d36+80", "197d10+100" },
		{ "75d10+107000", "-1286", "-1068", "8d36+82", "198d10+100" },
		{ "75d10+108000", "-1293", "-1074", "8d36+85", "199d10+100" }, // 200
		{ "75d10+109000", "-1300", "-1079", "8d36+87", "200d10+100" },
		{ "75d10+110000", "-1307", "-1085", "8d36+90", "201d10+100" },
		{ "75d10+111000", "-1314", "-1091", "8d37+80", "202d10+100" },
		{ "75d10+112000", "-1321", "-1097", "8d37+83", "203d10+100" },
		{ "75d10+113000", "-1328", "-1103", "8d37+85", "204d10+100" },
		{ "75d10+114000", "-1335", "-1109", "8d37+87", "205d10+100" },
		{ "75d10+115000", "-1342", "-1114", "8d37+90", "206d10+100" },
		{ "75d10+116000", "-1349", "-1120", "8d38+80", "207d10+100" },
		{ "75d10+117000", "-1356", "-1126", "8d38+82", "208d10+100" },
		{ "75d10+118000", "-1363", "-1132", "8d38+85", "209d10+100" }, // 210
		{ "75d10+119000", "-1370", "-1138", "8d38+90", "210d10+100" },
		{ "75d10+120000", "-1377", "-1143", "8d39+80", "211d10+100" },
		{ "75d10+121000", "-1384", "-1149", "8d39+82", "212d10+100" },
		{ "75d10+122000", "-1391", "-1155", "8d39+85", "213d10+100" },
		{ "75d10+123000", "-1398", "-1161", "8d39+87", "214d10+100" },
		{ "75d10+124000", "-1405", "-1167", "8d39+90", "215d10+100" },
		{ "75d10+125000", "-1412", "-1172", "8d40+80", "216d10+100" },
		{ "75d10+126000", "-1419", "-1178", "8d40+82", "217d10+100" },
		{ "75d10+127000", "-1426", "-1184", "8d40+85", "218d10+100" },
		{ "75d10+128000", "-1433", "-1190", "8d40+87", "219d10+100" }, // 220
		{ "75d10+129000", "-1440", "-1196", "8d40+90", "220d10+100" },
		{ "75d10+130000", "-1447", "-1202", "8d41+80", "221d10+100" },
		{ "75d10+131000", "-2908", "-1207", "8d41+82", "222d10+100" },
		{ "75d10+132000", "-2922", "-1213", "8d41+85", "223d10+100" },
		{ "75d10+133000", "-2936", "-1219", "8d41+87", "224d10+100" },
		{ "75d10+134000", "-2944", "-1225", "8d41+90", "225d10+100" }
	};

	public final static int EQarmor_table [][] = 
	{
		{ 1, 1, 1, 0, 0 },
		{ 2, 2, 2, 0, 1 },
		{ 2, 2, 2, 0, 1 },
		{ 2, 2, 3, 0, 1 },
		{ 2, 2, 3, 0, 1 },
		{ 3, 3, 3, 0, 1 },
		{ 3, 3, 3, 0, 1 },
		{ 3, 3, 3, 0, 1 },
		{ 3, 3, 4, 0, 1 },
		{ 3, 3, 4, 0, 1 },
		{ 4, 4, 4, 1, 2 },
		{ 4, 4, 4, 1, 2 },
		{ 4, 4, 4, 1, 2 },
		{ 4, 4, 5, 1, 2 },
		{ 4, 4, 5, 1, 2 },
		{ 5, 5, 5, 1, 2 },
		{ 5, 5, 5, 1, 2 },
		{ 5, 5, 5, 1, 2 },
		{ 5, 5, 6, 1, 2 },
		{ 6, 6, 6, 1, 3 },
		{ 6, 6, 6, 1, 3 },
		{ 6, 6, 6, 1, 3 },
		{ 6, 6, 6, 1, 3 },
		{ 6, 6, 6, 1, 3 },
		{ 7, 7, 7, 1, 3 },
		{ 7, 7, 7, 1, 3 },
		{ 7, 7, 7, 1, 3 },
		{ 7, 7, 7, 1, 3 },
		{ 7, 7, 7, 1, 3 },
		{ 8, 8, 8, 2, 4 },
		{ 8, 8, 8, 2, 4 },
		{ 8, 8, 8, 2, 4 },
		{ 8, 8, 8, 2, 4 },
		{ 9, 8, 8, 2, 4 },
		{ 9, 9, 9, 2, 4 },
		{ 9, 9, 9, 2, 4 },
		{ 9, 9, 9, 2, 4 },
		{ 9, 9, 9, 2, 4 },
		{ 10, 9, 9, 2, 4 },
		{ 10, 10, 10, 2, 5 },
		{ 10, 10, 10, 2, 5 },
		{ 10, 10, 10, 2, 5 },
		{ 11, 10, 10, 2, 5 },
		{ 11, 10, 10, 2, 5 },
		{ 11, 11, 11, 2, 5 },
		{ 11, 11, 11, 2, 5 },
		{ 12, 11, 11, 2, 5 },
		{ 12, 11, 11, 2, 5 },
		{ 12, 12, 12, 3, 6 },
		{ 12, 12, 12, 3, 6 },
		{ 12, 12, 12, 3, 6 },
		{ 13, 12, 12, 3, 6 },
		{ 13, 12, 12, 3, 6 },
		{ 13, 13, 13, 3, 6 },
		{ 13, 13, 13, 3, 6 },
		{ 14, 13, 13, 3, 6 },
		{ 14, 13, 13, 3, 6 },
		{ 14, 13, 13, 3, 6 },
		{ 14, 14, 14, 3, 7 },
		{ 14, 14, 14, 3, 7 },
		{ 15, 14, 14, 3, 7 },
		{ 15, 14, 14, 3, 7 },
		{ 15, 14, 14, 3, 7 },
		{ 15, 15, 15, 3, 7 },
		{ 15, 15, 15, 3, 7 },
		{ 16, 15, 15, 3, 7 },
		{ 16, 15, 15, 3, 7 },
		{ 16, 15, 15, 3, 7 },
		{ 16, 16, 16, 4, 8 },
		{ 17, 16, 16, 4, 8 },
		{ 17, 16, 16, 4, 8 },
		{ 17, 16, 16, 4, 8 },
		{ 17, 16, 16, 4, 8 },
		{ 17, 17, 17, 4, 8 },
		{ 18, 17, 17, 4, 8 },
		{ 18, 17, 17, 4, 8 },
		{ 18, 17, 17, 4, 8 },
		{ 18, 17, 17, 4, 8 },
		{ 18, 18, 18, 4, 9 },
		{ 19, 18, 18, 4, 9 },
		{ 19, 18, 18, 4, 9 },
		{ 19, 18, 18, 4, 9 },
		{ 19, 18, 18, 4, 9 },
		{ 20, 19, 19, 4, 9 },
		{ 20, 19, 19, 4, 9 },
		{ 20, 19, 19, 4, 9 },
		{ 20, 19, 19, 4, 9 },
		{ 20, 19, 19, 4, 9 },
		{ 21, 20, 20, 5, 10 },
		{ 21, 20, 20, 5, 10 },
		{ 21, 20, 20, 5, 10 },
		{ 21, 20, 20, 5, 10 },
		{ 22, 20, 20, 5, 10 },
		{ 22, 21, 21, 5, 10 },
		{ 22, 21, 21, 5, 10 },
		{ 22, 21, 21, 5, 10 },
		{ 22, 21, 21, 5, 10 },
		{ 23, 22, 21, 5, 11 },
		{ 23, 22, 22, 5, 11 },
		{ 23, 22, 22, 5, 11 },
		{ 23, 22, 22, 5, 11 },
		{ 23, 22, 22, 5, 11 },
		{ 24, 23, 22, 5, 11 },
		{ 24, 23, 23, 5, 11 },
		{ 24, 23, 23, 5, 11 },
		{ 24, 23, 23, 5, 11 },
		{ 25, 23, 23, 5, 11 },
		{ 25, 24, 23, 6, 12 },
		{ 25, 24, 24, 6, 12 },
		{ 25, 24, 24, 6, 12 },
		{ 25, 24, 24, 6, 12 },
		{ 26, 24, 24, 6, 12 },
		{ 26, 25, 24, 6, 12 },
		{ 26, 25, 25, 6, 12 },
		{ 26, 25, 25, 6, 12 },
		{ 26, 25, 25, 6, 12 },
		{ 27, 25, 25, 6, 12 },
		{ 27, 26, 25, 6, 13 },
		{ 27, 26, 26, 6, 13 },
		{ 27, 26, 26, 6, 13 },
		{ 28, 26, 26, 6, 13 },
		{ 28, 26, 26, 6, 13 },
		{ 28, 27, 26, 6, 13 },
		{ 28, 27, 27, 6, 13 },
		{ 28, 27, 27, 6, 13 },
		{ 29, 27, 27, 6, 13 },
		{ 29, 27, 27, 6, 13 },
		{ 29, 28, 27, 7, 14 },
		{ 29, 28, 28, 7, 14 },
		{ 30, 28, 28, 7, 14 },
		{ 30, 28, 28, 7, 14 },
		{ 30, 28, 28, 7, 14 },
		{ 30, 29, 28, 7, 14 },
		{ 30, 29, 29, 7, 14 },
		{ 31, 29, 29, 7, 14 },
		{ 31, 29, 29, 7, 14 },
		{ 31, 29, 29, 7, 14 },
		{ 31, 30, 29, 7, 15 },
		{ 31, 30, 30, 7, 15 },
		{ 32, 30, 30, 7, 15 },
		{ 32, 30, 30, 7, 15 },
		{ 32, 30, 30, 7, 15 },
		{ 32, 31, 30, 7, 15 },
		{ 33, 31, 31, 7, 15 },
		{ 33, 31, 31, 7, 15 },
		{ 33, 31, 31, 7, 15 },
		{ 33, 31, 31, 7, 15 },
		{ 33, 32, 31, 8, 16 },
		{ 34, 32, 32, 8, 16 },
		{ 34, 32, 32, 8, 16 },
		{ 34, 32, 32, 8, 16 },
		{ 34, 32, 32, 8, 16 },
		{ 34, 33, 32, 8, 16 },
		{ 35, 33, 33, 8, 16 },
		{ 35, 33, 33, 8, 16 },
		{ 35, 33, 33, 8, 16 },
		{ 35, 33, 33, 8, 16 },
		{ 36, 34, 33, 8, 17 },
		{ 36, 34, 34, 8, 17 },
		{ 36, 34, 34, 8, 17 },
		{ 36, 34, 34, 8, 17 },
		{ 36, 34, 34, 8, 17 },
		{ 37, 35, 34, 8, 17 },
		{ 37, 35, 35, 8, 17 },
		{ 37, 35, 35, 8, 17 },	
		{ 37, 35, 35, 8, 17 },
		{ 37, 35, 35, 8, 17 },
		{ 38, 36, 35, 9, 18 },
		{ 38, 36, 36, 9, 18 },
		{ 38, 36, 36, 9, 18 },
		{ 38, 36, 36, 9, 18 },
		{ 39, 36, 36, 9, 18 },
		{ 39, 37, 36, 9, 18 },
		{ 39, 37, 36, 9, 18 },
		{ 39, 37, 37, 9, 18 },
		{ 39, 37, 37, 9, 18 },
		{ 40, 38, 37, 9, 19 },
		{ 40, 38, 37, 9, 19 },
		{ 40, 38, 37, 9, 19 },
		{ 40, 38, 38, 9, 19 },
		{ 41, 38, 38, 9, 19 },
		{ 41, 39, 38, 9, 19 },
		{ 41, 39, 38, 9, 19 },
		{ 41, 39, 38, 9, 19 },
		{ 41, 39, 39, 9, 19 },
		{ 42, 39, 39, 9, 19 },
		{ 42, 40, 39, 10, 20 },
		{ 42, 40, 39, 10, 20 },
		{ 42, 40, 39, 10, 20 },
		{ 42, 40, 40, 10, 20 },
		{ 43, 40, 40, 10, 20 },
		{ 43, 41, 40, 10, 20 },
		{ 43, 41, 40, 10, 20 },
		{ 43, 41, 40, 10, 20 },
		{ 44, 41, 41, 10, 20 },
		{ 44, 41, 41, 10, 20 },
		{ 44, 42, 41, 10, 21 },
		{ 44, 42, 41, 10, 21 },
		{ 44, 42, 41, 10, 21 },
		{ 45, 42, 42, 10, 21 },
		{ 45, 42, 42, 10, 21 },
		{ 45, 43, 42, 10, 21 },
		{ 45, 43, 42, 10, 21 },
		{ 45, 43, 42, 10, 21 },
		{ 46, 43, 43, 10, 21 },
		{ 46, 43, 43, 10, 21 },
		{ 46, 44, 43, 11, 22 },
		{ 46, 44, 43, 11, 22 },
		{ 47, 44, 43, 11, 22 },
		{ 47, 44, 44, 11, 22 },
		{ 47, 44, 44, 11, 22 },
		{ 47, 45, 44, 11, 22 },
		{ 47, 45, 44, 11, 22 },
		{ 48, 45, 44, 11, 22 },
		{ 48, 45, 45, 11, 22 },
		{ 48, 45, 45, 11, 22 },
		{ 48, 46, 45, 11, 23 },
		{ 48, 46, 45, 11, 23 },
		{ 49, 46, 45, 11, 23 },
		{ 49, 46, 46, 11, 23 },
		{ 49, 46, 46, 11, 23 },
		{ 49, 47, 46, 11, 23 },
		{ 50, 47, 46, 11, 23 },
		{ 50, 47, 46, 11, 23 },
		{ 50, 47, 47, 11, 23 }
	};

		
	public final static int lookupWeaponColumn(String s ) {
		if( s.equalsIgnoreCase("sword") || s.equalsIgnoreCase("exotic")) {
			return 0;
		} else if( s.equalsIgnoreCase("crossbow") || s.equalsIgnoreCase("dagger")) {
			return 1;
		} else if( s.equalsIgnoreCase("spear")) {
			return 2;			
		} else if( s.equalsIgnoreCase("mace")) {
			return 3;
		} else if( s.equalsIgnoreCase("polearm")) {
			return 4;			
		} else if( s.equalsIgnoreCase("axe")) {
			return 5;		
		} else if( s.equalsIgnoreCase("flail")) {
			return 6;			
		}

		return 0;

	}
		
//			  crossbow
	// sword, dagger, spear, mace, polearm, axe, flail
public	final static String WStat_table[][] =
	{
	 { "1 6", "1 3", "1 4", "1 6", "2 3", "1 6", "1 6" },
	 { "1 7", "1 4", "1 5", "1 6", "2 3", "1 7", "1 7" },
	 { "2 3", "1 5", "1 5", "1 7", "2 3", "2 4", "2 3" },
	 { "2 4", "1 5", "1 6", "1 8", "2 4", "2 4", "2 4" },
	 { "2 4", "1 5", "1 6", "1 9", "2 4", "2 5", "2 4" },
	 { "2 5", "1 6", "1 6", "1 9", "2 5", "2 5", "2 5" },
	 { "2 5", "1 6", "1 6", "2 5", "2 5", "2 6", "2 5" },
	 { "2 5", "1 6", "1 7", "2 5", "2 5", "2 6", "2 6" },
	 { "2 6", "1 6", "2 7", "2 6", "2 6", "2 6", "2 6" },
	 { "2 6", "2 6", "2 7", "2 6", "2 6", "2 7", "2 6" },
	 { "2 7", "2 6", "2 7", "2 6", "2 7", "2 7", "2 7" },
	 { "2 7", "2 6", "2 7", "2 7", "2 7", "2 8", "2 7" },
	 { "2 8", "2 6", "2 7", "2 7", "2 7", "2 8", "2 8" },
	 { "2 8", "2 6", "2 7", "2 8", "2 5", "2 9", "2 8" },
	 { "2 8", "2 6", "2 7", "2 8", "2 5", "2 9", "2 8" },
	 { "2 9", "2 6", "2 7", "2 8", "2 6", "2 9", "2 9" },
	 { "2 9", "2 7", "2 7", "2 9", "3 6", "2 10", "2 9" },
	 { "2 10", "2 7", "2 7", "2 9", "3 6", "2 10", "2 10" },
	 { "2 10", "2 7", "2 7", "2 10", "3 6", "2 11", "2 10" },
	 { "3 7", "2 7", "2 7", "2 10", "3 7", "3 7", "3 7" },
	 { "3 7", "2 7", "2 7", "2 10", "3 7", "3 8", "3 7" },
	 { "3 7", "2 7", "2 7", "2 11", "3 7", "3 8", "3 7" },
	 { "3 8", "2 7", "2 7", "2 11", "3 7", "3 8", "3 8" },
	 { "3 8", "2 7", "2 7", "3 12", "3 8", "3 8", "3 8" },
	 { "3 8", "2 7", "3 7", "3 8", "3 8", "3 9", "3 8" },
	 { "3 8", "2 7", "3 7", "3 8", "3 8", "3 9", "3 9" },
	 { "3 9", "3 7", "3 8", "3 8", "3 8", "3 9", "3 9" },
	 { "3 9", "3 7", "3 8", "3 9", "3 9", "3 10", "3 9" },
	 { "3 9", "3 7", "3 8", "3 9", "3 9", "3 10", "3 9" },
	 { "3 10", "3 7", "3 8", "3 9", "3 7", "3 10", "3 10" },
	 { "3 10", "3 7", "3 8", "3 9", "3 7", "3 10", "3 10" },
	 { "3 10", "3 7", "3 8", "3 10", "4 7", "3 11", "3 10" },
	 { "3 10", "3 7", "3 8", "3 10", "4 7", "3 11", "3 11" },
	 { "3 11", "3 7", "3 8", "3 10", "4 8", "3 11", "3 11" },
	 { "3 11", "3 7", "3 8", "3 10", "4 8", "3 12", "3 11" },
	 { "4 8", "3 7", "3 8", "3 11", "4 8", "4 9", "4 8" },
	 { "4 8", "3 7", "3 8", "3 11", "4 8", "4 9", "4 9" },
	 { "4 9", "3 7", "3 8", "3 11", "4 8", "4 9", "4 9" },
	 { "4 9", "3 7", "3 8", "3 12", "4 9", "4 9", "4 9" },
	 { "4 9", "3 7", "3 8", "3 12", "4 9", "4 10", "4 9" },
	 { "4 9", "3 7", "4 8", "4 12", "4 9", "4 10", "4 9" },
	 { "4 9", "3 7", "4 8", "4 12", "4 9", "4 10", "4 10" },
	 { "4 10", "4 7", "4 8", "4 9", "4 9", "4 10", "4 10" },
	 { "4 10", "4 7", "4 8", "4 10", "4 10", "4 10", "4 10" },
	 { "4 10", "4 7", "4 8", "4 10", "4 10", "4 11", "4 10" },
	 { "4 10", "4 7", "4 8", "4 10", "4 8", "4 11", "4 11" },
	 { "4 11", "4 7", "4 8", "4 10", "5 8", "4 11", "4 11" },
	 { "4 11", "4 7", "4 8", "4 10", "5 8", "4 11", "4 11" },
	 { "4 11", "4 7", "4 8", "4 11", "5 8", "4 12", "4 11" },
	 { "4 11", "4 7", "4 8", "4 11", "5 8", "4 12", "4 11" },
	 { "4 11", "4 7", "4 8", "4 11", "5 9", "4 12", "4 12" },
	 { "5 9", "4 7", "4 8", "4 11", "5 9", "5 10", "5 9" },
	 { "5 9", "4 7", "4 8", "4 11", "5 9", "5 10", "5 10" },
	 { "5 9", "4 7", "4 8", "4 11", "5 9", "5 10", "5 10" },
	 { "5 10", "4 7", "4 8", "4 12", "5 9", "5 10", "5 10" },
	 { "5 10", "4 7", "4 8", "4 12", "5 9", "5 10", "5 10" },
	 { "5 10", "4 7", "5 8", "4 12", "5 10", "5 11", "5 10" },
	 { "5 10", "4 7", "5 8", "5 12", "5 10", "5 11", "5 10" },
	 { "5 10", "4 7", "5 8", "5 12", "5 10", "5 11", "5 11" },
	 { "5 10", "5 7", "5 8", "5 13", "5 10", "5 11", "5 11" },
	 { "5 11", "5 7", "5 8", "5 10", "5 10", "5 11", "5 11" },
	 { "5 11", "5 7", "5 8", "5 10", "5 10", "5 11", "5 11" },
	 { "5 11", "5 7", "5 8", "5 11", "6 9", "5 12", "5 11" },
	 { "5 11", "5 7", "5 8", "5 11", "6 9", "5 12", "5 11" },
	 { "5 11", "5 7", "5 8", "5 11", "6 9", "5 12", "5 12" },
	 { "5 11", "5 7", "5 8", "5 11", "6 9", "5 12", "5 12" },
	 { "5 12", "5 7", "5 8", "5 11", "6 9", "5 12", "5 12" },
	 { "5 12", "5 7", "5 8", "5 11", "6 9", "5 12", "5 12" },
	 { "6 10", "5 7", "5 8", "5 11", "6 9", "6 10", "6 10" },
	 { "6 10", "5 7", "5 8", "5 12", "6 10", "6 11", "6 10" },
	 { "6 10", "5 7", "5 8", "5 12", "6 10", "6 11", "6 10" },
	 { "6 10", "5 7", "5 8", "5 12", "6 10", "6 11", "6 11" },
	 { "6 10", "5 7", "6 8", "5 12", "6 10", "6 11", "6 11" },
	 { "6 11", "5 7", "6 8", "5 12", "6 10", "6 11", "6 11" },
	 { "6 11", "5 7", "6 8", "6 12", "6 10", "6 11", "6 11" },
	 { "6 11", "5 7", "6 8", "6 13", "6 10", "6 11", "6 11" },
	 { "6 11", "6 7", "6 8", "6 13", "6 10", "6 12", "6 11" },
	 { "6 11", "6 7", "6 8", "6 13", "7 11", "6 12", "6 11" },
	 { "6 11", "6 7", "6 8", "6 11", "7 9", "6 12", "6 11" },
	 { "6 11", "6 7", "6 8", "6 11", "7 9", "6 12", "6 12" },
	 { "6 11", "6 7", "6 8", "6 11", "7 9", "6 12", "6 12" },
	 { "6 12", "6 7", "6 8", "6 11", "7 9", "6 12", "6 12" },
	 { "6 12", "6 7", "6 8", "6 11", "7 10", "6 12", "6 12" },
	 { "6 12", "6 7", "6 8", "6 11", "7 10", "6 13", "6 12" },
	 { "7 10", "6 7", "6 8", "6 12", "7 10", "7 11", "7 10" },
	 { "7 10", "6 7", "6 8", "6 12", "7 10", "7 11", "7 11" },
	 { "7 10", "6 7", "6 8", "6 12", "7 10", "7 11", "7 11" },
	 { "7 11", "6 7", "6 8", "6 12", "7 10", "7 11", "7 11" },
	 { "7 11", "6 7", "7 8", "6 12", "7 10", "7 11", "7 11" },
	 { "7 11", "6 7", "7 8", "6 12", "7 10", "7 11", "7 11" },
	 { "7 11", "6 7", "7 8", "6 12", "7 10", "7 12", "7 11" },
	 { "7 11", "6 7", "7 8", "7 13", "7 11", "7 12", "7 11" },
	 { "7 11", "6 7", "7 8", "7 13", "8 11", "7 12", "7 11" },
	 { "7 11", "7 7", "7 8", "7 13", "8 11", "7 12", "7 12" },
	 { "7 11", "7 7", "7 8", "7 13", "8 9", "7 12", "7 12" },
	 { "7 12", "7 7", "7 8", "7 13", "8 10", "7 12", "7 12" },
	 { "7 12", "7 7", "7 8", "7 11", "8 10", "7 12", "7 12" },
	 { "7 12", "7 7", "7 8", "7 11", "8 10", "7 12", "7 12" },
	 { "7 12", "7 7", "7 8", "7 11", "8 10", "7 13", "7 12" },
	 { "7 12", "7 7", "7 8", "7 12", "8 10", "7 13", "7 12" },
	 { "8 11", "7 7", "7 8", "7 12", "8 10", "8 11", "8 11" },
	 { "8 11", "7 7", "7 8", "7 12", "8 10", "8 11", "8 11" },
	 { "8 11", "7 7", "7 8", "7 12", "8 10", "8 11", "8 11" },
	 { "8 11", "7 7", "8 8", "7 12", "8 10", "8 11", "8 11" },
	 { "8 11", "7 7", "8 8", "7 12", "8 10", "8 12", "8 11" },
	 { "8 11", "7 7", "8 8", "7 12", "8 11", "8 12", "8 11" },
	 { "8 11", "7 7", "8 8", "7 12", "8 11", "8 12", "8 11" },
	 { "8 11", "7 7", "8 8", "7 12", "8 11", "8 12", "8 12" },
	 { "8 11", "7 7", "8 8", "7 13", "9 11", "8 12", "8 12" },
	 { "8 11", "8 7", "8 8", "8 13", "9 11", "8 12", "8 12" },
	 { "8 12", "8 7", "8 8", "8 13", "9 10", "8 12", "8 12" },
	 { "8 12", "8 7", "8 8", "8 13", "9 10", "8 12", "8 12" },
	 { "8 12", "8 7", "8 8", "8 13", "9 10", "8 12", "8 12" },
	 { "8 12", "8 7", "8 8", "8 13", "9 10", "8 13", "8 12" },
	 { "8 12", "8 7", "8 8", "8 12", "9 10", "8 13", "8 12" },
	 { "8 12", "8 7", "8 8", "8 12", "9 10", "8 13", "8 12" },
	 { "8 12", "8 7", "8 8", "8 12", "9 10", "8 13", "8 12" },
	 { "9 11", "8 7", "8 8", "8 12", "9 10", "9 11", "9 11" },
	 { "9 11", "8 7", "8 8", "8 12", "9 10", "9 12", "9 11" },
	 { "9 11", "8 7", "9 8", "8 12", "9 11", "9 12", "9 11" },
	 { "9 11", "8 7", "9 8", "8 12", "9 11", "9 12", "9 11" },
	 { "9 11", "8 7", "9 8", "8 12", "9 11", "9 12", "9 12" },
	 { "9 11", "8 7", "9 8", "8 12", "9 11", "9 12", "9 12" },
	 { "9 11", "8 7", "9 8", "8 12", "10 11", "9 12", "9 12" },
	 { "9 12", "8 7", "9 8", "8 13", "10 11", "9 12", "9 12" },
	 { "9 12", "8 7", "9 8", "8 13", "10 11", "9 12", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 12", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 12", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 13", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 13", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 13", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 13", "9 12" },
	 { "9 12", "9 7", "9 8", "9 13", "10 10", "9 13", "9 13" },
	 { "10 11", "9 7", "9 8", "9 12", "10 11", "10 12", "10 11" },
	 { "10 11", "9 7", "9 8", "9 12", "10 11", "10 12", "10 11" },
	 { "10 11", "9 7", "10 8", "9 12", "10 11", "10 12", "10 11" },
	 { "10 11", "9 7", "10 8", "9 12", "10 11", "10 12", "10 12" },
	 { "10 11", "9 7", "10 8", "9 12", "10 11", "10 12", "10 12" },
	 { "10 11", "9 7", "10 8", "9 12", "11 11", "10 12", "10 12" },
	 { "10 12", "9 7", "10 8", "9 12", "11 11", "10 12", "10 12" },
	 { "10 12", "9 7", "10 8", "9 12", "11 11", "10 12", "10 12" },
	 { "10 12", "9 7", "10 8", "9 13", "11 11", "10 12", "10 12" },
	 { "10 12", "9 7", "10 8", "9 13", "11 10", "10 12", "10 12" },
	 { "10 12", "10 7", "10 8", "10 13", "11 10", "10 13", "10 12" },
	 { "10 12", "10 7", "10 8", "10 13", "11 10", "10 13", "10 12" },
	 { "10 12", "10 7", "10 8", "10 13", "11 10", "10 13", "10 12" },
	 { "10 12", "10 7", "10 8", "10 13", "11 10", "10 13", "10 12" },
	 { "10 12", "10 7", "10 8", "10 13", "11 11", "10 13", "10 13" },
	 { "10 12", "10 7", "10 8", "10 13", "11 11", "10 13", "10 13" },
	 { "11 11", "10 7", "10 8", "10 13", "11 11", "11 12", "11 11" },
	 { "11 11", "10 7", "10 8", "10 13", "11 11", "11 12", "11 12" },
	 { "11 11", "10 7", "11 8", "10 12", "11 11", "11 12", "11 12" },
	 { "11 11", "10 7", "11 8", "10 12", "11 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 12", "11 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 12", "12 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 12", "12 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 12", "12 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 13", "12 11", "11 12", "11 12" },
	 { "11 12", "10 7", "11 8", "10 13", "12 10", "11 13", "11 12" },
	 { "11 12", "10 7", "11 8", "10 13", "12 10", "11 13", "11 12" },
	 { "11 12", "11 7", "11 8", "11 13", "12 10", "11 13", "11 12" },
	 { "11 12", "11 7", "11 8", "11 13", "12 11", "11 13", "11 12" },
	 { "11 12", "11 7", "11 8", "11 13", "12 11", "11 13", "11 12" },
	 { "11 12", "11 7", "11 8", "11 13", "12 11", "11 13", "11 13" },
	 { "11 12", "11 7", "11 8", "11 13", "12 11", "11 13", "11 13" },
	 { "11 12", "11 7", "11 8", "11 13", "12 11", "11 13", "11 13" },
	 { "12 11", "11 7", "11 8", "11 13", "12 11", "12 12", "12 12" },
	 { "12 11", "11 7", "12 8", "11 13", "12 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 13", "12 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 12", "13 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 12", "13 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 12", "13 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 12", "13 11", "12 12", "12 12" },
	 { "12 12", "11 7", "12 8", "11 13", "13 11", "12 13", "12 12" },
	 { "12 12", "11 7", "12 8", "11 13", "13 10", "12 13", "12 12" },
	 { "12 12", "11 7", "12 8", "11 13", "13 11", "12 13", "12 12" },
	 { "12 12", "11 7", "12 8", "11 13", "13 11", "12 13", "12 12" },
	 { "12 12", "12 7", "12 8", "12 13", "13 11", "12 13", "12 12" },
	 { "12 12", "12 7", "12 8", "12 13", "13 11", "12 13", "12 13" },
	 { "12 12", "12 7", "12 8", "12 13", "13 11", "12 13", "12 13" },
	 { "12 12", "12 7", "12 8", "12 13", "13 11", "12 13", "12 13" },
	 { "12 12", "12 7", "12 8", "12 13", "13 11", "12 13", "12 13" },
	 { "13 11", "12 7", "13 8", "12 13", "13 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "13 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 12", "14 11", "13 12", "13 12" },
	 { "13 12", "12 7", "13 8", "12 12", "14 11", "13 13", "13 12" },
	 { "13 12", "12 7", "13 8", "12 12", "14 11", "13 13", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 13", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 13", "13 12" },
	 { "13 12", "12 7", "13 8", "12 13", "14 11", "13 13", "13 12" },
	 { "13 12", "13 7", "13 8", "12 13", "14 11", "13 13", "13 12" },
	 { "13 12", "13 7", "13 8", "13 13", "14 11", "13 13", "13 13" },
	 { "13 12", "13 7", "13 8", "13 13", "14 11", "13 13", "13 13" },
	 { "13 12", "13 7", "13 8", "13 13", "14 11", "13 13", "13 13" },
	 { "13 12", "13 7", "13 8", "13 13", "14 11", "13 13", "13 13" },
	 { "14 12", "13 7", "14 8", "13 13", "14 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "14 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 12", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 12" },
	 { "14 12", "13 7", "14 8", "13 13", "15 11", "14 13", "14 13" },
	 { "14 12", "14 7", "14 8", "13 13", "15 11", "14 13", "14 13" }

	};


}
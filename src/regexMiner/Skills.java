package regexMiner;

public enum Skills {
	AREA, SCAN, DIG, SELL, WHSE;
	
	public int getCostToAdvance(int level) {
		int costToAdvance = 0;
		switch(level) {
		case 1:
			costToAdvance = -1000;
			break;
		case 2:
			costToAdvance = -1500;
			break;
		case 3:
			costToAdvance = -2000;
			break;
		case 4:
			costToAdvance = -4000;
			break;
		}
		return costToAdvance;
	}
	
	public String getDescription() {
		String description = "";
		
		switch(this) {
		case AREA:
			description = "Increase target area";
			break;
		case DIG:
			description = "Reduce dig cost";
			break;
		case SCAN:
			description = "Reduce scan cost";
			break;
		case SELL:
			description = "Increase sale price";
			break;
		case WHSE:
			description = "Increase warehouse space";
			break;
		}
		return description;
	}
	
	public int[] getValue(int level) {
		int[] values = null;
		
		switch(this) {
		case AREA:
			values = new int[2];
			switch(level) {
			case 1:
				values[0] = 8;
				values[1] = 30;
				break;
			case 2:
				values[0] = 8;
				values[1] = 36;
				break;
			case 3:
				values[0] = 9;
				values[1] = 40;
				break;
			case 4:
				values[0] = 10;
				values[1] = 44;
				break;
			case 5:
				values[0] = 12;
				values[1] = 50;
				break;
			}
			break;
		case DIG:
			values = new int[1];
			switch(level) {
			case 1:
				values[0] = -100;
				break;
			case 2:
				values[0] = -90;
				break;
			case 3:
				values[0] = -80;
				break;
			case 4:
				values[0] = -70;
				break;
			case 5:
				values[0] = -50;
				break;
			}
			break;
		case SCAN:
			values = new int[1];
			switch(level) {
			case 1:
				values[0] = -200;
				break;
			case 2:
				values[0] = -180;
				break;
			case 3:
				values[0] = -160;
				break;
			case 4:
				values[0] = -140;
				break;
			case 5:
				values[0] = -100;
				break;
			}
			break;
		case SELL:
			values = new int[1];
			switch(level) {
			case 1:
				values[0] = 0;
				break;
			case 2:
				values[0] = 10;
				break;
			case 3:
				values[0] = 20;
				break;
			case 4:
				values[0] = 30;
				break;
			case 5:
				values[0] = 50;
				break;
			}
			break;
		case WHSE:
			values = new int[1];
			switch(level) {
			case 1:
				values[0] = 10;
				break;
			case 2:
				values[0] = 15;
				break;
			case 3:
				values[0] = 20;
				break;
			case 4:
				values[0] = 25;
				break;
			case 5:
				values[0] = 40;
				break;
			}
			break;
		}
		return values;
	}
	
	public String getText(int level) {
		String text = null;
		
		switch(this) {
		case AREA:
			switch(level) {
			case 1:
				text = "8 x 30";
				break;
			case 2:
				text = "8 x 36";
				break;
			case 3:
				text = "9 x 40";
				break;
			case 4:
				text = "10 x 44";
				break;
			case 5:
				text = "12 x 50";
				break;
			}
			break;
		case DIG:
			switch(level) {
			case 1:
				text = "$100";
				break;
			case 2:
				text = "$90";
				break;
			case 3:
				text = "$80";
				break;
			case 4:
				text = "$70";
				break;
			case 5:
				text = "$50";
				break;
			}
			break;
		case SCAN:
			switch(level) {
			case 1:
				text = "$200";
				break;
			case 2:
				text = "$180";
				break;
			case 3:
				text = "$160";
				break;
			case 4:
				text = "$140";
				break;
			case 5:
				text = "$100";
				break;
			}
			break;
		case SELL:
			switch(level) {
			case 1:
				text = "+ $0";
				break;
			case 2:
				text = "+ $10";
				break;
			case 3:
				text = "+ $20";
				break;
			case 4:
				text = "+ $30";
				break;
			case 5:
				text = "+ $50";
				break;
			}
			break;
		case WHSE:
			switch(level) {
			case 1:
				text = "10 Units";
				break;
			case 2:
				text = "15 Units";
				break;
			case 3:
				text = "20 Units";
				break;
			case 4:
				text = "25 Units";
				break;
			case 5:
				text = "40 Units";
				break;
			}
			break;
		}
		return text;
	}
	
}

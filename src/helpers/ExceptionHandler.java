package helpers;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void handleSQLUpdateException(Exception e, String uniqueField) {
		if (e.getClass().equals(SQLServerException.class)) {
			SQLServerException ex = (SQLServerException) e;
			if (ex.getMessage().equals("Email exists in TEACHERS TABLE")
					|| e.getMessage().equals("Email exists in STUDENTS TABLE") || ex.getErrorCode() == 2627)
				DialogCreator.showDialog(AlertType.ERROR, "Error", uniqueField + " Already Exists",
						"Please edit " + uniqueField);
			else if (ex.getErrorCode() == 8152)
				DialogCreator.showDialog(AlertType.ERROR, "Error", "Text Limit Exceeded", "Please shorten your text");
			else
				handleException(e);
		} else
			handleException(e);
	}

	public static void handleException(Exception e) {
		e.printStackTrace();
		DialogCreator.showErrorDialog(e);
	}
}

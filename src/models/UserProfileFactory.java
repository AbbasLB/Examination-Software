package models;

import javafx.scene.image.Image;
import models.users.CurrentUser;
import models.users.User;
import models.users.UserType;

public class UserProfileFactory {
	private static final Image IMAGE_MALE_STUDENT = new Image(
			UserProfileFactory.class.getResource("../resources/images/MaleStudent.png").toString());
	private static final Image IMAGE_FEMALE_STUDENT = new Image(
			UserProfileFactory.class.getResource("../resources/images/FemaleStudent.png").toString());;
	private static final Image IMAGE_MALE_TEACHER = new Image(
			UserProfileFactory.class.getResource("../resources/images/MaleTeacher.png").toString());;
	private static final Image IMAGE_FEMALE_TEACHER = new Image(
			UserProfileFactory.class.getResource("../resources/images/FemaleTeacher.png").toString());

	public static Image getUserProfile(User user) {
		if (user.getUserType() == UserType.STUDENT)
			if (CurrentUser.getUser().getGender() == 'M')
				return IMAGE_MALE_STUDENT;
			else
				return IMAGE_FEMALE_STUDENT;
		else if (CurrentUser.getUser().getGender() == 'M')
			return IMAGE_MALE_TEACHER;
		else
			return IMAGE_FEMALE_TEACHER;

	}
}

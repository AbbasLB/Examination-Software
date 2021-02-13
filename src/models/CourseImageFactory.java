package models;

import javafx.scene.image.Image;

public class CourseImageFactory {
	private static final Image IMAGE_MATH = new Image(
			CourseImageFactory.class.getResource("../resources/images/math.png").toString());
	private static final Image IMAGE_PHYSICS = new Image(
			CourseImageFactory.class.getResource("../resources/images/physics.png").toString());
	private static final Image IMAGE_CHEMISTRY = new Image(
			CourseImageFactory.class.getResource("../resources/images/Chemistry.png").toString());
	private static final Image IMAGE_BIOLOGY = new Image(
			CourseImageFactory.class.getResource("../resources/images/Biology.png").toString());
	private static final Image IMAGE_INFO = new Image(
			CourseImageFactory.class.getResource("../resources/images/Info.png").toString());
	private static final Image IMAGE_OTHER = new Image(
			CourseImageFactory.class.getResource("../resources/images/Other.png").toString());

	private CourseImageFactory() {
		// DO NOT INSTANTIATE
	}

	public static Image getCourseImage(Course course) {
		switch (course.getName().charAt(0)) {
		case 'M':
			return IMAGE_MATH;
		case 'P':
			return IMAGE_PHYSICS;
		case 'C':
			return IMAGE_CHEMISTRY;
		case 'B':
			return IMAGE_BIOLOGY;
		case 'I':
			return IMAGE_INFO;
		default:
			return IMAGE_OTHER;
		}
	}
}

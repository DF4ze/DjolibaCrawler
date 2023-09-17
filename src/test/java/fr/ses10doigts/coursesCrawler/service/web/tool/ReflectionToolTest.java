package fr.ses10doigts.coursesCrawler.service.web.tool;


import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.CourseComplete;
import fr.ses10doigts.djolibaCrawler.service.web.tool.ReflectionTool;
import jakarta.annotation.Generated;

@Generated(value = "org.junit-tools-1.1.0")
public class ReflectionToolTest {


    @Test
    public void testGetAllFields() throws Exception {
	List<Field> fields = ReflectionTool.getListAllFields(CourseComplete.class);

	for (Field field : fields) {
	    System.out.println(field.getName());
	}
    }

    @Test
    public void testGetValueOfCourseCompleteField() throws Exception {
	CourseComplete cc = new CourseComplete();
	cc.setCourseID(405070l);

	String courseID = ReflectionTool.getValueOfCourseCompleteField(cc, "courseID");

	System.out.println(courseID);
    }
}
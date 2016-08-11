/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.model;

/**
 *
 * @author junaid.ahmad
 */
public class ModelFactory {

    //returns the required model object
    public BaseModel getModel(String modelName) {

        if (modelName == null) {
            return null;
        } else if (modelName.equalsIgnoreCase("teacherModel")) {
            return new Teacher();
        } else if (modelName.equalsIgnoreCase("studentModel")) {
            return new Student();
        } else if (modelName.equalsIgnoreCase("courseModel")) {
            return new Course();
        }
        return null;
    }
}

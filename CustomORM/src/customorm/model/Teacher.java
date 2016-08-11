/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author junaid.ahmad
 */
public class Teacher implements BaseModel {

    private int id;
    private String name;
    private List<Student> students = new ArrayList();
    private List<Course> courses = new ArrayList();

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object obj) {

        if ((obj != null) && (obj instanceof Teacher)
                && (this.id == ((Teacher) obj).getId())
                && (this.name == null
                        ? ((Teacher) obj).getName() == null
                        : this.name.equals(((Teacher) obj).getName()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

}

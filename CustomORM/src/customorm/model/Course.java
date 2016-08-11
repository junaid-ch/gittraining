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
public class Course implements BaseModel{
    
    private int id;
    private String name;
    private List<Teacher> teachers = new ArrayList();
    private List<Student> students = new ArrayList();

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
     * @return the teachers
     */
    public List<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * @param teachers the teachers to set
     */
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
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

    @Override
    public boolean equals(Object obj) {
        
        if((obj != null) && (obj instanceof Course) 
                && (this.id == ((Course)obj).getId()) 
                && (this.name == null 
                ? ((Course)obj).getName() == null 
                : this.name.equals(((Course)obj).getName()))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        return hash;
    }
}

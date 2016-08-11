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

public class Student implements BaseModel{
    
   private int id;
   private String name;
   private String address;
   private List<Teacher> teachers = new ArrayList();
   private List<Course> courses = new ArrayList();
   
   @Override
   public String getName() {
      return name;
   }
   
   @Override
   public void setName(String name) {
      this.name = name;
   }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

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
        
        if((obj != null) && (obj instanceof Student) 
                && (this.id == ((Student)obj).getId()) 
                && (this.name == null 
                ? ((Student)obj).getName() == null 
                : this.name.equals(((Student)obj).getName()))
                && (this.address == null 
                ? ((Student)obj).getAddress() == null 
                : this.address.equals(((Student)obj).getAddress()))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.address);
        return hash;
    }
}

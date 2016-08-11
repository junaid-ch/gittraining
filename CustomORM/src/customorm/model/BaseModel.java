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
public interface BaseModel {

    public String getName();

    public void setName(String name);

    public int getId();

    public void setId(int id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.model.BaseModel;

/**
 *
 * @author junaid.ahmad
 */
public interface BaseController {

    public int add();

    public int delete();

    public int update();

    public BaseModel print();
}

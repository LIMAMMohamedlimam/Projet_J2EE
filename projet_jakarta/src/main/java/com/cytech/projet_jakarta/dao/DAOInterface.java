package com.cytech.projet_jakarta.dao;

import java.util.List;

public interface DAOInterface <T>{
    public int saveData(T data);
    public int updateData(T data);
    public int removeData(T data);
    public List<T> getAllData();

}

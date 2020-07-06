package com.polyclinic.library.net;

/**
 * @author Lxg
 * @create 2019/12/5
 * @Describe
 */
public interface NetWorkListener<T> {
    void Sucess(T t);

    void Fail(Object message);
}
